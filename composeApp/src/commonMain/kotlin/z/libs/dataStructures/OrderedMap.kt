package z.libs.dataStructures

/**
 * An ordered/indexed mutable map, that allows for list-like operations on its keys.
 *
 * Behaves like it inherits from MutableMap and MutableList, but these are incompatible in minor ways
 */
@Suppress("TooManyFunctions")
class OrderedMap<K, V>(initial: Map<out K, V> = mapOf()) { // : MutableMap<K,V>, MutableList<K>
    // Make an internal copy of the provided map to avoid external mutation desyncs.
    private val mutableMap: MutableMap<K, V> = LinkedHashMap(initial)
    private val orderedEntries: MutableList<K> = mutableMap.keys.toMutableList()

    // Helper to provide a clear exception if invariant is violated
    private fun valueOrThrow(key: K): V = mutableMap[key]
        ?: throw IllegalStateException("OrderedMap invariant broken: key '$key' present in orderedEntries but missing from backing map")

    /**
     * Commonly Inherited:
     */
    val size: Int
        get() = orderedEntries.size

    fun clear() {
        mutableMap.clear()
        orderedEntries.clear()
    }

    fun isEmpty(): Boolean = orderedEntries.isEmpty()

    fun remove(key: K): Boolean {
        mutableMap.remove(key)
        return orderedEntries.remove(key)
    }

    /**
     * Inherited from MutableMap (read-only snapshots, not live views):
     */
    val entries: List<Pair<K, V>>
        get() = orderedEntries.map { entry -> Pair(entry, valueOrThrow(entry)) }
    val keys: List<K>
        get() = orderedEntries.toList()
    val values: List<V>
        get() = orderedEntries.map { entry -> valueOrThrow(entry) }

    fun putAll(from: Map<out K, V>) {
        // Preserve iteration order of `from` while adding only missing keys at the end.
        from.forEach { (k, v) ->
            val existed = mutableMap.containsKey(k)
            mutableMap[k] = v
            if (!existed) orderedEntries.add(k)
        }
    }

    fun put(
        key: K,
        value: V,
    ): V? {
        if (!orderedEntries.contains(key)) orderedEntries.add(key)
        return mutableMap.put(key, value)
    }

    fun get(key: K): V? {
        return mutableMap[key]
    }

    fun containsValue(value: V): Boolean {
        return mutableMap.containsValue(value)
    }

    fun containsKey(key: K): Boolean {
        return mutableMap.containsKey(key)
    }

    /**
     * Inherited from MutableList:
     */
    fun getKey(index: Int): K {
        return orderedEntries[index]
    }

    fun getValue(index: Int): V? {
        return mutableMap[orderedEntries[index]]
    }

    fun mapIterator(): MutableMapIterator = MutableMapIterator(0)

    fun mapIterator(index: Int): MutableMapIterator = MutableMapIterator(index)

    fun removeAt(index: Int): K {
        val toRemove = orderedEntries.removeAt(index)
        mutableMap.remove(toRemove)
        return toRemove
    }

    fun subMap(
        fromIndex: Int,
        toIndex: Int,
    ): OrderedMap<K, V> {
        val subList = orderedEntries.subList(fromIndex, toIndex)
        val constructorParams: MutableMap<K, V> = mutableMapOf()
        subList.forEach { entry -> constructorParams[entry] = valueOrThrow(entry) }
        return OrderedMap(constructorParams)
    }

    fun set(
        index: Int,
        key: K,
        value: V,
    ): K {
        // Robust replace: ensure index bounds, keep keys unique and update backing map.
        if (index !in 0 until size) throw IndexOutOfBoundsException("Index: $index, Size: $size")

        val oldKey = orderedEntries[index]
        if (oldKey == key) {
            // same key: update value only
            mutableMap[key] = value
            return oldKey
        }

        // If the new key already exists earlier or later, remove it first to keep keys unique.
        val existingIndex = orderedEntries.indexOf(key)
        if (existingIndex != -1) {
            orderedEntries.removeAt(existingIndex)
            // If the removed key was before the target index, shifting occurs.
            val replaceIndex = if (existingIndex < index) index - 1 else index
            orderedEntries[replaceIndex] = key
        } else {
            // Simple replace in-place
            orderedEntries[index] = key
        }

        // Update backing map: remove old mapping and set new mapping.
        mutableMap.remove(oldKey)
        mutableMap[key] = value
        return oldKey
    }

    fun swapIndex(
        a: Int,
        b: Int,
    ): Boolean {
        if (a !in 0 until size || b !in 0 until size) return false

        val tempKey = orderedEntries[a]
        orderedEntries[a] = orderedEntries[b]
        orderedEntries[b] = tempKey
        return true
    }

