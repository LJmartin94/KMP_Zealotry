---
name: preserve-comments
description: Use before replacing any part of an existing file's content in this repository — whether via a targeted Edit-style patch, a full Write-tool rewrite that regenerates the whole file, a refactor, a reformat, or restructuring a function/class. Version A silently losing comments when Version B replaces it is not just an Edit-tool risk — a full-file rewrite drops them just as easily. Load before generating the replacement content (not only when a diff is about to be shown), and always run the verification check afterward, even for full rewrites.
---

# Preserving Comments

Comments in the codebase are part of the codebase. The AI should be very hesitant to delete them.

**Prefer surgical edits over full rewrites.** When modifying an existing file, make targeted `old_str → new_str` replacements rather than rewriting the whole file. Full rewrites are permitted when a file changes structurally enough that surgical edits would produce an unreasonably fragmented diff, but they require extra care — see the verification rule below.

**This applies to every replacement path, not just Edit.** A Write-tool call that regenerates a whole file from scratch is exactly as likely to silently drop comments as a manual edit — there is no separate, lighter rule for rewrites. Treat "I'm about to write a new version of this file" as the trigger to think about its existing comments, before the new content is generated.

**Illustrative comments** — explaining what a line does, why a decision was made, or providing context for a non-obvious implementation — should be preserved whenever the code they describe is still present and the comment remains accurate. If code moves to a new file, its comments move with it.

**Commented-out code** — requires judgment:
- If the context is clearly no longer relevant (e.g. the feature it relates to has been removed or fundamentally changed), it can be deleted — but see the consent rule below.
- If the commented-out code follows a similar pattern to the live code being modified, adapt it to match the new pattern and leave it commented out. Do not uncomment it as part of the adaptation.
- When in doubt, keep it.

**Misleading or actively incorrect comments** — should be updated to reflect current behaviour rather than deleted where possible. If deletion is the right call, it still requires explicit consent (see below).

**Consent rule:** Removing any comment — illustrative, decision-rationale, or commented-out code — must be raised explicitly with the owner and requires separate approval. It must not be bundled silently into a main code change.

**Verification rule:** Before presenting any diff that modifies an existing file, the AI must run:
```bash
git diff HEAD -- <file> | grep "^-" | grep -v "^---" | grep "//"
```
If this produces output, each removed comment must be either restored or explicitly raised with the owner for approval before the diff is presented. This check applies regardless of whether the change was a surgical edit or a full rewrite.
