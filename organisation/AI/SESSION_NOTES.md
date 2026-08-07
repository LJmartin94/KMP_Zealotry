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

None — `organisation/AI/SESSION_NOTES.md` (this file) is the only unstaged change.

---

## Recently Committed

~~Detekt wired into `check` (was silently NO-SOURCE); pre-push git hook + `first-time-setup.sh` bootstrap script; British English + Local Git Hooks docs~~ — committed in `10c9771`, `ccf26d0`, `1b54a3e`, `8ab4bd7`, `91fb1a3`

~~Global detekt `generated/**` exclude (replaces ad-hoc per-rule excludes); 100-char line limit adopted for both detekt and ktlint (`.editorconfig` + `detekt.yml`); `OrderedMap.kt` detekt fixes (`UseCheckOrError`/`ThrowingExceptionsWithoutMessageOrCause`); full line-wrap pass across 21 files to satisfy the new 100-char limit~~ — committed in `36cae7c`, `f198049`, `c958832`, `3fa048f`, `b890e40`

~~Kover coverage enforcement, linting cleanup, script-transparency hook~~ — committed in 98b1f32, e4dbc45, 57aa43d, 482d782, ee6a265

~~Doze mode mitigation: `RefreshCalendarContext` action fired on `ON_START` + integration test~~ — committed in 2f3c7a8, 1beb3db

~~Symlinks at project root, hooks to enforce AI behaviour, skills library~~ — committed in d3f063c, d7608b9, b66c82c, c708fd0

---

## Remaining Planned Work

**In progress: fixing all `detektMetadataMain` violations flagged by the pre-push hook, before adding further checks.**

Started at 33 issues (after fixing the `check`/detekt wiring bug). Now at **18 remaining**, categorised:

| Rule | Count | Location(s) | Notes |
|---|---|---|---|
| `ForbiddenComment` | 5 | `Navigation.kt`, `ExampleRepositoryImpl.kt` (×4) | TODO markers — need to view context and decide resolve/remove/reword per TODO |
| `MagicNumber` | 4 | `GetAstronomicalContextUseCase.kt` | Define named constants |
| `EmptyFunctionBlock` | 3 | `SubTaskList.kt` | Need to view context to decide: suppress vs. add comment vs. remove |
| `TooGenericExceptionCaught` | 2 | `ActionContracts.kt` | Core toad-framework exception handling — may be intentional design, review carefully before "fixing" |
| `UnusedParameter` | 1 | `DayPartMenuScreen.kt` (`onBack`) | Wire it up or prefix `_`/remove |
| `TooManyFunctions` | 1 | `BaseDao.kt` (15 vs threshold 11) | Architectural — split class or raise threshold, needs judgment call |
| `SpreadOperator` | 1 | `DatabaseFactory.kt` | Performance suggestion — judgment call on restructure vs. suppress |
| `MatchingDeclarationName` | 1 | `ChecklistButton.kt` (contains `ChecklistButtonState`) | File **rename** scenario — must invoke `rename-before-modify` skill before touching |

Already resolved this pass: `UseCheckOrError` (3), `ThrowingExceptionsWithoutMessageOrCause` (2), `MaxLineLength` (all — first 6 at 120-char limit, then a further ~29 after adopting the 100-char limit).

**Key working agreement for this remaining work:** group fixes into small, separately-reviewable commits (not one giant "fix all detekt issues" commit) — mechanical/trivial fixes travel together; anything touching behaviour or requiring judgment gets its own group. Never run `git add`/`commit`/`push` directly — always hand the owner a runnable `git add && git diff --cached` command.

**Also deferred (raised, not started):** close out `organisation/Backlog/000-Linting Epic/` — most remaining items need a new custom detekt rule or Konsist architecture test written (not just config); `No hardcoded strings` needs a feasibility check first (Android Lint's `HardcodedText` check likely doesn't cover Compose `Text()` calls, only XML). Also: no GitHub Actions CI workflow was added this session — deliberately deferred in favour of the local pre-push hook (see `AI_WORKING_AGREEMENT.md` § Local Git Hooks for reasoning).
