# Zealotry — Architecture Notes

**Repository:** LJmartin94/KMP_Zealotry

> This file contains stable, general architectural rules and decisions.  
> For orientation, file purposes, and session setup see: `organisation/AI/INDEX.md`

---

## Background

The project is a KMP (Kotlin Multiplatform) Compose app. The `data/example/` feature is the architectural template — a deliberately simple stand-in that pioneers the patterns all real features will follow. The `z` package contained earlier, messier implementations of real features (calendar, day-part menu, main menu). The `z` package has now been fully eliminated.

The two reference architectures used as inspiration:
- https://github.com/android/architecture-samples (offline-first, Flow-based repositories)
- https://github.com/the-mobile-architect/TOAD-Example (TOAD: Typed Object Action Dispatch pattern)

---

## UseCase Policy

UseCases are **not** a required layer for every feature. They are introduced only for pure domain computation logic that is:
- Complex enough to warrant isolation
- Testable with a simple `assertEquals` (no ViewModel/ActionScope machinery needed)
- Potentially shared across multiple Actions

For simple CRUD features (like `example/`), Actions calling repositories directly is correct — no UseCase needed.

**UseCase wiring — differentiated approach:**
Where a UseCase lives depends on whether it has external dependencies:

- **Pure computation UseCase** (no repositories, no network, no platform resources — e.g. `GetAstronomicalContextUseCase`): constructed directly inside `ActionDependencies`. Not registered in Koin. Not passed through the ViewModel constructor. The ViewModel constructor only reflects *external* dependencies.
- **UseCase with external dependencies** (e.g. depends on a repository or network client): registered as a singleton in Koin, passed through the ViewModel constructor into `ActionDependencies`, exactly like a repository.

This makes the ViewModel constructor a reliable signal: everything in it has external dependencies managed by DI. `ActionDependencies` may additionally construct pure UseCases itself without those appearing in the constructor.

---

## Package Structure

