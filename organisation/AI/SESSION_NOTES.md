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

Staged (not yet committed): rename of `organisation/Backlog/015-Create proper Logger & Logging` → `organisation/Backlog/Done/015-Create proper Logger & Logging`.

---

## Recently Committed

~~Kermit-based logger + facade (`util/Logger.kt`) and logging at call sites (backlog 015, now moved to Done); adaptive column fix applied to main-menu buttons~~ — committed in `38c70a5`, `6da223f`, `a2a8449`

~~All remaining detekt and ktlint violations cleared (`AdaptiveColumn.kt` reordered so the Composable is first; trivial detekt fixes; remaining ktlint errors) — the previously open `ForbiddenComment` warning is gone~~ — committed in `60c122c`, `42aa5e7`, `79e8736` (HEAD at time of writing)

~~Remaining `detektMetadataMain` violations resolved: `EmptyFunctionBlock` (TODO() stubs in `SubTaskList.kt`), `TooManyFunctions` (suppressed on `BaseDao` with justification), `TooGenericExceptionCaught` (narrowed to `Exception` + explicit `CancellationException` rethrow in `ActionContracts.kt`, then suppressed with justification), `UnusedParameter` (`onBack` wired up: back button + centred title with mirrored spacer in `DayPartMenuScreen.kt`, `back`/`terug` string resources, `minimumInteractiveComponentSize()` reused from Material instead of a custom size constant)~~ — committed in `a22cfcd`, `4d4b055`, `6de392a`, `5033e35`, `5e24a6d`, `258b415`, `2eaea2c`, `c5e3a29`, `d07c095`

---

## Remaining Planned Work

**Detekt and ktlint cleanup complete** (reported clean by the owner; not independently re-run this session). The logger (backlog 015) is done.

**Current goal (owner, 2026-10-16): resolve every remaining story in `organisation/Backlog/000-Linting Epic/` and move the whole epic to `Done/`.** The remaining stories are architecture/convention rules that stock detekt/ktlint can't enforce, so the chosen approach is **Konsist architecture tests run via `composeApp:check`**. The existing non-blocking `.githooks/pre-push` then covers them with no extra wiring.

**Agreed sequencing (owner, 2026-10-16) — two phases, do not merge them:**

1. **Phase 1 — implement all the checks, and leave the violations in place.** `composeApp:check` is *expected to fail* during this phase; that is deliberate. Do **not** fix, suppress, baseline or exclude any violation to make it pass. The pre-push hook is non-blocking, so a red `check` doesn't impede pushing.
2. **Phase 2 — the owner fixes the violations personally** (as practice). Each rule's failures going away is the verification that the check fires correctly and detects the right things. The AI must not fix them unprompted; if asked, guide rather than just do it.

Verify in phase 1 that every check genuinely fails against the current violations (a rule that passes vacuously on an empty/mis-scoped scope is the main risk). Rules with no current violations (`@Composable` in data, one ViewModel per view) can't be proven by the existing code — prove them with a temporary deliberate violation, reverted afterwards, and tell the owner.

**Open technical question for phase 1:** Konsist is JVM-only, and this project's targets are Android + iOS only. The tests probably have to live in the Android unit-test source set (`androidUnitTest`, run by `testDebugUnitTest`, already part of `check`) rather than `commonTest`, with `Konsist.scopeFromProject()` / module scoping confirmed to find `commonMain` sources. Not yet verified — check this first. Also confirm the Konsist dependency goes in `libs.versions.toml` as a test-only dependency and that Kover's exclusions don't need changing (the tests add no production code).

| Story | Violations at 2026-10-16 (grep scan) — left in place until phase 2 | Check to implement |
|---|---|---|
| `No .dp or .sp in files that aren't style-files` | ~17 literals outside `presentation/style/` (screens, `MainTaskButton`, `ChecklistButton`, `MainMenuSub`, `CustomFABs` private vals) | Konsist rule: no `.dp`/`.sp` literal outside `presentation.style` |
| `No @Composable annotation in data layer` | None (only `toad/GetViewModel.kt`, which is not the data layer) | Konsist rule on the `data` package |
| `No hardcoded strings` | No Compose `Text("...")` literals found; one user-facing literal in `ExampleAction.kt:37`; `error(...)` messages in `style/` are developer-facing | Feasibility check first (Android Lint `HardcodedText` likely misses Compose); else a Konsist rule, with a decision on developer-facing strings (`error(...)`, logging) |
| `Only one viewmodel per view` | None (3 ViewModels, 3 screens, one `getViewModel<T>()` each) | Konsist rule: one `getViewModel` call per `*Screen` file |
| `Presentation should never import from data layer unless in ViewModel` | 11 (Actions, ActionDependencies, UiStates, Screens import `data.*`) | **Decide the allowed boundary first** — ViewModel only, or ViewModel + Actions/ActionDependencies (the strict option implies presentation models in phase 2). Then a Konsist import rule |

`016-Run linting & tests on commit or push to main` also sits in the epic folder but is **deliberately not being implemented via GitHub Actions** — see below. A dry run (2026-10-16) confirmed `composeApp:check` already includes every item the story lists (`koverVerify`, detekt, all ktlint source sets, `testDebugUnitTest`, `iosSimulatorArm64Test`/`allTests`, `lintDebug`), and the hook runs it on push. Proposed close-out, awaiting owner's go-ahead: rewrite the story as "local pre-push only", then move it to `Done/`. Open question for the owner: should the hook stay non-blocking (current, deliberate) or become blocking?

Also still in the epic folder: `Fix existing linter issues` (now satisfied — verify with a clean `composeApp:check`, then move to `Done/`) and `General Info` (reference notes, not a story — decide where it lives when the epic moves).

**`016-Run linting & tests on commit or push to main` (GitHub Actions CI) is intentionally being avoided, not deferred** — see memory `no-github-actions-ci`. This is a solo project the owner wants to be able to make private at will; any dependency on GitHub-hosted runners is seen as jeopardising that, and CI's core value (catching bad changes from other contributors) doesn't apply with a single contributor. Only revisit if a desired lint/architecture rule turns out to be strictly impossible to enforce via the local `.githooks/pre-push` hook alone — and raise that explicitly rather than adding a workflow unprompted.

**Key working agreement for this remaining work:** group fixes into small, separately-reviewable commits (not one giant commit) — mechanical/trivial fixes travel together; anything touching behaviour or requiring judgment gets its own group. Never run `git add`/`commit`/`push` directly — always hand the owner a runnable `git add && git diff --cached` command.
