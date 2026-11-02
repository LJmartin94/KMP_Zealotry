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

Uncommitted (2026-10-30, phase 1 in progress): Konsist dependency (`libs.versions.toml`, `composeApp/build.gradle.kts`, `androidUnitTest` only) and `composeApp/src/androidUnitTest/kotlin/architecture/` (`ProductionScope`, `ProductionScopeTest`, `RuleSupport`, `LayerRulesTest`, `BoundaryRulesTest`, `HardcodedStringsRuleTest`); `ARCHITECTURE_NOTES.md` (boundary rules, `model/` package, decision 12); this file. Not yet handed over for commit.

---

## Recently Committed

~~Backlog admin: 015 moved to `Backlog/Done/`; `016-Run linting & tests…` and `Fix existing linter issues` moved to `000-Linting Epic/Done/`; these session notes updated~~ — committed in `fcfee64` (HEAD as of 2026-10-30)

~~Kermit-based logger + facade (`util/Logger.kt`) and logging at call sites (backlog 015, now moved to Done); adaptive column fix applied to main-menu buttons~~ — committed in `38c70a5`, `6da223f`, `a2a8449`

~~All remaining detekt and ktlint violations cleared (`AdaptiveColumn.kt` reordered so the Composable is first; trivial detekt fixes; remaining ktlint errors) — the previously open `ForbiddenComment` warning is gone~~ — committed in `60c122c`, `42aa5e7`, `79e8736` (HEAD at time of writing)

---

## Remaining Planned Work

**Detekt and ktlint cleanup complete** (reported clean by the owner; not independently re-run this session). The logger (backlog 015) is done.

**Current goal (owner, 2026-10-16): resolve every remaining story in `organisation/Backlog/000-Linting Epic/` and move the whole epic to `Done/`.** The remaining stories are architecture/convention rules that stock detekt/ktlint can't enforce, so the chosen approach is **Konsist architecture tests run via `composeApp:check`**. The existing non-blocking `.githooks/pre-push` then covers them with no extra wiring.

**Agreed sequencing (owner, 2026-10-16) — two phases, do not merge them:**

1. **Phase 1 — implement all the checks, and leave the violations in place.** `composeApp:check` is *expected to fail* during this phase; that is deliberate. Do **not** fix, suppress, baseline or exclude any violation to make it pass. The pre-push hook is non-blocking, so a red `check` doesn't impede pushing.
2. **Phase 2 — the owner fixes the violations personally** (as practice). Each rule's failures going away is the verification that the check fires correctly and detects the right things. The AI must not fix them unprompted; if asked, guide rather than just do it.

Verify in phase 1 that every check genuinely fails against the current violations (a rule that passes vacuously on an empty/mis-scoped scope is the main risk). Rules with no current violations (`@Composable` in data, one ViewModel per view) can't be proven by the existing code — prove them with a temporary deliberate violation, reverted afterwards, and tell the owner.

**Technical setup (verified 2026-10-30):** Konsist is JVM-only, so the tests live in `composeApp/src/androidUnitTest/kotlin/architecture/` (run by `testDebugUnitTest`, part of `check`) and scan `commonMain` via `ProductionScope`. Konsist is a test-only dependency (`libs.versions.toml`); Kover needs no change (no production code added). `composeApp/build.gradle.kts` sets `testLogging` to `exceptionFormat = FULL` for all `Test` tasks, so each violation (file, line, code) prints in the terminal without opening the HTML report.

| Story | Violations at 2026-10-16 (grep scan) — left in place until phase 2 | Check to implement |
|---|---|---|
| `No .dp or .sp in files that aren't style-files` | ~17 literals outside `presentation/style/` (screens, `MainTaskButton`, `ChecklistButton`, `MainMenuSub`, `CustomFABs` private vals) | Konsist rule: no `.dp`/`.sp` literal outside `presentation.style` |
| `No @Composable annotation in data layer` | None (only `toad/GetViewModel.kt`, which is not the data layer) | Konsist rule on the `data` package |
| `No hardcoded strings` | 3 user-facing literals: `ExampleAction.kt:37`, `ExampleScreen.kt:36` (`"on"`/`"off"`), `Strings.kt:46` (`"Error"`) | Implemented: `HardcodedStringsRuleTest` flags every string literal not matched by a named whitelist entry |
| `Only one viewmodel per view` | None (3 ViewModels, 3 screens, one `getViewModel<T>()` each) | Konsist rule: one `getViewModel` call per `*Screen` file |
| `Presentation should never import from data layer unless in ViewModel` | 5 (Actions 2, view-side files 3); wiring files are clean | Implemented as role rules in `BoundaryRulesTest` (decided; see `ARCHITECTURE_NOTES.md` "TOAD boundary rules" for the rules and the phase 2 fix plan) |

