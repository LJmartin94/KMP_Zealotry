---
name: rename-before-modify
description: Use whenever a file's path is changing in this repository, full stop — regardless of mechanism (plain `mv`, `git mv`, Write-a-new-path-then-delete-the-old-one, or any other way a file ends up at a new location), and regardless of which tool later stages it. Also applies when splitting a large file or extracting a class/function into its own file as part of a refactor. Load this before performing the move itself, not only once you've already judged the content change to be "substantial" — that judgment is what the skill helps make, and a pure rename with no special handling needed is a valid, fast outcome once loaded.
---

# Rename-Before-Modify Pattern

Git rename detection depends on content similarity. When files are renamed or their code moves to new locations, history is only traceable if git can detect the rename. To maximise this:

*Note: throughout this section, "commit" refers to the intended unit of work the owner will commit. The AI prepares and stages the files; the owner writes the commit message and runs `git commit`.*

**If it's a pure rename with no content change**, git already detects this as a 100% similarity rename automatically — just do the move, no special handling needed. The rest of this skill applies once content is also changing.

When a file is being renamed AND its content is changing substantially, split the work into two commits:

1. **Rename commit:** Move the file to its new location with zero content changes. Git will detect this as a 100% similarity rename. No logic, no package declarations, no imports — pure file move.
2. **Content commit:** Apply all content changes (package declarations, imports, logic) to the already-renamed file.

This is especially important when:
- A file is being renamed or moved as part of a refactor
- A large file is being split into multiple smaller files — git can only trace one rename per deleted file (the highest content-similarity match). Identify the **primary destination** (the one receiving the most of the original file's content), move the source file to that destination first (rename commit), then create secondary destinations as new files in the content commit. The AI should explain in its group summary that a split occurred and name all destinations, so the owner can write a commit message that makes the split human-traceable even where git history is not.
- Old files are being deleted because their code has been distributed into new files — same rule applies: move to the primary destination in the rename commit, the AI documents the rest in its group summary.
- Very short files are involved, since small files fall below git's rename similarity threshold (~50%) even with minor content changes

## General principle

The owner reviews all code changes before committing. The AI should flag when a planned change risks severing file history and suggest the rename-first approach proactively.