```
composeApp/src/
├── commonMain/kotlin/
│   ├── App.kt
│   ├── data/
│   │   ├── AppDatabase.kt / BaseDao.kt / DatabaseFactory.kt / DatabaseMigration.kt / DatabaseSeeder.kt / EntityId.kt
│   │   ├── calendar/
│   │   │   ├── CalendarRepository.kt         ← interface; Flow<Instant>
│   │   │   ├── CalendarRepositoryImpl.kt     ← polls clock, emits Instant at day boundary
│   │   │   └── CalendarTimeUtils.kt          ← getInstantMinusOffset / getDateMinusOffset (used to schedule next wake time)
│   │   ├── dayPartMenu/
│   │   │   ├── DayPart.kt                    ← @Serializable enum (MORNING, MIDDAY, EVENING) — data layer entity
│   │   │   ├── DayPartMenuRepository.kt      ← interface (empty — stub for future task button queries)
│   │   │   ├── DayPartMenuRepositoryImpl.kt  ← empty stub implementing DayPartMenuRepository
│   │   │   └── source/local/
│   │   │       └── TaskButtonState.kt        ← data class (id: String) — will grow into full entity
│   │   ├── example/
│   │   │   ├── Example.kt                    ← domain model + CanonicalKey nested enum
│   │   │   ├── ExampleRepository.kt          ← interface
│   │   │   ├── ExampleRepositoryImpl.kt
│   │   │   ├── ModelMappers.kt
│   │   │   └── source/local/
│   │   │       ├── ExampleDao.kt
│   │   │       ├── ExampleEntityLocal.kt
│   │   │       └── (network stub)
│   │   └── mainMenu/
│   │       └── MainMenuRepository.kt         ← empty class stub (no interface yet — no methods to define)
│   ├── di/
│   │   └── DependencyInjection.kt            ← Koin setup
│   ├── domain/
│   │   └── GetAstronomicalContextUseCase.kt  ← AstronomicalContext + Season + FestiveDay (public); SeasonInfo + computation (private)
│   ├── navigation/
│   │   ├── Navigation.kt                     ← NavHost + NavDestination sealed class
│   │   └── NavTypeOf.kt                      ← generic NavType<T> helper using SavedState (JB Nav 2.9.x API)
│   ├── presentation/
│   │   ├── dayPartMenu/
│   │   │   ├── DayPartMenuAction.kt          ← abstract DayPartMenuAction + SetDayPart(part: DayPart)
│   │   │   ├── DayPartMenuActionDependencies.kt
│   │   │   ├── DayPartMenuScreen.kt
│   │   │   ├── DayPartMenuUiState.kt         ← implements ViewState; greeting removed (see decision D)
│   │   │   ├── DayPartMenuViewModel.kt       ← ToadViewModel
│   │   │   ├── checklistButtons/
│   │   │   │   ├── ChecklistButton.kt
│   │   │   │   ├── MainTaskButton.kt
│   │   │   │   └── SubTaskList.kt
│   │   │   └── morningButtons/
│   │   │       └── MorningButtons.kt         ← hardcoded enum of morning tasks (stub — not yet DB-backed)
│   │   ├── example/                          ← architectural template; do not delete
│   │   │   ├── ExampleAction.kt
│   │   │   ├── ExampleActionDependencies.kt
│   │   │   ├── ExampleScreen.kt
│   │   │   ├── ExampleUiState.kt             ← + ExampleEvent sealed interface
│   │   │   └── ExampleViewModel.kt
│   │   ├── mainMenu/
│   │   │   ├── MainMenuAction.kt             ← abstract MainMenuAction + ObserveCalendarContext
│   │   │   ├── MainMenuActionDependencies.kt ← depends on CalendarRepository; constructs GetAstronomicalContextUseCase internally
│   │   │   ├── MainMenuScreen.kt
│   │   │   ├── MainMenuSub.kt
│   │   │   ├── MainMenuTitle.kt
│   │   │   ├── MainMenuUiState.kt            ← implements ViewState; + MainMenuEvent sealed interface
│   │   │   └── MainMenuViewModel.kt          ← ToadViewModel; dispatches ObserveCalendarContext on init
│   │   ├── resourceComposition/
│   │   │   ├── Drawables.kt                  ← Season → drawable resource mapping
│   │   │   └── Strings.kt                    ← DayOfWeek/Season/FestiveDay → string resource mappings
│   │   ├── reusableUi/                       ← AdaptiveColumn, Chiaroscuro, CustomFABs, GreyScale, ImageButton, OutlinedText
│   │   └── style/                            ← DarkMode, MainColours, MainTheme, MainTypography, Padding, Weights
│   ├── toad/
│   │   ├── ActionContracts.kt                ← ViewAction, ActionScope, ActionDependencies
│   │   ├── GetViewModel.kt
│   │   ├── StateContracts.kt                 ← ViewState, ViewEvent
│   │   └── ToadViewModel.kt
│   └── util/
│       ├── FlowExtensions.kt                 ← onUnexpectedNull Flow extension
│       ├── dataStructures/
│       │   ├── OrderedMap.kt
│       │   └── TristateBoolean.kt            ← Boolean? extension functions
│       └── localisation/
│           └── Locale.kt                     ← Locale interface + implementations for ~50 languages
└── commonTest/kotlin/
    ├── domain/
    │   └── GetAstronomicalContextUseCaseTest.kt  ← 11 tests; TimeZone.UTC injected for determinism
    └── presentation/
        ├── example/
        │   ├── UpdateToggleTest.kt
        │   └── ObserveExampleTest.kt
        ├── mainMenu/
        │   └── ObserveCalendarContextTest.kt
        └── dayPartMenu/
            └── SetDayPartTest.kt
```

---

## Key Architectural Decisions

