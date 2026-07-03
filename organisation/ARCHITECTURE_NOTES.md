# Zealotry Architecture Review — Remaining Work

**Repository:** LJmartin94/KMP_Zealotry  
**Last updated:** 2026-07-01 (session 5). Steps 1–6 are complete and committed. Steps 7–8 remain.  
**Session context:** Working through 9 architectural concerns raised in an initial review, then executing a planned refactor of the `z` package. The z refactor is now fully complete.

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

The project is a KMP (Kotlin Multiplatform) Compose app. The `data/example/` feature is the architectural template — a deliberately simple stand-in that pioneers the patterns all real features will follow. The `z` package contained earlier, messier implementations of real features (calendar, day-part menu, main menu). The `z` package has now been fully eliminated in step 6.

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

### 🔄 Concern 2 — Two parallel namespace systems
Content commit staged but not yet owner-approved. See "Step 6" section.

### 🔄 Concern 3 — Mixed ViewModel patterns
Content commit staged but not yet owner-approved. `MainMenuViewModel` and `DayPartMenuViewModel` are rewritten as `ToadViewModel` in the staged commit.

### ✅ Concern 5 — No use case / domain layer
**Decision already made:** UseCases are NOT a required layer for every feature. They are introduced only for pure domain computation logic that is:
- Complex enough to warrant isolation
- Testable with a simple `assertEquals` (no ViewModel/ActionScope machinery needed)
- Potentially shared across multiple Actions

For simple CRUD features (like Example), Actions calling repositories directly is correct — no UseCase needed.

**The only current UseCase candidate** is the astronomical/seasonal context computation in `data/calendar/`. Plan:
1. ✅ z refactor: calendar logic moved to `ObserveCalendarContext` TOAD action (calendar repository moved to `data/calendar/`).
2. ⏳ **After z refactor (step 7):** extract pure computation into `GetAstronomicalContextUseCase`. See step 7.

The UseCase will:
- Take an `Instant`, apply the 4-hour offset business rule, return `AstronomicalContext(season, dayOfSeason, festiveDay?)`
- `CalendarRepositoryImpl` will be simplified to just emit `Instant` at day-boundary intervals (scheduling only — 4hr rule moves out of it)
- `SeasonInfo` becomes an internal implementation detail of the UseCase, not a public data class

**UseCases as Action dependencies:** When a UseCase exists, it is injected into `ActionDependencies` (not into the ViewModel directly). Actions call the UseCase; they do not call the repository if a UseCase is present.

### ✅ Concern 6 — `viewModelScope` passed into `ActionDependencies`
`ActionDependencies.coroutineScope` is an `open val` defaulting to `null` — no ViewModel is forced to pass anything. Already resolved.

### ✅ Concern 7 — Placeholder `id = "example"` in `ExampleViewModel` initial state
`ExampleUiState.id` is now `String? = null`. `UpdateToggle` guards against null. Resolved.

### ✅ Concern 8 — `deleteAllFrom` opened N write transactions
Fixed as part of Concern 4 work — now executes in a single transaction.

### ✅ Concern 9 — No tests
Testing framework (Mokkery 3.3.0 + Turbine 1.2.1) is in place and all Actions have tests. See "Tests" section below.

---

## Step 6 Complete — z Refactor Summary

The z refactor was executed as two commits:

**Step 6 status:** Rename commit is pushed. Content commit is staged and all 9 tests pass, but the owner is reviewing the changes before committing. Revisions may follow.

**Commit 1 — Pure renames (pushed):** All files moved to their final locations with 100% identical content. This allows git rename detection to work reliably on all files including tiny 2-line stubs.

**Commit 2 — Package declarations, imports, structural changes, and ViewModel rewrites:** All content updated.

### Final package structure

