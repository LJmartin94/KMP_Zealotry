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
| 8 | Kover coverage enforcement | ✅ Complete |

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

## Uncommitted Changes

**Step 8 — Kover setup (complete, uncommitted):**

| File | Change |
|---|---|
| `gradle/libs.versions.toml` | Added `kover = "0.9.1"` version + plugin entry |
| `build.gradle.kts` | Added `alias(libs.plugins.kover) apply false` |
| `composeApp/build.gradle.kts` | Kover plugin applied + excludes + 40% line threshold |
| `organisation/AI/AI_WORKING_AGREEMENT.md` | Added Script Transparency section (echo rule) |

---

## Recently Committed

~~Groups A–D: layering violation fix, top-level functions, DI wiring, docs~~ — committed in 7f89d55, 3e27036, 08da832, c7f7ded

~~Symlinks at project root, hooks to enforce AI behaviour, skills library~~ — committed in d3f063c, d7608b9, b66c82c, c708fd0

~~Koin default-parameter crash fix~~ — committed in 95de691

---

## Remaining Planned Work

### Step 8 — Kover coverage enforcement

Add the JetBrains Kover Gradle plugin after step 7 is committed. Configure:
- HTML report generation for local viewing
- Minimum line coverage threshold (decide once baseline from steps 6–7 is known)

Kover measures coverage via JVM/Android test execution. Coverage of `commonMain` logic is fully captured. iOS native targets are not measured directly by Kover but share the same logic paths.

### Deferred: refresh() lifecycle wiring

`CalendarRepository.refresh()` is not yet wired to any lifecycle observer. Wire it to `ON_START`/`onResume` for Doze mode mitigation when the lifecycle layer is established. No changes needed in `CalendarRepositoryImpl` — since the scheduling loop now lives in `ObserveCalendarContext`, the action's next scheduled `refresh()` will already fire promptly once the app is foregrounded and the scope resumes. The lifecycle wiring simply adds an immediate `refresh()` on foreground to cover any Doze drift without waiting for the scheduled tick.