1. **TOAD everywhere** — no raw MVVM ViewModels
2. **Offline-first, Flow-based repositories** — `observe*` for reactive reads, `refresh*` for explicit network sync
3. **`domain/` for pure computation UseCases only** — not every feature needs a UseCase; see UseCase Policy above
4. **`Example.kt` stays in `data/example/`** — entity domain models live with their data layer feature
5. **`seedKey` at DAO level; `canonicalKey` above** — `seedKey` is the field name at the DAO/entity layer; `canonicalKey` is the vocabulary at the repository interface and above
6. **`initialActions` = one-shot startup actions only** — fed to `dispatchAll()`; long-running observations dispatched separately via `dispatch()` because `collect` never returns
7. **DAO methods do not return `Result`** — DAOs throw on failure; `runCatching` is applied once at the repository boundary. Aligns with Room's design and avoids catching programmer errors silently at the wrong layer.
8. **The 4-hour day offset is a domain/business rule** — belongs in `GetAstronomicalContextUseCase`, not in `CalendarRepositoryImpl`. The repository emits raw `Instant`; the UseCase applies the offset.
9. **Entity IDs are plain `String` throughout the stack** — typed `EntityId` wrapper rejected. UUID uniqueness via `generateEntityId()` (`Uuid.random()`). See also decision #10 for canonical key safety.
10. **Canonical keys use a per-entity nested enum** — `Example.CanonicalKey` (with `FIRST`, `SECOND`). Type-incompatible with `String` at compile time, preventing accidental passing to entity ID methods. Each entity owns its own `CanonicalKey` enum; they are distinct types.
11. **Presentation layer uses `String?` for entity IDs** — `Example.id: String` flows into `ExampleUiState.id: String?` without wrapping. No re-wrapping at any layer boundary.
12. **`DayPart` is a data-layer filter, not a presentation type** — same pattern as `CanonicalKey`. `@Serializable` retained because it is used as a navigation argument.
13. **`greeting` does not live in `DayPartMenuUiState`** — it is a pure function of `DayPart`, derived in the composable. Storing Compose `StringResource` objects in state breaks unit testability.
14. **Lambda receiver shadowing** — when an action parameter name matches a UiState field name, capture it in a `val` before the `setState` lambda to prevent the receiver from shadowing it.

---

## Key Decisions — z Refactor

**A. Navigation in its own top-level `navigation/` package.**
Navigation is app-shell infrastructure (uses `@Serializable` for Compose Navigation routing). Not domain logic, not presentation logic — belongs at app-shell level alongside `di/` and `App.kt`.

**B. `DayPart` is a data-layer entity, not a presentation type.**
`DayPart` (MORNING / MIDDAY / EVENING) is a filter/discriminator on a shared entity type — analogous to `CanonicalKey` in the Example feature. Lives in `data/dayPartMenu/DayPart.kt`. `@Serializable` retained for navigation argument encoding.

**C. `NavDestination.DayPart` vs `data.dayPartMenu.DayPart` name clash.**
Both are named `DayPart`. Resolved with a type alias import in `Navigation.kt`:
```kotlin
import data.dayPartMenu.DayPart as DayPartEnum
```
The sealed class `NavDestination.DayPart` keeps its name; the enum is referenced as `DayPartEnum` inside that file.

**D. `greeting: StringResource` removed from `DayPartMenuUiState`.**
The greeting is a pure function of `DayPart` (`MORNING` → "Good morning", etc.). Storing a `StringResource` in state makes it untestable in `commonTest` (Compose resources runtime unavailable) and adds redundant state that can never be out of sync with `part`. Derived directly in `DayPartMenuScreen`.

**E. `SetDayPart` — lambda receiver shadowing pitfall.**
Inside `scope.setState { copy(part = part) }`, the unqualified `part` resolves to the **receiver's** `part` (current value), not `SetDayPart.part` (new value). Fix: capture before the lambda:
```kotlin
val newPart = part
scope.setState { copy(part = newPart) }
```
Apply this pattern any time an action parameter name matches a UiState field name.

**F. `DayPartMenuRepository` is an interface.**
Even though currently empty, it is declared as `interface DayPartMenuRepository` with a `DayPartMenuRepositoryImpl` stub. This allows Mokkery to mock it in tests.

**G. `CalendarRepositoryImpl` implements `CalendarRepository` interface.**
`CalendarRepository` (interface) defines `val updateFlow: Flow<Instant>`. DI binds `CalendarRepositoryImpl` to `CalendarRepository` via `singleOf(::CalendarRepositoryImpl) { bind<CalendarRepository>() }`.