```
composeApp/src/commonMain/kotlin/
├── App.kt
├── data/
│   ├── AppDatabase.kt / BaseDao.kt / DatabaseFactory.kt / DatabaseMigration.kt / DatabaseSeeder.kt / EntityId.kt
│   ├── calendar/
│   │   ├── CalendarRepository.kt         ← interface
│   │   ├── CalendarRepositoryImpl.kt     ← implements CalendarRepository; polls clock, emits CalendarState
│   │   ├── CalendarSeasons.kt            ← Season enum + SeasonInfo data class + season computation
│   │   ├── CalendarState.kt              ← data class (dayOfWeek, seasonInfo)
│   │   ├── CalendarTimeUtils.kt          ← getInstantMinusOffset / getDateMinusOffset helpers
│   │   └── FestiveDay.kt                 ← FestiveDay enum + SeasonInfo.getFestiveDay() extension
│   ├── dayPartMenu/
│   │   ├── DayPart.kt                    ← @Serializable enum (MORNING, MIDDAY, EVENING) — data layer entity
│   │   ├── DayPartMenuRepository.kt      ← interface (empty — stub for future task button queries)
│   │   ├── DayPartMenuRepositoryImpl.kt  ← empty stub implementing DayPartMenuRepository
│   │   └── source/local/
│   │       └── TaskButtonState.kt        ← data class (id: String) — will grow into full entity
│   ├── example/
│   │   ├── Example.kt                    ← domain model + CanonicalKey nested enum
│   │   ├── ExampleRepository.kt          ← interface
│   │   ├── ExampleRepositoryImpl.kt
│   │   ├── ModelMappers.kt
│   │   └── source/local/
│   │       ├── ExampleDao.kt
│   │       ├── ExampleEntityLocal.kt
│   │       └── (network stub)
│   └── mainMenu/
│       └── MainMenuRepository.kt         ← empty class stub (no interface yet — no methods to define)
├── di/
│   └── DependencyInjection.kt            ← Koin setup; CalendarRepositoryImpl bound as CalendarRepository;
│                                            DayPartMenuRepositoryImpl bound as DayPartMenuRepository
├── navigation/
│   ├── Navigation.kt                     ← NavHost + NavDestination sealed class
│   └── NavTypeOf.kt                      ← generic NavType<T> helper using SavedState (JB Nav 2.9.x API)
├── presentation/
│   ├── calendar/
│   │   ├── CalendarFragment.kt           ← preserved (dead code, not routed to — kept for file history)
│   │   └── CalendarViewModel.kt          ← preserved (dead code — kept for file history)
│   ├── dayPartMenu/
│   │   ├── DayPartMenuAction.kt          ← abstract DayPartMenuAction + SetDayPart(part: DayPart)
│   │   ├── DayPartMenuActionDependencies.kt
│   │   ├── DayPartMenuScreen.kt
│   │   ├── DayPartMenuUiState.kt         ← implements ViewState; greeting removed (see decisions)
│   │   ├── DayPartMenuViewModel.kt       ← ToadViewModel
│   │   ├── checklistButtons/
│   │   │   ├── ChecklistButton.kt
│   │   │   ├── MainTaskButton.kt
│   │   │   └── SubTaskList.kt
│   │   └── morningButtons/
│   │       └── MorningButtons.kt         ← hardcoded enum of morning tasks (stub — not yet DB-backed)
│   ├── example/                          ← architectural template; do not delete
│   │   ├── ExampleAction.kt
│   │   ├── ExampleActionDependencies.kt
│   │   ├── ExampleScreen.kt
│   │   ├── ExampleUiState.kt             ← + ExampleEvent sealed interface
│   │   └── ExampleViewModel.kt
│   ├── mainMenu/
│   │   ├── MainMenuAction.kt             ← abstract MainMenuAction + ObserveCalendarContext
│   │   ├── MainMenuActionDependencies.kt ← depends on CalendarRepository
│   │   ├── MainMenuScreen.kt
│   │   ├── MainMenuSub.kt
│   │   ├── MainMenuTitle.kt
│   │   ├── MainMenuUiState.kt            ← implements ViewState; + MainMenuEvent sealed interface
│   │   └── MainMenuViewModel.kt          ← ToadViewModel; dispatches ObserveCalendarContext on init
│   ├── resourceComposition/
│   │   ├── Drawables.kt                  ← Season → drawable resource mapping
│   │   └── Strings.kt                    ← DayOfWeek/Season/FestiveDay → string resource mappings
│   ├── reusableUi/                       ← AdaptiveColumn, Chiaroscuro, CustomFABs, GreyScale, ImageButton, OutlinedText
│   └── style/                            ← DarkMode, MainColours, MainTheme, MainTypography, Padding, Weights
├── toad/
│   ├── ActionContracts.kt                ← ViewAction, ActionScope, ActionDependencies
│   ├── GetViewModel.kt
│   ├── StateContracts.kt                 ← ViewState, ViewEvent
│   └── ToadViewModel.kt
└── util/
    ├── FlowExtensions.kt                 ← onUnexpectedNull Flow extension
    ├── dataStructures/
    │   ├── OrderedMap.kt
    │   └── TristateBoolean.kt            ← Boolean? extension functions
    └── localisation/
        └── Locale.kt                     ← Locale interface + implementations for ~50 languages
```

