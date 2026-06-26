# Zealotry Architecture Review — Remaining Work

**Repository:** LJmartin94/KMP_Zealotry  
**Last commit when this was written:** c4be39c (session 1); continued in session 2 on 2026-06-19  
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
- The companion object constants (`FIRST`, `SECOND`) on `Example` are kept — they are stable domain identifiers, not seed artifacts. The seeder depends on the domain constants (correct direction), not the other way around.

### ✅ Concern 4 — Suspend-only repository with `forceUpdate` anti-pattern
- All read methods in `ExampleRepository` now return `Flow<T>` (not `suspend fun` returning `Result<T>`)
- Pattern: `observe*` for reactive reads, `refresh*` for explicit network sync (stubs for now)
- `LoadExample` action replaced by `ObserveExample` (long-running, collects Flow indefinitely)
- `ObserveExample` dispatched via `dispatch()` separately from `dispatchAll(initialActions)` — because `collect` never returns and would block sequential dispatch
- `initialActions` is now intentionally empty (ready for one-shot startup actions)
- Deletion of a canonical entity logs an explicit error (println placeholder, TODO for proper logging)
- All DAO write methods now return `Result<Unit>` consistently (previously mixed Unit/Int/Result)
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

### ⏳ Concern 6 — `viewModelScope` passed into `ActionDependencies`
`ExampleViewModel` passes `viewModelScope` into `ExampleActionDependencies`. The `coroutineScope` field on `ActionDependencies` is described as "for advanced scenarios only" but is required by the abstract class, so every ViewModel is forced to pass its scope even when not needed. This is a scope leak — actions could launch rogue coroutines. Fix: make `coroutineScope` optional or remove the abstract requirement.

### ⏳ Concern 7 — Placeholder `id = "example"` in `ExampleViewModel` initial state
```kotlin
initialState = ExampleUiState(id = "example", toggle = false, isLoading = false)
```
If `ObserveExample` fails, the ViewModel's `state.id` is the literal string `"example"`. Any subsequent action using `scope.currentState.id` (e.g. `UpdateToggle`) would look up an entity with a fake ID. Fix: make `id` nullable and guard in actions, or reconsider initial state design.

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
7. **All DAO write methods return `Result<Unit>`** — consistent error surfacing
8. The **4-hour day offset** is a domain/business rule, not a repository concern — belongs in a UseCase

---

## Ordered Execution Plan (updated 2026-06-24)

> **Current status:** Room migration code is complete but does not compile yet.
> Root cause: Room 2.7.0 was compiled with Kotlin 2.1.x — pulling in `kotlin-stdlib:2.1.10` — which Kotlin 1.9.23 cannot read (reads up to 2.0.0 metadata only). The Kotlin upgrade must happen immediately after committing the Room migration. Both commits together produce a compiling state.

1. **Room migration** ✅ Code complete — commit as broken intermediate state, then immediately do step 2.
   - All Realm entities → Room `@Entity` data classes
   - `RealmDao.kt` → `BaseDao.kt` (generic interface, no Realm types; `RealmDaoImpl` removed)
   - `ExampleDao` → Room `@Dao` abstract class (abstract internal methods + concrete `Result<Unit>` wrappers)
   - `Database.kt` → `AppDatabase.kt` + `DatabaseFactory.kt` (expect/actual for platform paths)
   - `ZealotryApp` Application class added on Android; `initKoin()` moved from `MainActivity` to `Application.onCreate()`
   - `DatabaseSeeder` uses `insertIgnoreInternal` (idempotent via unique index on `seedKey`)
   - `DatabaseMigration.kt` and `RoomQueries.kt` preserved and updated for Room patterns
   - `TaskButtonState` stripped of `RealmObject`; ObjectId replaced with String UUID
   - New files: `BaseDao.kt`, `AppDatabase.kt`, `DatabaseFactory.kt`, `EntityId.kt`, `RoomQueries.kt`, `DatabaseFactory.android.kt`, `DatabaseFactory.ios.kt`, `ZealotryApp.kt`
   - Deleted: `Database.kt`, `RealmDao.kt`, `RealmQueries.kt`

2. **Kotlin + AGP + dependency upgrade** — unblocks Room compilation:
   - Kotlin 1.9.23 → 2.2.0 (latest stable)
   - AGP 8.2.2 → 8.13.x (latest stable 8.x)
   - Compose Multiplatform 1.6.1 → 1.8.2
   - kotlinx-coroutines → 1.10.2, kotlinx-serialization → 1.9.0, kotlinx-datetime → 0.7.0
   - compileSdk / targetSdk → 35
   - Add `org.jetbrains.kotlin.plugin.compose` plugin (required for Kotlin 2.0+)
   - Update `androidTarget { compilerOptions { jvmTarget } }` (replaces deprecated `kotlinOptions`)
   - TODO: Replace `generateEntityId()` in `EntityId.kt` with `kotlin.uuid.Uuid.random().toString()` (available post-2.0.0 as `@ExperimentalUuidApi`)

3. **Testing framework + POC tests** — Mokkery 3.0.0 + Turbine in `commonTest`:
   - `UpdateToggleTest` — suspend action with mocked repo (establishes Mokkery pattern)
   - `ObserveExampleTest` — Flow-collecting action with mocked Flow (establishes Turbine pattern)

4. **z refactor (Concerns 2, 3)** — migrate `z` package to proper structure; rewrite `MainMenuViewModel` and `DayPartMenuViewModel` as `ToadViewModel`; fix composition side-effect bug in `DayPartMenuScreen`; new Actions get tests as they are created.

5. **`GetAstronomicalContextUseCase` extraction (Concern 5, step 2)** — test-driven: write tests first, then extract the UseCase to satisfy them.

**Testing policy:** Any Action with branching logic, null guards, or non-trivial state transitions gets a unit test at the time it is written.