    fun swapKey(
        a: K,
        b: K,
    ): Boolean {
        val first = orderedEntries.indexOf(a)
        val second = orderedEntries.indexOf(b)
        if (first == -1 || second == -1) return false
        return swapIndex(first, second)
    }

    fun retainAll(keys: Collection<K>): Boolean {
        val ret = orderedEntries.retainAll(keys)
        val trimmedPairList = orderedEntries.map { entry -> Pair(entry, valueOrThrow(entry)) }
        mutableMap.clear()
        trimmedPairList.forEach { kvp -> mutableMap[kvp.first] = kvp.second }
        return ret
    }

    fun removeAll(keys: Collection<K>): Boolean {
        keys.forEach { mutableMap.remove(it) }
        return orderedEntries.removeAll(keys)
    }

    fun indexOf(key: K): Int {
        return orderedEntries.indexOf(key)
    }

    fun containsAll(keys: Collection<K>): Boolean {
        return orderedEntries.containsAll(keys)
    }

    // Formerly addAll
    fun putAllAt(
        index: Int,
        from: Map<out K, V>,
    ) {
        // Preserve iteration order of `from` when inserting at index.
        // Remove any existing keys first so we can insert in-order.
        from.keys.forEach { orderedEntries.remove(it) }
        var insertionIndex = index
        from.forEach { (k, v) ->
            mutableMap[k] = v
            orderedEntries.add(insertionIndex++, k)
        }
    }

    // Formerly add
    fun putAt(
        index: Int,
        key: K,
        value: V,
    ) {
        mutableMap[key] = value
        orderedEntries.remove(key)
        orderedEntries.add(index, key)
    }

    /**
     * A ListIterator implementation that manipulates both the ordered key list and the backing map
     * to preserve the invariant that both structures contain the same keys.
     */
    inner class MutableMapIterator(private var cursor: Int = 0) : MutableListIterator<Pair<K, V>> {
        // index of last element returned by next()/previous(); -1 if none or after add/remove
        private var lastReturnedIndex: Int = -1

        init {
            if (cursor < 0 || cursor > orderedEntries.size) throw IndexOutOfBoundsException("Index: $cursor")
        }

        override fun add(element: Pair<K, V>) {
            val key = element.first
            val value = element.second

            // If key exists elsewhere, remove it first to keep uniqueness
            val existing = orderedEntries.indexOf(key)
            if (existing != -1) {
                orderedEntries.removeAt(existing)
                if (existing < cursor) cursor--
            }

            orderedEntries.add(cursor, key)
            mutableMap[key] = value
            cursor++
            lastReturnedIndex = -1
        }

        override fun hasNext(): Boolean {
            return cursor < orderedEntries.size
        }

        override fun hasPrevious(): Boolean {
            return cursor > 0
        }

        override fun next(): Pair<K, V> {
            if (!hasNext()) throw NoSuchElementException()
            val key = orderedEntries[cursor]
            lastReturnedIndex = cursor
            cursor++
            return Pair(key, valueOrThrow(key))
        }

        override fun nextIndex(): Int {
            return cursor
        }

        override fun previous(): Pair<K, V> {
            if (!hasPrevious()) throw NoSuchElementException()
            cursor--
            val key = orderedEntries[cursor]
            lastReturnedIndex = cursor
            return Pair(key, valueOrThrow(key))
        }

        override fun previousIndex(): Int {
            return cursor - 1
        }

        override fun remove() {
            if (lastReturnedIndex == -1) throw IllegalStateException()
            val key = orderedEntries.removeAt(lastReturnedIndex)
            mutableMap.remove(key)

            if (lastReturnedIndex < cursor) cursor--
            lastReturnedIndex = -1
        }

        override fun set(element: Pair<K, V>) {
            if (lastReturnedIndex == -1) throw IllegalStateException()
            val newKey = element.first
            val newValue = element.second

            val currentIndex = lastReturnedIndex
            val oldKey = orderedEntries[currentIndex]

            if (oldKey == newKey) {
                // same key, just update value
                mutableMap[newKey] = newValue
                return
            }

            // If newKey exists elsewhere, remove it first and adjust indices
            val existing = orderedEntries.indexOf(newKey)
            if (existing != -1) {
                orderedEntries.removeAt(existing)
                if (existing < currentIndex) {
                    // removal shifted target to left
                    orderedEntries[currentIndex - 1] = newKey
                    // remove oldKey from map and add new mapping
                    mutableMap.remove(oldKey)
                    mutableMap[newKey] = newValue
                    // adjust cursor and lastReturnedIndex
                    if (existing < cursor) cursor--
                    lastReturnedIndex = currentIndex - 1
                    return
                }
            }

            // Replace in place
            orderedEntries[currentIndex] = newKey
            mutableMap.remove(oldKey)
            mutableMap[newKey] = newValue
            lastReturnedIndex = currentIndex
        }
    }
}