### Key decisions made during the z refactor

**A. Navigation in its own top-level `navigation/` package.**
Navigation is app-shell infrastructure (it uses `@Serializable` for Compose Navigation routing). It is not domain logic and not presentation logic. It belongs at the app-shell level alongside `di/` and `App.kt`.

**B. `DayPart` is a data-layer entity, not a presentation type.**
`DayPart` (MORNING / MIDDAY / EVENING) is a filter/discriminator on a shared entity type — analogous to `CanonicalKey` in the Example feature. It lives in `data/dayPartMenu/DayPart.kt`. The `@Serializable` annotation is retained because `NavDestination.DayPart` uses it for navigation argument encoding.

**C. `NavDestination.DayPart` vs `data.dayPartMenu.DayPart` name clash.**
Both are named `DayPart`. Resolved with a type alias import in `Navigation.kt`:
```kotlin
import data.dayPartMenu.DayPart as DayPartEnum
```
The sealed class `NavDestination.DayPart` keeps its name; the enum is referenced as `DayPartEnum` inside that file.

**D. `greeting: StringResource` removed from `DayPartMenuUiState`.**
The greeting is a pure function of `DayPart` (`MORNING` → "Good morning", etc.). Storing a `StringResource` in state:
- Makes the state untestable in unit tests (`Res.string.*` requires the Compose resources runtime, which is unavailable in `commonTest`)
- Adds redundant state that can never be out of sync with `part`

The greeting is now derived directly in `DayPartMenuScreen` from `uiState.part`. `SetDayPart` action only sets `part`.

**E. `SetDayPart` — lambda receiver shadowing pitfall.**
Inside `scope.setState { copy(part = part) }`, the unqualified `part` resolves to the **receiver's** `part` (i.e. `DayPartMenuUiState.part`, the current value), not `SetDayPart.part` (the new value). The fix is to capture the new value before the lambda:
```kotlin
val newPart = part
scope.setState { copy(part = newPart) }
```
This pattern should be applied any time an action parameter name matches a UiState field name.

**F. `DayPartMenuRepository` is an interface.**
Even though it is currently empty, it is declared as `interface DayPartMenuRepository` with a `DayPartMenuRepositoryImpl` stub. This allows Mokkery to mock it in tests. The same interface/impl split applies to `CalendarRepository`/`CalendarRepositoryImpl` (already had the split before this refactor, now formalised).

**G. `CalendarRepositoryImpl` now implements `CalendarRepository` interface.**
`CalendarRepository` (interface) defines `val updateFlow: Flow<CalendarState>`. `CalendarRepositoryImpl` overrides it. DI binds `CalendarRepositoryImpl` to `CalendarRepository` via `singleOf(::CalendarRepositoryImpl) { bind<CalendarRepository>() }`.