**G2. `CalendarRepository.forceRefresh()` — Doze mode mitigation.**
`CalendarRepositoryImpl` uses a `StateFlow` updated by a coroutine that sleeps until the next day boundary. Doze mode can defer `delay()` calls when the app is backgrounded, leaving a stale value on resume. `forceRefresh()` recomputes and sets `_updateFlow.value` synchronously (thread-safe on `MutableStateFlow`). Call site: a lifecycle observer on app foreground (`ON_START`/`onResume`). **Not yet implemented** — wire when lifecycle layer is established.

**H. `presentation/calendar/` removed.**
`CalendarFragment` and `CalendarViewModel` were dead code (not routed to by the navigation graph). Deleted during review of the z refactor content commit. They are a valid candidate implementation reference if a standalone calendar view is eventually needed — check git history.

**I. `MainMenuRepository` is a stub class, not an interface yet.**
`MainMenuRepository` has no methods to define — `MainMenuViewModel` currently gets all its data from `CalendarRepository`. Stub class retained (not promoted to interface) until methods are actually needed.

**J. `DayPartMenuScreen` bug fixed — `setDayPart` moved to `LaunchedEffect`.**
Previously: `viewModel.setDayPart(content.part)` called directly in the composable body — a side effect during composition (wrong). Now: `LaunchedEffect(content.part) { viewModel.runAction(SetDayPart(content.part)) }`.

**K. `screens/` subdirectory removed from `presentation/`.**
Feature directories sit directly under `presentation/` (following `presentation/example/` template), not inside a `screens/` subdirectory.

**L. `util/` for shared pure utilities.**
`z.libs.*` becomes `util/FlowExtensions.kt` (flat), `util/dataStructures/` for `OrderedMap` and `TristateBoolean`, `util/localisation/` for `Locale.kt`.

---

## Testing Patterns

Testing framework: **Mokkery 3.3.0** + **Turbine 1.2.1** in `commonTest`.

**Action test setup:**
```kotlin
ActionScope(MutableStateFlow(initialState), Channel(Channel.UNLIMITED))
```
Construct `ActionScope` directly in tests — no ViewModel instantiation needed.

**Mokkery gotchas:**
- Property mocking: `every { repo.updateFlow } returns flowOf(...)` requires `import dev.mokkery.answering.returns`
- Argument matchers: `import dev.mokkery.matcher.any` must be explicit (not auto-imported)

**Kotlin Native test constraints:**
- `assert()` from Kotlin stdlib requires `@OptIn(kotlin.experimental.ExperimentalNativeApi::class)` on Native — use `assertTrue()` from `kotlin.test` instead
- Backtick test names: spaces allowed on Native; **commas are not** (causes compile error on Native)

**UseCase tests:**
- Inject `TimeZone.UTC` via constructor for timezone-independent tests
- Pure computation UseCases need no mocks — plain `assertEquals` suffices

**Testing policy:** Any Action with branching logic, null guards, or non-trivial state transitions gets a unit test at the time it is written.

---

## Step History

Detailed record of what each numbered step involved.

**1. Room migration ✅**
- All Realm entities → Room `@Entity` data classes
- `RealmDao.kt` → `BaseDao.kt` (generic interface, no Realm types; `RealmDaoImpl` removed)
- `ExampleDao` → Room `@Dao` abstract class; generic CRUD inherited from `BaseDao`; entity-specific `@Query` overrides only
- `DatabaseSeeder` uses `insertIfAbsent` (idempotent via unique index on `seedKey`)
- `DatabaseMigration.kt` preserved and updated for Room patterns
- `RoomQueries.kt` removed (annotation arguments must be compile-time constants; no functional benefit over inline SQL)
- `TaskButtonState` stripped of `RealmObject`; ObjectId replaced with String UUID
- New files: `BaseDao.kt`, `AppDatabase.kt`, `DatabaseFactory.kt`, `EntityId.kt`, `DatabaseFactory.android.kt`, `DatabaseFactory.ios.kt`, `ZealotryApp.kt`
- Deleted: `Database.kt`, `RealmDao.kt`, `RealmQueries.kt`

