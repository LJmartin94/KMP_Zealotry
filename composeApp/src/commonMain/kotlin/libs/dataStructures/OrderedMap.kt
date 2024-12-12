package libs.dataStructures

/**
 * An ordered/indexed mutable map, that allows for list-like operations on its keys.
 *
 * Behaves like it inherits from MutableMap and MutableList, but these are incompatible in minor ways
 */
class OrderedMap<K, V>(private val mutableMap: MutableMap<K, V> = mutableMapOf()) /* : MutableMap<K,V>, MutableList<K>*/ {
    private val orderedEntries: MutableList<K> = mutableMap.keys.toMutableList()

    /**
     * Commonly Inherited:
     */
    val size: Int = orderedEntries.size
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
     * Inherited from MutableMap:
     */
    val entries: MutableList<Pair<K, V>>
        get() = orderedEntries.map { entry -> Pair(entry, mutableMap[entry]!!) }.toMutableList()
    val keys: MutableList<K>
        get() = orderedEntries
    val values: MutableList<V>
        get() = orderedEntries.map{ entry -> mutableMap[entry]!!}.toMutableList()
    fun putAll(from: Map<out K, V>) {
        mutableMap.putAll(from)
        val keysToAdd : MutableSet<K> = from.keys.toMutableSet()
        keysToAdd.removeAll(orderedEntries.toSet())
        orderedEntries.addAll(keysToAdd)
    }
    fun put(key: K, value: V): V? {
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

    fun mapIterator(): MutableMapIterator<K,V> {
        TODO("Not yet implemented")
    }

    fun mapIterator(index: Int): MutableMapIterator<K,V> {
        TODO("Not yet implemented")
    }

    fun removeAt(index: Int): K {
        val toRemove = orderedEntries.removeAt(index)
        mutableMap.remove(toRemove)
        return toRemove
    }

    fun subMap(fromIndex: Int, toIndex: Int): OrderedMap<K,V> {
        val subList = orderedEntries.subList(fromIndex,toIndex)
        val constructorParams: MutableMap<K,V> = mutableMapOf()
        orderedEntries.forEach { entry -> constructorParams[entry] = mutableMap[entry]!! }
        return OrderedMap(constructorParams)
    }

    fun set(index: Int, key: K, value: V): K {
        mutableMap[key] = value
        orderedEntries.remove(key)
        return orderedEntries.set(index, key)
    }

    fun swapIndex(a: Int, b: Int): Boolean {
        if (!(a in 0..<size) && (b in 0..<size)) return false

        val tempKey = orderedEntries[a]
        orderedEntries[a] = orderedEntries[b]
        orderedEntries[b] = tempKey
        return true
    }

    fun swapKey(a: K, b: K):Boolean {
        val first = orderedEntries.indexOf(a)
        val second = orderedEntries.indexOf(b)
        return swapIndex(first,second)
    }

    fun retainAll(keys: Collection<K>): Boolean {
        val ret = orderedEntries.retainAll(keys)
        val trimmedPairList = orderedEntries.map { entry-> Pair(entry, mutableMap[entry]) }
        mutableMap.clear()
        trimmedPairList.forEach{kvp -> mutableMap[kvp.first] = kvp.second!! }
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

    //Formerly addAll
    fun putAllAt(index: Int, from: Map<out K, V>): Unit {
        mutableMap.putAll(from)
        orderedEntries.removeAll(from.keys)
        orderedEntries.addAll(index, from.keys)
    }

    //Formerly add
    fun putAt(index: Int, key: K, value: V) {
        mutableMap[key] = value
        orderedEntries.remove(key)
        orderedEntries.add(index, key)
    }
}

class MutableMapIterator<K,V>(private val iterable: OrderedMap<K, V>, private val index: Int=0) : MutableListIterator<Pair<K,V>> {
    private var current : MutableListIterator<Pair<K,V>> = iterable.entries.iterator() as MutableListIterator

    override fun add(element: Pair<K, V>) {
        TODO("Not yet implemented")
    }

    override fun hasNext(): Boolean {
        return current.hasNext()
    }

    override fun hasPrevious(): Boolean {
        return current.hasPrevious()
    }

    override fun next(): Pair<K, V> {
        TODO("Not yet implemented")
    }

    override fun nextIndex(): Int {
        TODO("Not yet implemented")
    }

    override fun previous(): Pair<K, V> {
        TODO("Not yet implemented")
    }

    override fun previousIndex(): Int {
        TODO("Not yet implemented")
    }

    override fun remove() {
        TODO("Not yet implemented")
    }

    override fun set(element: Pair<K, V>) {
        TODO("Not yet implemented")
    }


}