**G2. `CalendarRepository.forceRefresh()` — Doze mode mitigation.**
`CalendarRepositoryImpl` uses a `StateFlow` updated by a coroutine that sleeps until the next day boundary. On Android, Doze mode can defer coroutine `delay()` calls when the app is backgrounded, meaning the StateFlow could hold a stale value when the user returns to the app. `forceRefresh()` recomputes and sets `_updateFlow.value` synchronously (no coroutine — `MutableStateFlow.value` is thread-safe). The call site is a lifecycle observer that calls `repository.forceRefresh()` on app foreground (`ON_START` or `onResume`). This wiring is **not yet implemented** — it should be done when the lifecycle layer is established (likely alongside or after step 7).

**H. `presentation/calendar/` retained as dead code.**
`CalendarFragment` and `CalendarViewModel` are not routed to by the navigation graph and are effectively dead code. They were deliberately kept (rather than deleted) to preserve git file history. They are valid candidate implementations for when a standalone calendar view is eventually needed.

**I. `MainMenuRepository` is a stub class, not an interface yet.**
`MainMenuRepository` has no methods to define — `MainMenuViewModel` currently gets all its data from `CalendarRepository`. `MainMenuRepository` is retained as a stub class (not promoted to interface) until methods are actually needed.

**J. `DayPartMenuScreen` bug fixed — `setDayPart` moved to `LaunchedEffect`.**
Previously: `viewModel.setDayPart(content.part)` was called directly in the composable body — a side effect during composition (wrong).
Now: `LaunchedEffect(content.part) { viewModel.runAction(SetDayPart(content.part)) }` — fired once per key change, as composable side effects must be.

**K. `screens/` subdirectory removed from `presentation/`.**
Following the `presentation/example/` template, feature directories sit directly under `presentation/` — not inside a `screens/` subdirectory. This was an inconsistency in the old structure.

**L. `util/` for shared pure utilities.**
`z.libs.*` becomes:
- `util/FlowExtensions.kt` (flat, no subdirectory — single file)
- `util/dataStructures/` for `OrderedMap` and `TristateBoolean`
- `util/localisation/` for `Locale.kt`

---

## Tests

All 9 tests pass on Android and iOS.

| Test file | Actions tested | Notes |
|---|---|---|
| `presentation/example/UpdateToggleTest.kt` | `UpdateToggle` | Mocks `ExampleRepository`; tests null-id guard and successful toggle update |
| `presentation/example/ObserveExampleTest.kt` | `ObserveExample` | Turbine; tests happy path and upstream error path |
| `presentation/mainMenu/ObserveCalendarContextTest.kt` | `ObserveCalendarContext` | Turbine; tests single and multiple emissions; mocks `CalendarRepository.updateFlow` (property mock) |
| `presentation/dayPartMenu/SetDayPartTest.kt` | `SetDayPart` | Tests part update for single part and all three parts; no repository mock needed |

**Key testing patterns established:**
- `ActionScope(MutableStateFlow(initialState), Channel(Channel.UNLIMITED))` — construct scope directly in tests; no ViewModel instantiation
- Mokkery property mocking: `every { repo.updateFlow } returns flowOf(...)` requires `import dev.mokkery.answering.returns`
- `import dev.mokkery.matcher.any` is required explicitly (extension on `ArgMatchersScope`, not auto-imported)
- Backtick test names: spaces are allowed on Kotlin Native; **commas are not** (causes `Name contains illegal characters: ","` at compile time on Native)

---

## Remaining Work

### Step 7 — `GetAstronomicalContextUseCase` extraction (test-driven)