**2. Kotlin + AGP + dependency upgrade ✅**
- Kotlin 1.9.23 → 2.2.0; KSP `2.2.0-2.0.2` (KSP suffix changed from `1.0.x` to `2.0.x` at Kotlin 2.x)
- AGP 8.2.2 → 8.7.0; Gradle wrapper 8.7 → 8.9 (AGP 8.7 requires Gradle 8.9+)
- Compose Multiplatform 1.6.1 → 1.8.2; Compose 1.7.3 → 1.8.0
- kotlinx-coroutines → 1.10.2; kotlinx-serialization → 1.7.3; compileSdk / targetSdk → 35
- `org.jetbrains.kotlin.plugin.compose` plugin added (mandatory since Kotlin 2.0.0)
- `androidTarget { kotlinOptions { jvmTarget } }` → `compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }` (old DSL removed in Kotlin 2.x)
- `EntityId.kt`: manual UUID → `kotlin.uuid.Uuid.random().toString()` with `@OptIn(ExperimentalUuidApi::class)`
- `DatabaseFactory.kt`: removed `setQueryCoroutineContext(Dispatchers.IO)` — `Dispatchers.IO` is internal on Kotlin Native in coroutines 1.10.2
- `DatabaseMigration.kt`: `SupportSQLiteDatabase` (Android-only) → `SQLiteConnection` + `androidx.sqlite.execSQL` (KMP-compatible)
- `DatabaseFactory.ios.kt`: added `@OptIn(ExperimentalForeignApi::class)` (required in Kotlin 2.x for Objective-C/cinterop)

**3. Ktlint cleanup ✅** — 150 pre-existing violations fixed and committed.

**4. EntityId / CanonicalKey ✅** — EntityId wrapper rejected (see decisions #9–11). Companion object String constants replaced with `Example.CanonicalKey` nested enum. Committed.

**5. Testing framework + POC tests ✅** — Mokkery 3.3.0 + Turbine 1.2.1 in `commonTest`. `UpdateToggleTest` and `ObserveExampleTest` pass on Android and iOS.

**5a. Dependency upgrade ✅**
- Kotlin 2.2.0 → 2.3.21; KSP 2.3.9 (now version-independent suffix)
- AGP 8.7.0 → 8.13.2; Gradle 8.9 → 9.5.1
- Compose Multiplatform 1.8.2 → 1.11.1 (dropped iosX64 — only iosArm64 + iosSimulatorArm64 now)
- kotlinx-datetime 0.5.0 → 0.8.0 (breaking: `kotlinx.datetime.Clock/Instant` → `kotlin.time.Clock/Instant`)
- jb-navigation 2.8.0-alpha10 → 2.9.2 (breaking: `NavType` now uses `SavedState` not `Bundle`)
- Room 2.7.0 → 2.8.4; all other deps bumped to latest stable

**6. z refactor ✅** — `z` package fully eliminated. Two-commit pattern used: pure renames first (preserves git history), then content/structural changes. `MainMenuViewModel` and `DayPartMenuViewModel` rewritten as `ToadViewModel`. Composition side-effect bug in `DayPartMenuScreen` fixed. `CalendarFragment` and `CalendarViewModel` deleted (dead code). New Actions: `ObserveCalendarContext`, `SetDayPart` — both with tests.

**7. GetAstronomicalContextUseCase extraction ✅** — test-driven (11 tests written first). `CalendarRepository` simplified to `Flow<Instant>`. Season/festive-day computation extracted to `domain/GetAstronomicalContextUseCase`. `CalendarSeasons.kt`, `CalendarState.kt`, `FestiveDay.kt` deleted. `SeasonInfo` made private inside UseCase. UseCase constructed inside `MainMenuActionDependencies` (pure computation pattern — not in Koin, not in ViewModel constructor). `TimeZone` injected for timezone-independent testing.

**8. Kover coverage enforcement ⏳** — add JetBrains Kover Gradle plugin. Configure HTML report generation and minimum coverage threshold (decide once baseline from steps 6–7 is known). Kover measures via JVM/Android; `commonMain` logic fully captured. iOS native targets not measured directly but share same logic paths.
