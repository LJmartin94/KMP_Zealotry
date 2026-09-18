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

None — working tree clean.

---

## Recently Committed

~~Detekt wired into `check` (was silently NO-SOURCE); pre-push git hook + `first-time-setup.sh` bootstrap script; British English + Local Git Hooks docs~~ — committed in `10c9771`, `ccf26d0`, `1b54a3e`, `8ab4bd7`, `91fb1a3`

~~Global detekt `generated/**` exclude (replaces ad-hoc per-rule excludes); 100-char line limit adopted for both detekt and ktlint (`.editorconfig` + `detekt.yml`); `OrderedMap.kt` detekt fixes (`UseCheckOrError`/`ThrowingExceptionsWithoutMessageOrCause`); full line-wrap pass across 21 files to satisfy the new 100-char limit~~ — committed in `36cae7c`, `f198049`, `c958832`, `3fa048f`, `b890e40`

~~Remaining `detektMetadataMain` violations resolved: `EmptyFunctionBlock` (TODO() stubs in `SubTaskList.kt`), `TooManyFunctions` (suppressed on `BaseDao` with justification), `TooGenericExceptionCaught` (narrowed to `Exception` + explicit `CancellationException` rethrow in `ActionContracts.kt`, then suppressed with justification), `UnusedParameter` (`onBack` wired up: back button + centred title with mirrored spacer in `DayPartMenuScreen.kt`, `back`/`terug` string resources, `minimumInteractiveComponentSize()` reused from Material instead of a custom size constant)~~ — committed in `a22cfcd`, `4d4b055`, `6de392a`, `5033e35`, `5e24a6d`, `258b415`, `2eaea2c`, `c5e3a29`, `d07c095`

---

## Remaining Planned Work

**Detekt cleanup essentially complete.** Of the original 8 tracked `detektMetadataMain` violations, 7 are resolved (see Recently Committed). Only one remains, deliberately left as a live, unsuppressed warning rather than fixed:

| Rule | Count | Location(s) | Notes |
|---|---|---|---|
| `ForbiddenComment` | 1 | `ExampleRepositoryImpl.kt:21` (logging TODO) | Genuinely pending — resolve by implementing proper error reporting, not by suppressing |

**Next decision (raised 2026-09-18, not yet started):** whether to build a proper error-reporting mechanism next (small, self-contained — no logging library or abstraction exists yet; `println` is used ad-hoc in ~5 files) or close out the rest of `organisation/Backlog/000-Linting Epic/` (bigger: custom detekt rules or Konsist architecture tests for `No hardcoded strings`, `No .dp or .sp in files that aren't style-files`, `No @Composable annotation in data layer`, `Only one viewmodel per view`, `Presentation should never import from data layer unless in ViewModel`). `No hardcoded strings` still needs the feasibility check flagged previously (Android Lint's `HardcodedText` likely doesn't cover Compose `Text()` calls).

**`016-Run linting & tests on commit or push to main` (GitHub Actions CI) is intentionally being avoided, not deferred** — see memory `no-github-actions-ci`. This is a solo project the owner wants to be able to make private at will; any dependency on GitHub-hosted runners is seen as jeopardising that, and CI's core value (catching bad changes from other contributors) doesn't apply with a single contributor. Only revisit if a desired lint/architecture rule turns out to be strictly impossible to enforce via the local `.githooks/pre-push` hook alone — and raise that explicitly rather than adding a workflow unprompted.

**Key working agreement for this remaining work:** group fixes into small, separately-reviewable commits (not one giant commit) — mechanical/trivial fixes travel together; anything touching behaviour or requiring judgment gets its own group. Never run `git add`/`commit`/`push` directly — always hand the owner a runnable `git add && git diff --cached` command.
