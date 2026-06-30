# Zealotry Architecture Review — Remaining Work

**Repository:** LJmartin94/KMP_Zealotry  
**Last commit when this was written:** c4be39c (session 1); continued in session 2 on 2026-06-19; session 3 on 2026-06-26; session 4 on 2026-06-30  
**Session context:** Working through 9 architectural concerns raised in an initial review, then executing a planned refactor of the `z` package.

---

## AI Session Setup Instructions

This file is the single source of truth for ongoing architectural work. The Copilot CLI session system uses a per-session `plan.md` file — to keep it in sync with this file, `plan.md` should be a symlink pointing here.

**If you are resuming in a new session**, run the following to re-establish the symlink (replace `<session-id>` with the current session UUID, visible in the session context at the top of the conversation):

```bash
PLAN=~/.copilot/session-state/<session-id>/plan.md
NOTES=/Users/lindsayjames.martin/ghZealotry/organisation/ARCHITECTURE_NOTES.md
rm "$PLAN"
ln -s "$NOTES" "$PLAN"
```

After running this, writes to `plan.md` and writes to this file are the same operation.

---

## Background

The project is a KMP (Kotlin Multiplatform) Compose app. The `data/example/` feature is the architectural template — a deliberately simple stand-in that pioneers the patterns all real features will follow. The `z` package contains earlier, messier implementations of real features (calendar, day-part menu, main menu) that need to be refactored to match the patterns established by `data/example/`.

The two reference architectures used as inspiration:
- https://github.com/android/architecture-samples (offline-first, Flow-based repositories)
- https://github.com/the-mobile-architect/TOAD-Example (TOAD: Typed Object Action Dispatch pattern)

---

## Concerns Already Resolved

