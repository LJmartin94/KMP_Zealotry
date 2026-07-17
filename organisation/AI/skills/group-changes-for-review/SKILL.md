---
name: group-changes-for-review
description: Use when the AI has made a set of code changes in this repository and is about to present them to the owner for review before committing — wrapping up a task, handing off a batch of edits, or being asked "show me what changed." Covers grouping changes into reviewable batches, telling trivial changes apart from substantive ones, and producing the git stage+diff command for each group.
---

# Grouping Changes for Review

When the AI has made a set of changes, it presents them to the owner for review before committing. The review process works as follows:

## Grouping principle

Changes are grouped into small, cohesive batches that a reviewer can understand as a single unit. The primary goal is **minimising cognitive burden on the reviewer**, not reflecting the order in which changes were made.

**Grouping heuristics (in order of priority):**

1. **Trivial changes travel together.** Package/import-only changes, comment restorations, renamed type references with no logic impact, and **linting/formatting auto-fixes** should be grouped together — even if they span different features — rather than mixed into substantive groups. A reviewer should be able to scan a trivial group in seconds.

2. **Substantive changes are grouped by concept, not by file location.** A ViewModel change and its corresponding Action change belong together. A repository interface change and its implementation change belong together.

3. **Deletions travel with their destination.** When code is deleted because it moved somewhere else, the deleted file and the destination file are reviewed together. This allows the reviewer to verify that nothing was lost, not just that something was removed.

4. **Tests travel with the code they test**, unless the test suite is large enough to warrant its own group at the end.

5. **DI and wiring changes** are typically last — they connect already-reviewed pieces and are easiest to verify once the pieces themselves are understood.

## Review commands

For each group, the AI provides a single runnable command of the form:

```bash
git add <files> && git diff --cached HEAD -- <files>
```

This stages exactly the files in the group and shows the diff. The owner can then:
- Commit the group if satisfied: `git commit`
- Unstage and request changes: `git restore --staged .`
- Ask follow-up questions before deciding

New (untracked) files must be staged with `git add` before they appear in `git diff --cached HEAD`. Deleted files must also be staged for their removal to appear in the diff.

## Linting sequencing

**Do not run linting auto-fixes until all logical changes for the current task are approved and committed.** Linting is typically run sporadically rather than on every save, which means a linting pass can touch files that are simultaneously being edited for logical reasons — producing diff noise that makes logical changes harder to review.

The correct sequence is:
1. Implement logical changes → present for review → owner commits
2. *Then* run the linting auto-fixer (e.g. `./gradlew ktlintFormat`) as a separate step
3. Present the linting-only diff as its own group → owner commits

If the owner asks to run `./gradlew composeApp:check` during development and it fails on linting violations, report the violations but **do not auto-fix them inline** — note that a linting-only commit should follow once the logical work is approved.

## Before presenting groups

Before presenting review groups, the AI should identify which changes are trivial (package/import/comment only, no logic impact) by running the equivalent of:

```bash
git diff HEAD -- <file> | grep "^[+-]" | grep -v "^---\|^+++" | grep -v "^[+-]package\|^[+-]import"
```

Any file with zero output from that filter is trivial and should be grouped with other trivial files.
