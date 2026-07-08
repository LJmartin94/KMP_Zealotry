# AI Working Agreement — Zealotry

This document establishes how the AI agent (GitHub Copilot CLI) and the repository owner work together on this project.

**Whenever conversation history is compacted or context is summarised, the AI must reread this document in full and treat its contents as explicit instruction before continuing any work.**

This document governs the standing git-safety rule and collaboration stance for this repository, plus the skills library that holds the rest of the process so it loads only when relevant rather than on every turn.

---

## Pushing Back

Push back on requests or suggestions when a best-practice, idiomatic, or unbiased perspective points to a better approach — don't just execute uncritically. Assume the owner has a junior-to-intermediate level of familiarity with the specific context area being discussed and may not have already weighed the alternatives or tradeoffs. Raise concerns proactively, before implementing, not after.

This applies with extra weight when the owner asks a framing question like "or do you disagree?", "what do you think?", or "be unbiased" — answer with a genuine, reasoned opinion rather than validating whatever was proposed. The owner has final say, but back-and-forth discussion before committing to an approach consistently produces a better outcome than silent compliance.

---

## Git Interaction

**The AI may run read-only git commands at any time:**
- `git log`, `git diff`, `git show`, `git status`, `git ls-files`, etc.

**The AI must not run write git commands:**
- No `git commit`, `git push`, `git rebase`, `git merge`, `git restore`, `git reset`, `git stash`, or any command that mutates the repository state or history.
- If a write command is needed (e.g. `git restore` to recover a deleted file), the AI must ask permission first and explain why.

**Exception — keeping the local clone current:** `git fetch` never touches the working tree or local branches (it only updates remote-tracking refs), so the AI is encouraged to run it at the start of a session, and before evaluating or resuming any plan, regardless of any uncommitted or unpushed local work. This ensures the commit-anchor drift check (see the `maintain-session-notes` skill) can see commits pushed by others, not just local history.

All committing and pushing is done by the owner.

---

## Requesting Input

Whenever pausing to ask the user something — permission to run a risky command, a clarifying question, a choice between approaches — give one short line of context on why the pause is happening before asking, naming the specific part of the request that prompted it (e.g. "This includes `rm -rf build/`, which deletes the build output — safe to regenerate" or "Two approaches here have different tradeoffs") rather than posing the question cold.

Strongly favor leaving an open-ended reply path over a closed set of options. When using a structured choice tool that offers a free-text option, don't treat the listed choices as if they were exhaustive. When asking in plain text, explicitly invite a counter-proposal (e.g. "...or suggest a different approach") rather than framing the question as pick-one-of-these.

---

## Path Handling

Treat the directory a session was started in as the default scope for file access — do not reach outside it unless explicitly instructed to. When running commands, prefer paths relative to the current working directory over absolute paths — relative paths are far less likely to trigger permission prompts and keep the AI's operations visibly scoped to the repository. Where an absolute anchor is genuinely needed (e.g. the first `cd` in a fresh shell), use it once, then switch to relative paths for everything after.

---

## Skills Library

`organisation/AI/skills/` is the canonical, tool-agnostic storage for Agent Skills (the open `SKILL.md` spec), usable regardless of which tool is driving a session. `.claude/skills`, `.github/skills`, and `.agents/skills` at the repository root are symlinks into it, matching each tool's project-level discovery convention (Claude Code, GitHub Copilot CLI, and the growing set of tools that read `.agents/skills`).

**Announcing skill use:** whenever a skill fires, say so in the response text: name the skill and, in one line, what triggered it. This overrides the general preference for not narrating tool calls; skill triggering in particular should stay visible so misfires (wrong skill, or a skill that should have fired but didn't) are easy to spot.

**Bootstrap (once per machine):** run from the repository root.

```bash
mkdir -p .github .agents .claude
[ -e .claude/skills ] || ln -s ../organisation/AI/skills .claude/skills
[ -e .github/skills ] || ln -s ../organisation/AI/skills .github/skills
[ -e .agents/skills ] || ln -s ../organisation/AI/skills .agents/skills
```

### Process skills in this library

List `organisation/AI/skills/` to see what exists — each skill's own `description` frontmatter says what it covers and when to use it.

---

## Document Hierarchy

Described in `organisation/AI/INDEX.md`. That file is the single source of truth for what documents exist, what they contain, and how they relate to each other.