**What to do:**
1. Write tests first for the UseCase (pure computation — no mocks needed, just `assertEquals`)
2. Extract the season/festive-day computation from `CalendarRepositoryImpl` / `SeasonInfo` into `domain/GetAstronomicalContextUseCase`
3. `CalendarRepositoryImpl` becomes a simple clock-polling flow that emits `Instant` (not `CalendarState`)
4. `CalendarRepository` interface changes to `val updateFlow: Flow<Instant>`
5. `ObserveCalendarContext` action gets `GetAstronomicalContextUseCase` injected via `MainMenuActionDependencies`
6. `SeasonInfo` becomes internal to the UseCase (not exposed in `data/calendar/`)
7. `CalendarState` may become the UseCase output type or be replaced by a new `AstronomicalContext` data class

**Why test-driven here:**
The season computation logic (`getEquinoxesAndSolstices`, `getSeasonStart`, etc.) is pure and complex — exactly the kind of code that benefits from unit tests written before the refactor, so that the refactor can be verified against them.

### Step 8 — Kover coverage enforcement

Add the JetBrains Kover Gradle plugin after step 7 is complete. Configure:
- HTML report generation for local viewing
- Minimum line coverage threshold (decide once baseline is known after steps 6–7)

Kover measures coverage via JVM/Android test execution. Coverage of `commonMain` logic is fully captured. iOS native targets are not measured directly by Kover but share the same logic paths.

---

## Key Architectural Decisions (for AI continuity)

1. **TOAD everywhere** — no raw MVVM ViewModels
2. **Offline-first, Flow-based repositories** — `observe*` for reads, `refresh*` for network sync
3. **`domain/` directory** stays empty as a placeholder until real UseCases are introduced
4. **`Example.kt` stays in `data/example/`** — entity domain models live with their data layer feature
5. **`seedKey` stays as the field name** at the DAO/entity level; `canonicalKey` is the vocabulary at the repository interface and above
6. **`initialActions`** in ViewModels = one-shot startup actions only (fed to `dispatchAll`); long-running observations dispatched separately via `dispatch()`
7. **DAO methods do not return `Result`** — DAOs throw on failure; `runCatching` is applied once at the repository boundary
8. **The 4-hour day offset** is a domain/business rule — belongs in `GetAstronomicalContextUseCase` (step 7)
9. **Entity IDs are plain `String` throughout the stack** — typed `EntityId` wrapper rejected. `generateEntityId()` uses `Uuid.random()`.
10. **Canonical keys use a per-entity nested enum** — `Example.CanonicalKey` (with `FIRST`, `SECOND`). Type-incompatible with `String` at compile time. Each entity owns its own `CanonicalKey` enum.
11. **Presentation layer uses `String?` for entity IDs** — flows from domain model without wrapping.
12. **`DayPart` is a data-layer filter, not a presentation type** — same pattern as `CanonicalKey`. It is `@Serializable` because it is used as a navigation argument.
13. **`greeting` does not live in `DayPartMenuUiState`** — it is a pure function of `DayPart`, derived in the composable. Storing Compose `StringResource` objects in state breaks unit testability.
14. **Lambda receiver shadowing** — when an action parameter name matches a UiState field name, capture it in a `val` before the `setState` lambda to prevent the lambda receiver from shadowing it.

---

## Ordered Execution Plan

1. **Room migration** ✅
2. **Kotlin + AGP + dependency upgrade** ✅ (Kotlin 2.2.0, AGP 8.7.0, CMP 1.8.2, etc.)
3. **Ktlint cleanup** ✅
4. **EntityId / CanonicalKey** ✅
5. **Testing framework + POC tests** ✅ (Mokkery 3.3.0 + Turbine 1.2.1; 5 passing tests)
5a. **Dependency upgrade** ✅ (Kotlin 2.3.21, KSP 2.3.9, AGP 8.13.2, Gradle 9.5.1, CMP 1.11.1)
6. **z refactor** 🔄 — rename commit pushed; content commit staged and passing tests, but not yet reviewed/committed by owner. See "Step 6 Complete" section below for full details of what was done — owner is reviewing before committing.
7. **`GetAstronomicalContextUseCase` extraction** ⏳ — test-driven; see details above
8. **Kover coverage enforcement** ⏳ — after step 7


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
