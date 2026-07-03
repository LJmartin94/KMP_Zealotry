# Zealotry — Session Notes

**Repository:** LJmartin94/KMP_Zealotry

> This file tracks current session state: step status, uncommitted work, and next steps.  
> For stable architectural rules and decisions see: `organisation/AI/ARCHITECTURE_NOTES.md`  
> For AI/owner collaboration rules see: `organisation/AI/AI_WORKING_AGREEMENT.md`  
> For file directory and session setup see: `organisation/AI/INDEX.md`

---

## Step Status

| Step | Description | Status |
|---|---|---|
| 1 | Room migration | ✅ Complete |
| 2 | Kotlin + AGP + dependency upgrade | ✅ Complete |
| 3 | Ktlint cleanup | ✅ Complete |
| 4 | EntityId / CanonicalKey | ✅ Complete |
| 5 | Testing framework + POC tests | ✅ Complete |
| 5a | Second dependency upgrade | ✅ Complete |
| 6 | z refactor | ✅ Complete |
| 7 | GetAstronomicalContextUseCase extraction | ✅ Complete |
| 8 | Kover coverage enforcement | ⏳ Not started |

---

## Concerns Status

All 9 original architectural concerns are now resolved.

| Concern | Description | Resolution |
|---|---|---|
| 1 | Domain model polluted with seed key vocabulary | `getCanonicalExample`, `canonicalKey` vocab, `Example.CanonicalKey` enum |
| 2 | Two parallel namespace systems (z package) | z package fully eliminated in step 6 |
| 3 | Mixed ViewModel patterns | All ViewModels are now ToadViewModel |
| 4 | Suspend-only repository / forceUpdate anti-pattern | Flow-based repos; `observe*` / `refresh*` pattern |
| 5 | No use case / domain layer | UseCase policy established; `GetAstronomicalContextUseCase` extracted in step 7 |
| 6 | `viewModelScope` passed into `ActionDependencies` | `coroutineScope` is `open val` defaulting to `null` |
| 7 | Placeholder `id = "example"` in initial state | `ExampleUiState.id` is `String? = null`; `UpdateToggle` guards against null |
| 8 | `deleteAllFrom` opened N write transactions | Fixed: single transaction |
| 9 | No tests | Testing framework in place; all Actions have tests |

---

## Recently Committed

~~`organisation/AI/` — INDEX.md, SESSION_NOTES.md, ARCHITECTURE_NOTES.md split, AI_WORKING_AGREEMENT.md updates~~ — committed in 410c9c3

~~Step 7 remainder — `Drawables.kt`, `Strings.kt`, `ObserveCalendarContextTest.kt`, `domain/GetAstronomicalContextUseCaseTest.kt`~~ — committed in 73e5ac4

---

## Remaining Planned Work

### Step 8 — Kover coverage enforcement

Add the JetBrains Kover Gradle plugin after step 7 is committed. Configure:
- HTML report generation for local viewing
- Minimum line coverage threshold (decide once baseline from steps 6–7 is known)

Kover measures coverage via JVM/Android test execution. Coverage of `commonMain` logic is fully captured. iOS native targets are not measured directly by Kover but share the same logic paths.

### Deferred: forceRefresh() lifecycle wiring

`CalendarRepository.forceRefresh()` exists as a Doze mode mitigation but is not yet wired to any lifecycle observer. Wire it to `ON_START`/`onResume` when the lifecycle layer is established.
