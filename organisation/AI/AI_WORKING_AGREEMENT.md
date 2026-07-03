# AI Working Agreement — Zealotry

This document establishes how the AI agent (GitHub Copilot CLI) and the repository owner work together on this project.

**Whenever conversation history is compacted or context is summarised, the AI must reread this document in full and treat its contents as explicit instruction before continuing any work.**

---

## Git Interaction

**The AI may run read-only git commands at any time:**
- `git log`, `git diff`, `git show`, `git status`, `git ls-files`, etc.

**The AI must not run write git commands:**
- No `git commit`, `git push`, `git rebase`, `git merge`, `git restore`, `git reset`, `git stash`, or any command that mutates the repository state or history.
- If a write command is needed (e.g. `git restore` to recover a deleted file), the AI must ask permission first and explain why.

All committing and pushing is done by the owner.

---

## Code Review Process

When the AI has made a set of changes, it presents them to the owner for review before committing. The review process works as follows:

### Grouping principle

Changes are grouped into small, cohesive batches that a reviewer can understand as a single unit. The primary goal is **minimising cognitive burden on the reviewer**, not reflecting the order in which changes were made.

**Grouping heuristics (in order of priority):**

1. **Trivial changes travel together.** Package/import-only changes, comment restorations, and renamed type references with no logic impact should be grouped together — even if they span different features — rather than mixed into substantive groups. A reviewer should be able to scan a trivial group in seconds.

2. **Substantive changes are grouped by concept, not by file location.** A ViewModel change and its corresponding Action change belong together. A repository interface change and its implementation change belong together.

3. **Deletions travel with their destination.** When code is deleted because it moved somewhere else, the deleted file and the destination file are reviewed together. This allows the reviewer to verify that nothing was lost, not just that something was removed.

4. **Tests travel with the code they test**, unless the test suite is large enough to warrant its own group at the end.

5. **DI and wiring changes** are typically last — they connect already-reviewed pieces and are easiest to verify once the pieces themselves are understood.

### Review commands

For each group, the AI provides a single runnable command of the form:

```bash
git add <files> && git diff --cached HEAD -- <files>
```

This stages exactly the files in the group and shows the diff. The owner can then:
- Commit the group if satisfied: `git commit`
- Unstage and request changes: `git restore --staged .`
- Ask follow-up questions before deciding

New (untracked) files must be staged with `git add` before they appear in `git diff --cached HEAD`. Deleted files must also be staged for their removal to appear in the diff.

### Before presenting groups

Before presenting review groups, the AI should identify which changes are trivial (package/import/comment only, no logic impact) by running the equivalent of:

```bash
git diff HEAD -- <file> | grep "^[+-]" | grep -v "^---\|^+++" | grep -v "^[+-]package\|^[+-]import"
```

Any file with zero output from that filter is trivial and should be grouped with other trivial files.

---

## Preserving Comments

Comments in the codebase are part of the codebase. The AI should be very hesitant to delete them.

**Illustrative comments** — explaining what a line does, why a decision was made, or providing context for a non-obvious implementation — should be preserved whenever the code they describe is still present and the comment remains accurate. If code moves to a new file, its comments move with it.

**Commented-out code** — requires judgment:
- If the context is clearly no longer relevant (e.g. the feature it relates to has been removed or fundamentally changed), it can be deleted — but see the consent rule below.
- If the commented-out code follows a similar pattern to the live code being modified, adapt it to match the new pattern and leave it commented out. Do not uncomment it as part of the adaptation.
- When in doubt, keep it.

**Misleading or actively incorrect comments** — should be updated to reflect current behaviour rather than deleted where possible. If deletion is the right call, it still requires explicit consent (see below).

**Consent rule:** Removing any comment — illustrative, decision-rationale, or commented-out code — must be raised explicitly with the owner and requires separate approval. It must not be bundled silently into a main code change.

---

Git rename detection depends on content similarity. When files are renamed or their code moves to new locations, history is only traceable if git can detect the rename. To maximise this:

### Rename-before-modify pattern

*Note: throughout this section, "commit" refers to the intended unit of work the owner will commit. The AI prepares and stages the files; the owner writes the commit message and runs `git commit`.*

When a file is being renamed AND its content is changing substantially, split the work into two commits:

1. **Rename commit:** Move the file to its new location with zero content changes. Git will detect this as a 100% similarity rename. No logic, no package declarations, no imports — pure file move.
2. **Content commit:** Apply all content changes (package declarations, imports, logic) to the already-renamed file.

This is especially important when:
- A file is being renamed or moved as part of a refactor
- A large file is being split into multiple smaller files — git can only trace one rename per deleted file (the highest content-similarity match). Identify the **primary destination** (the one receiving the most of the original file's content), move the source file to that destination first (rename commit), then create secondary destinations as new files in the content commit. The AI should explain in its group summary that a split occurred and name all destinations, so the owner can write a commit message that makes the split human-traceable even where git history is not.
- Old files are being deleted because their code has been distributed into new files — same rule applies: move to the primary destination in the rename commit, the AI documents the rest in its group summary.
- Very short files are involved, since small files fall below git's rename similarity threshold (~50%) even with minor content changes

### General principle

The owner reviews all code changes before committing. The AI should flag when a planned change risks severing file history and suggest the rename-first approach proactively.

---

## Session Notes Maintenance

`organisation/AI/SESSION_NOTES.md` tracks current state only. It is not a log. The AI should prune it at the start of each session as part of setup — stale content creates noise and inflates context.

**At session start — verify before pruning:**

Run `git status` and compare the output against the "Uncommitted Changes" section of SESSION_NOTES.md. Reconcile any discrepancies first — entries that git shows as already committed should be removed, and any unstaged changes not listed should be added. Do this before any other work.

**Rules for trimming:**

- **Uncommitted changes** — remove an entry once the relevant files have been committed. Also update mid-session: when the owner commits a batch, remove those files from the list immediately rather than waiting for the next session start.
- **Step status table** — once a step is fully committed, collapse it to a single ✅ row. Detailed notes about what a step involved belong in the Step History section of `ARCHITECTURE_NOTES.md`, not in session notes.
- **Concerns table** — once all concerns are stably resolved and are unlikely to resurface, compress the table to a single summary line (e.g. "All 9 original concerns resolved ✅ — see ARCHITECTURE_NOTES.md for decisions").
- **Remaining planned work** — remove sections as steps complete.
- **Anything that has become permanently true** — if a decision or pattern has been stable across multiple sessions, it belongs in `ARCHITECTURE_NOTES.md`, not repeated in session notes.

The target state for SESSION_NOTES.md is always: the minimum information a new AI needs to understand what is currently in progress and what to do next.

---

## Document Hierarchy

Described in `organisation/AI/INDEX.md`. That file is the single source of truth for what documents exist, what they contain, and how they relate to each other.