`016-Run linting & tests on commit or push to main` and `Fix existing linter issues` are already in `000-Linting Epic/Done/` (`fcfee64`). Stories already marked done are left unmodified — do not refine or rewrite them. 016 is **deliberately not implemented via GitHub Actions** — see below. **The pre-push hook is non-blocking. This is settled (owner, 2026-10-30), not an open question — do not raise it again.**

`General Info` (in the epic folder) is reference material to help complete the work; move it to `Done/` along with the epic once every story is done.

**Phase 1 progress (2026-10-30):** Konsist is wired in (`androidUnitTest`, run from `~/ghZealotry` with `./gradlew composeApp:testDebugUnitTest --tests 'architecture.*' --no-watch-fs --rerun` — the `Ai/ghZealotry` symlink gives stale Gradle results). Implemented and proven: dp/sp (17 real violations), `@Composable` in data and the ViewModel rules (proven with temporary violations; the one-per-screen rule also counts the inferred `val vm: T = getViewModel()` form, `getViewModel` may only be called from `*Screen` files, and hand-built ViewModels — `XViewModel(...)` or androidx `viewModel { }` outside `toad/`, `di/` and the ViewModel files — are banned), presentation→data boundary as three role rules (Actions: 2 violations; view-side files: 3; wiring files: entities banned, 0), and `model/` imports nothing from other layers (proven with a temporary violation; package intentionally does not exist yet). **Temporary violation files are deliberately left in place (owner request, 2026-10-30)** so the owner can watch each rule fire: `data/TemporaryKonsistViolation.kt`, `presentation/example/TemporaryKonsistViolationScreen.kt`, `presentation/example/TemporaryKonsistViolationButton.kt`, `presentation/example/TemporaryKonsistStringsViolation.kt`, `presentation/example/TemporaryViolationViewModel.kt`, `model/TemporaryModelViolation.kt` (each headed "TEMPORARY"). They are deleted in phase 2 alongside the real violations; do not delete or "fix" them earlier, and they are expected to show up as extra failures until then. **Remaining in phase 1: nothing to implement — all five stories have a rule (12 tests; 10 fail by design: 4 on real violations — dp/sp, strings, Actions, view-side imports — and 6 only because of the temporary files).** Hardcoded-strings design (owner decision: flag *all* literals, whitelist developer-facing ones): the whitelist lives in `HardcodedStringsRuleTest.ALLOWED`, each entry with a reason (annotations, `const val`, `Logger.x`, `error`/`check`/`require`/`throw`, `parse`/`parseIsoString`, locale codes in `util.localisation.Locale`, and the whole `data` package for SQL/table names/storage keys). Entries are deliberately not checked for staleness (owner decision, 2026-10-30): a convention pattern such as `error(` stays valid even if the code base briefly has none, and a broken pattern already shows up as violations in the main test. Matching follows the call enclosing each literal rather than a line count (documented in the test header): the file is scanned character by character with a stack of open calls, and a literal is allowed if an entry matches the text before an enclosing call, a trailing lambda's call (`check(x) {\n "msg"\n}`), or its own line. A wrapped `error(…)` message is allowed at any length; `Text("Hello")` after an already-closed `Logger.d("x")` is flagged (the previous 2-line look-back let that through silently). Phase 2 fix for the strings: move the three real literals to string resources (the fourth reported literal is in a temporary file). `ProductionScopeTest` guards against rules silently missing code: every non-test source set under `composeApp/src` must be scanned (`commonMain`) or listed with a reason as deliberately unscanned (`androidMain`, `iosMain`: platform glue only), and stale exclusions fail too (proven with a temporary `desktopMain` directory, removed). Next: hand over commits via `group-changes-for-review`. Phase 2 plan for the boundary violations is in `ARCHITECTURE_NOTES.md` ("TOAD boundary rules").

**`016-Run linting & tests on commit or push to main` (GitHub Actions CI) is intentionally being avoided, not deferred** — see memory `no-github-actions-ci`. This is a solo project the owner wants to be able to make private at will; any dependency on GitHub-hosted runners is seen as jeopardising that, and CI's core value (catching bad changes from other contributors) doesn't apply with a single contributor. Only revisit if a desired lint/architecture rule turns out to be strictly impossible to enforce via the local `.githooks/pre-push` hook alone — and raise that explicitly rather than adding a workflow unprompted.

**Key working agreement for this remaining work:** group fixes into small, separately-reviewable commits (not one giant commit) — mechanical/trivial fixes travel together; anything touching behaviour or requiring judgment gets its own group. Never run `git add`/`commit`/`push` directly — always hand the owner a runnable `git add && git diff --cached` command.
