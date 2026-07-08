---
name: maintain-session-notes
description: Use at the start of a session that resumes, continues, or picks back up work on this repository — including when asked to check status, or before trusting `organisation/AI/SESSION_NOTES.md`'s references to specific files, lines, or branches. Reconciles SESSION_NOTES.md against git status and commit-anchor drift before continuing, and trims it as steps complete.
---

# Maintaining Session Notes

`organisation/AI/SESSION_NOTES.md` tracks current state only. It is not a log. The AI should prune it at the start of each session as part of setup — stale content creates noise and inflates context.

**At session start — verify before pruning:**

Run `git status` and compare the output against the "Uncommitted Changes" section of SESSION_NOTES.md. Reconcile any discrepancies first — entries that git shows as already committed should be removed, and any unstaged changes not listed should be added. Do this before any other work.

**Also verify — drift since the last known anchor:** the above check covers local uncommitted work; it does not cover commits that landed after the notes were last updated — whether from the owner working directly without the AI, a teammate's merge, a previous session, or a different machine. The notes already carry an anchor for this: take the most recent commit hash recorded in the "Recently Committed" list and run `git log <hash>..HEAD --oneline` (against the base branch instead of `HEAD` if the notes track a shared branch other than the one currently checked out). Anything listed happened without the notes' knowledge. Treat any file, line-number, or code-state reference in the notes as perishable until re-verified against the current tip — rather than trusting the doc's snapshot as still accurate — and update the anchor once reconciled. On a shared base branch, `git fetch` first (safe regardless of local uncommitted work — it only updates remote-tracking refs) so the diff also picks up commits pushed by others.

**Rules for trimming:**

- **Uncommitted changes** — when the owner commits a batch, do not remove the entry. Instead, mark it as committed with the short hash immediately (mid-session, not deferred to next session start):
  `~~Drawables.kt, Strings.kt~~ — committed in abc1234`
  Keep the last 3 committed batches visible in the list. Remove only the oldest entry when a 4th committed batch is added. This gives a new session a recent-history anchor it can verify against `git log`.
- **Step status table** — once a step is fully committed, collapse it to a single ✅ row. Detailed notes about what a step involved belong in the Step History section of `ARCHITECTURE_NOTES.md`, not in session notes.
- **Concerns table** — once all concerns are stably resolved and are unlikely to resurface, compress the table to a single summary line (e.g. "All 9 original concerns resolved ✅ — see ARCHITECTURE_NOTES.md for decisions").
- **Remaining planned work** — remove sections as steps complete.
- **Anything that has become permanently true** — if a decision or pattern has been stable across multiple sessions, it belongs in `ARCHITECTURE_NOTES.md`, not repeated in session notes.

The target state for SESSION_NOTES.md is always: the minimum information a new AI needs to understand what is currently in progress and what to do next.