### ✅ Concern 1 — Domain model polluted with seed key vocabulary
- Renamed `getSeededExample` → `getCanonicalExample` in `ExampleRepository`
- Renamed parameter `seedString` → `canonicalKey`
- The `seedKey` field name is intentionally kept at the DAO/entity layer (data-layer internal)
- The companion object String constants (`FIRST`, `SECOND`) on `Example` were later replaced by `Example.CanonicalKey` enum (see decision #9)

### ✅ Concern 4 — Suspend-only repository with `forceUpdate` anti-pattern
- All read methods in `ExampleRepository` now return `Flow<T>` (not `suspend fun` returning `Result<T>`)
- Pattern: `observe*` for reactive reads, `refresh*` for explicit network sync (stubs for now)
- `LoadExample` action replaced by `ObserveExample` (long-running, collects Flow indefinitely)
- `ObserveExample` dispatched via `dispatch()` separately from `dispatchAll(initialActions)` — because `collect` never returns and would block sequential dispatch
- `initialActions` is now intentionally empty (ready for one-shot startup actions)
- Deletion of a canonical entity logs an explicit error (println placeholder, TODO for proper logging)
- All DAO write methods now return `Result<Unit>` consistently (previously mixed Unit/Int/Result) — **later revised: see decision #7 below**
- `deleteAllFrom` now executes in a single write transaction (was N transactions — performance bug fixed)
- `findBySeedKey` removed from `RealmDao` interface (superseded by `observeBySeedKey`)

---

## Concerns Still To Address

### 🔄 Concern 2 — Two parallel namespace systems (deferred — execute after all structural decisions are finalised)
See "The z Refactor" section below.

### ⏳ Concern 3 — Mixed ViewModel patterns
**Decision already made:** Fully adopt TOAD everywhere. `MainMenuViewModel` and `DayPartMenuViewModel` currently extend raw `ViewModel` — both will be rewritten as `ToadViewModel` subclasses during the z refactor (Concern 2).

### ✅ Concern 5 — No use case / domain layer
**Decision made:** UseCases are NOT a required layer for every feature. They are introduced only for pure domain computation logic that is:
- Complex enough to warrant isolation
- Testable with a simple `assertEquals` (no ViewModel/ActionScope machinery needed)
- Potentially shared across multiple Actions

For simple CRUD features (like Example), Actions calling repositories directly is correct — no UseCase needed.

**The only current UseCase candidate** is the astronomical/seasonal context computation in `z.calendar`. Plan:
1. During the z refactor: move calendar logic as-is into a proper `ObserveCalendarContext` TOAD action (calendar repository stays mostly the same, just moves to `data/calendar/`)
2. After the z refactor: extract pure computation into `GetAstronomicalContextUseCase` as a separate follow-up step

The UseCase will:
- Take an `Instant`, apply the 4-hour offset business rule, return `AstronomicalContext(season, dayOfSeason, festiveDay?)`
- `CalendarRepository` will be simplified to just emit `Instant` at day-boundary intervals (scheduling only — 4hr rule moves out of it)
- `SeasonInfo` becomes an internal implementation detail of the UseCase, not a public data class

**UseCases as Action dependencies:** When a UseCase exists, it is injected into `ActionDependencies` (not into the ViewModel directly). Actions call the UseCase; they do not call the repository if a UseCase is present.

### ✅ Concern 6 — `viewModelScope` passed into `ActionDependencies`
`ActionDependencies.coroutineScope` is an `open val` defaulting to `null` — no ViewModel is forced to pass anything. `ExampleActionDependencies` does not pass `viewModelScope`. Concern was based on an outdated version of the code; already resolved.

### ✅ Concern 7 — Placeholder `id = "example"` in `ExampleViewModel` initial state
`ExampleUiState.id` is now `String? = null`. `UpdateToggle` guards against null and no-ops with an error message if the example has not yet loaded. Concern resolved.

### ⏳ Concern 8 — `deleteAllFrom` opened N write transactions ✅ ALREADY FIXED
Fixed as part of Concern 4 work — now executes in a single transaction.

### ⏳ Concern 9 — No tests
TOAD's primary selling point is testable actions. No tests exist.

**Testing policy decided:**
- Any Action with branching logic, null guards, or non-trivial state transitions gets a unit test at the time it is written — not deferred to a later pass.
- Actions that collect Flows use Turbine for testing.

**Planned test order:**
1. After testing framework is set up: write `UpdateToggleTest` (suspend action, mock repo — establishes the Mokkery pattern) and `ObserveExampleTest` (Flow-collecting action — establishes the Turbine pattern).
2. During z refactor: new Actions (`ObserveCalendarContext`, `SetDayPart`, etc.) get tests as they are created.
3. During UseCase extraction: `GetAstronomicalContextUseCase` tests are written first (test-driven), then the UseCase is extracted to satisfy them.

---

## The z Refactor (Concern 2 — Execute Last)

The `z` package is a transitional namespace for code written before the architecture was finalised. Once all structural decisions above are resolved, everything in `z` is migrated to the proper structure and `z` is deleted.

### What's in z and where it goes

| File/Package | What it is | Destination |
|---|---|---|
| `z.calendar.CalendarRepository` | Repo that polls clock + applies 4hr offset | `data/calendar/` — 4hr rule stays for now, extracted to UseCase in step 2 |
| `z.calendar.CalendarState` | Domain data class | `data/calendar/` — becomes UseCase output type in step 2 |
| `z.calendar.SimplifiedSeasons` | Season calculation logic | `data/calendar/` — becomes internal to `GetAstronomicalContextUseCase` in step 2 |
| `z.calendar.FestiveDay` | Festive day enum + extension | `data/calendar/` — becomes part of UseCase output type in step 2 |
| `z.calendar.DayOfWeek` (misnamed — actually time offset utils) | Utility functions | `data/calendar/` |
| `z.screens.mainMenu.MainMenuUIState` | UIState | `presentation/screens/mainMenu/` |
| `z.screens.mainMenu.MainMenuRepository` | Empty stub | Delete |
| `z.screens.dayPartMenu.DayPartMenuUIState` | UIState | `presentation/screens/dayPartMenu/` |
| `z.screens.dayPartMenu.DayPart` | Domain enum | `data/dayPartMenu/` or inline with screen |
| `z.screens.dayPartMenu.TaskButtonState` | Realm entity | `data/dayPartMenu/source/local/` |
| `z.screens.dayPartMenu.DayPartMenuRepository` | Empty stub | Delete |
| `z.libs.*` | Pure utilities | Keep as `z.libs` or move to top-level `util/` |
| `z.navigation.*` | Old navigation (superseded) | Delete |
| `z.greeting.Greeting` | Scaffolding artifact | Delete |
| `z.platform.*` | Platform abstraction | Keep — already has proper `androidMain`/`iosMain` |

### ViewModels to rewrite as ToadViewModel during refactor

**`MainMenuViewModel`** — currently raw MVVM, collects `CalendarRepository.updateFlow`  
Pattern after refactor: `ObserveCalendarContext` action (long-running collect), dispatched separately from `dispatchAll(initialActions)` — same pattern as `ObserveExample`.

**`DayPartMenuViewModel`** — currently raw MVVM, has `setDayPart(part: DayPart)` method  
Pattern after refactor: `SetDayPart(part)` TOAD action, or use `ToadViewModel.updateState()` directly for this simple synchronous state update.

**Bug to fix during refactor:** `DayPartMenuScreen` calls `viewModel.setDayPart(content.part)` directly in the composable body — a side effect during composition. Must be moved to a `LaunchedEffect(content.part)`.

### UIState locations to fix

`MainMenuUIState` is in `z.screens.mainMenu` but `MainMenuViewModel` and `MainMenuScreen` are in `presentation.screens.mainMenu` — the split must be resolved by moving UIState into `presentation`.

---

## Key Architectural Decisions Already Made (for AI continuity)

1. **TOAD everywhere** — no raw MVVM ViewModels
2. **Offline-first, Flow-based repositories** — `observe*` for reads, `refresh*` for network sync
3. **`domain/` directory** stays empty as a placeholder until real UseCases are introduced
4. **`Example.kt` stays in `data/example/`** — not moved to domain; entity domain models live with their data layer feature
5. **`seedKey` stays as the field name** at the DAO/entity level; `canonicalKey` is the vocabulary at the repository interface and above
6. **`initialActions`** in ViewModels = one-shot startup actions only (fed to `dispatchAll`); long-running observations dispatched separately via `dispatch()`
7. **DAO methods do not return `Result`** — DAOs throw on failure; `runCatching` is applied once at the repository boundary for operations where the interface contracts `Result<T>`. This aligns with Room's design (suspend functions propagate exceptions naturally) and avoids catching programmer errors silently at the wrong layer.
8. The **4-hour day offset** is a domain/business rule, not a repository concern — belongs in a UseCase
9. **Entity IDs are plain `String` throughout the stack** — a typed `EntityId` wrapper was explored and deliberately rejected. UUID uniqueness comes from `generateEntityId()` (which uses `Uuid.random()`), not from the type system. The specific canonical-key/entity-ID confusion risk is handled by decision #10 below. Reference: both android/architecture-samples and the TOAD example use plain `String` for IDs.
10. **Canonical keys use a per-entity nested enum** — `Example.CanonicalKey` (with `FIRST`, `SECOND`) replaces the former companion object String constants. The enum type is incompatible with `String` parameters at compile time, preventing canonical keys from being accidentally passed to entity ID methods. Each entity owns its own nested `CanonicalKey` enum; they are distinct types (`Example.CanonicalKey` ≠ `Foo.CanonicalKey`). `BaseDao.observeBySeedKey(seedKey: String)` remains generic — the repository impl calls `canonicalKey.value` to extract the String before passing to the DAO.
11. **Presentation layer uses `String?` for entity IDs** — the domain model (`Example.id: String`) flows into `ExampleUiState.id: String?` without wrapping. If a repository method requires an ID, the action passes `currentState.id` directly. No unwrapping or re-wrapping is needed at any layer boundary.

---

## Ordered Execution Plan (updated 2026-06-24)

> **Current status:** Ktlint cleanup and EntityId/CanonicalKey work complete. Both targets compile cleanly. Next: testing framework + POC tests.

1. **Room migration** ✅ Code complete — commit as broken intermediate state, then immediately do step 2.
   - All Realm entities → Room `@Entity` data classes
   - `RealmDao.kt` → `BaseDao.kt` (generic interface, no Realm types; `RealmDaoImpl` removed)
   - `ExampleDao` → Room `@Dao` abstract class; generic CRUD inherited from `BaseDao`; entity-specific `@Query` overrides only
   - `DatabaseSeeder` uses `insertIfAbsent` (idempotent via unique index on `seedKey`)
   - `DatabaseMigration.kt` preserved and updated for Room patterns
   - `RoomQueries.kt` removed (annotation arguments must be compile-time constants; no functional benefit over inline SQL)
   - `TaskButtonState` stripped of `RealmObject`; ObjectId replaced with String UUID
   - New files: `BaseDao.kt`, `AppDatabase.kt`, `DatabaseFactory.kt`, `EntityId.kt`, `RoomQueries.kt`, `DatabaseFactory.android.kt`, `DatabaseFactory.ios.kt`, `ZealotryApp.kt`
   - Deleted: `Database.kt`, `RealmDao.kt`, `RealmQueries.kt`

2. **Kotlin + AGP + dependency upgrade** ✅ Complete:
   - Kotlin 1.9.23 → 2.2.0; KSP `2.2.0-2.0.2` (note: KSP suffix changed from `1.0.x` to `2.0.x` at Kotlin 2.x)
   - AGP 8.2.2 → 8.7.0; Gradle wrapper 8.7 → 8.9 (AGP 8.7 requires Gradle 8.9+)
   - Compose Multiplatform 1.6.1 → 1.8.2; Compose 1.7.3 → 1.8.0
   - kotlinx-coroutines → 1.10.2; kotlinx-serialization → 1.7.3
   - compileSdk / targetSdk → 35
   - `org.jetbrains.kotlin.plugin.compose` plugin added (mandatory since Kotlin 2.0.0)
   - `androidTarget { kotlinOptions { jvmTarget } }` → `compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }` (old DSL removed in Kotlin 2.x)
   - `EntityId.kt`: manual UUID → `kotlin.uuid.Uuid.random().toString()` with `@OptIn(ExperimentalUuidApi::class)`
   - `DatabaseFactory.kt`: removed `setQueryCoroutineContext(Dispatchers.IO)` — `Dispatchers.IO` is internal on Kotlin Native in coroutines 1.10.2; Room defaults correctly per platform without it
   - `DatabaseMigration.kt`: `SupportSQLiteDatabase` (Android-only) → `SQLiteConnection` + `androidx.sqlite.execSQL` (KMP-compatible)
   - `DatabaseFactory.ios.kt`: added `@OptIn(ExperimentalForeignApi::class)` (required in Kotlin 2.x for any Objective-C/cinterop API access; was implicit in 1.9.x)
   - **Note:** `./gradlew build` runs ktlint as part of the `check` lifecycle and currently fails (150 pre-existing violations). Compilation itself (`assembleDebug` / Android Studio) succeeds. Ktlint cleanup is a separate step before testing.

3. **Ktlint cleanup** ✅ — violations fixed and committed.

4. **EntityId / CanonicalKey** ✅ — EntityId wrapper rejected (see decisions #9–11). Companion object String constants replaced with `Example.CanonicalKey` nested enum. Committed.

5. **Testing framework + POC tests** ✅ — Mokkery 3.3.0 + Turbine 1.2.1 in `commonTest`. `UpdateToggleTest` and `ObserveExampleTest` pass on Android and iOS. Note: backtick test names cannot contain commas on Kotlin Native — spaces only.

5a. **Dependency upgrade** ✅ — Kotlin 2.2.0 → 2.3.21; KSP 2.3.9 (now version-independent); AGP 8.7.0 → 8.13.2; Gradle 8.9 → 9.5.1; Compose Multiplatform 1.8.2 → 1.11.1 (dropped iosX64 — only iosArm64 + iosSimulatorArm64 now); kotlinx-datetime 0.5.0 → 0.8.0 (breaking: `kotlinx.datetime.Clock/Instant` → `kotlin.time.Clock/Instant`); jb-navigation 2.8.0-alpha10 → 2.9.2 (breaking: `NavType` now uses `SavedState` not `Bundle`); Room 2.7.0 → 2.8.4; all other deps bumped to latest stable.

6. **z refactor (Concerns 2, 3)** — migrate `z` package to proper structure; rewrite `MainMenuViewModel` and `DayPartMenuViewModel` as `ToadViewModel`; fix composition side-effect bug in `DayPartMenuScreen`; new Actions get tests as they are created.

7. **`GetAstronomicalContextUseCase` extraction (Concern 5, step 2)** — test-driven: write tests first, then extract the UseCase to satisfy them.

8. **Kover coverage enforcement** — add the Kover Gradle plugin after the z refactor is complete. Configure HTML report generation and a minimum coverage threshold (to be decided once baseline coverage from steps 6–7 is known). Kover measures via JVM/Android test execution; coverage of `commonMain` logic is fully captured. iOS native targets are not measured directly but share the same logic paths.

**Testing policy:** Any Action with branching logic, null guards, or non-trivial state transitions gets a unit test at the time it is written.
