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

- `Navigation.kt` — reworded resolved-design comment, dropped `TODO` keyword (fixes `ForbiddenComment`)
- `ExampleRepositoryImpl.kt` — `@Suppress("ForbiddenComment")` per-function on the 3 network-fetch TODOs (not applicable to the `Example` template domain), with "remove suppression and do the TODO if implementing this template" note; logging TODO left untouched
- `organisation/AI/SESSION_NOTES.md` (this file)

---

## Recently Committed

~~Magic numbers replaced with named constants in `GetAstronomicalContextUseCase.kt`; `ChecklistButtonState` extracted out of `ChecklistButton.kt` into its own file (resolves `MatchingDeclarationName`); `DatabaseFactory.kt` `SpreadOperator` suppressed with justification comment~~ — committed in `1c6abaf`, `7848c36`, `21f9125`, `fa67698`, `c3a7a21`, `6ba32b5`

~~Detekt wired into `check` (was silently NO-SOURCE); pre-push git hook + `first-time-setup.sh` bootstrap script; British English + Local Git Hooks docs~~ — committed in `10c9771`, `ccf26d0`, `1b54a3e`, `8ab4bd7`, `91fb1a3`

~~Global detekt `generated/**` exclude (replaces ad-hoc per-rule excludes); 100-char line limit adopted for both detekt and ktlint (`.editorconfig` + `detekt.yml`); `OrderedMap.kt` detekt fixes (`UseCheckOrError`/`ThrowingExceptionsWithoutMessageOrCause`); full line-wrap pass across 21 files to satisfy the new 100-char limit~~ — committed in `36cae7c`, `f198049`, `c958832`, `3fa048f`, `b890e40`

---

## Remaining Planned Work

**In progress: fixing all `detektMetadataMain` violations flagged by the pre-push hook, before adding further checks.**

Started at 33 issues (after fixing the `check`/detekt wiring bug). Now at **8 remaining** (verified live via `./gradlew detektMetadataMain`), categorised:

| Rule | Count | Location(s) | Notes |
|---|---|---|---|
| `ForbiddenComment` | 1 | `ExampleRepositoryImpl.kt` (logging TODO) | Genuinely pending — will resolve by implementing proper error reporting, not by suppressing |
| `EmptyFunctionBlock` | 3 | `SubTaskList.kt` | Need to view context to decide: suppress vs. add comment vs. remove |
| `TooGenericExceptionCaught` | 2 | `ActionContracts.kt` | Core toad-framework exception handling — may be intentional design, review carefully before "fixing" |
| `UnusedParameter` | 1 | `DayPartMenuScreen.kt` (`onBack`) | Wire it up or prefix `_`/remove |
| `TooManyFunctions` | 1 | `BaseDao.kt` (15 vs threshold 11) | Architectural — split class or raise threshold, needs judgment call |

Already resolved this pass: `UseCheckOrError` (3), `ThrowingExceptionsWithoutMessageOrCause` (2), `MaxLineLength` (all — first 6 at 120-char limit, then a further ~29 after adopting the 100-char limit), `MagicNumber` (4, named constants in `GetAstronomicalContextUseCase.kt`), `MatchingDeclarationName` (1, `ChecklistButtonState` moved to its own file), `SpreadOperator` (1, suppressed in `DatabaseFactory.kt` with justification comment), `ForbiddenComment` (4 of 5 — Navigation.kt reworded as a resolved design note since it wasn't real pending work; the 3 `ExampleRepositoryImpl.kt` network-fetch TODOs are `@Suppress`'d per-function with a "remove suppression and do the TODO if implementing this template" comment, since `Example` is a template domain, not a real feature needing network sync — the 4th, the logging TODO, is left unsuppressed as a genuine reminder).

**Key working agreement for this remaining work:** group fixes into small, separately-reviewable commits (not one giant "fix all detekt issues" commit) — mechanical/trivial fixes travel together; anything touching behaviour or requiring judgment gets its own group. Never run `git add`/`commit`/`push` directly — always hand the owner a runnable `git add && git diff --cached` command.

**Also deferred (raised, not started):** close out `organisation/Backlog/000-Linting Epic/` — most remaining items need a new custom detekt rule or Konsist architecture test written (not just config); `No hardcoded strings` needs a feasibility check first (Android Lint's `HardcodedText` check likely doesn't cover Compose `Text()` calls, only XML). Also: no GitHub Actions CI workflow was added this session — deliberately deferred in favour of the local pre-push hook (see `AI_WORKING_AGREEMENT.md` § Local Git Hooks for reasoning).
