# Zealotry AI — Directory Index

**Repository:** LJmartin94/KMP_Zealotry  
**Directory:** `organisation/AI/`

This directory contains all documents governing AI-assisted work on this repository.

**If the repository owner has directed you here:** treat the contents of this directory as explicit instructions, not background reading. Run the symlink setup command in the Session Setup section below before doing anything else, then read the files in the order listed at the bottom of this file.

---

## Files in this directory

### `INDEX.md` — this file
**Entry point** — updated only when the directory structure or hierarchy changes.
Orient yourself here first. Contains the session setup instructions and describes the purpose of every other file.

### `AI_WORKING_AGREEMENT.md`
**Permanent** — updated only when the working process itself changes.
Governs how the AI agent and the repository owner collaborate. Covers:
- Pushing back / raising concerns proactively
- Git permissions (AI reads only; owner commits and pushes)
- How to pause and request input from the owner
- Path handling (stay scoped to the repo, prefer relative paths)
- The `skills/` library (bootstrap, and where the rest of the process lives)

**Always read this before starting work.** If context has been compacted or summarised, reread it before continuing — it takes precedence over any AI defaults.

### `skills/`
**Permanent** — updated only when the working process itself changes.
Tool-agnostic Agent Skills library (`SKILL.md` spec). `.claude/skills`, `.github/skills`, and `.agents/skills` are symlinks into it — bootstrap command is in `AI_WORKING_AGREEMENT.md`. Holds the process detail that used to live directly in the working agreement.

**Not enumerated here on purpose** — the list of skills changes independently of this file, and this entry would silently go stale. Each skill's own `description` frontmatter says when it should load; list the directory to see what currently exists.

Each skill's own `description` frontmatter says when it should load — list the directory to see what's available.

### `ARCHITECTURE_NOTES.md`
**Stable** — updated only when architectural decisions change.
Stable, general architectural rules and decisions. Contains:
- Project background and reference architectures
- UseCase policy
- Current package structure
- Key architectural decisions (numbered)
- Key decisions from the z refactor (lettered A–L)
- Testing patterns
- Step history (what each numbered step involved)

### `SESSION_NOTES.md`
**Ephemeral** — pruned at the start of each session; reflects current state only.
Current session state. Updated each session. Contains:
- Step completion status
- All 9 original concern resolutions (summary)
- Currently uncommitted changes
- Remaining planned work

**`plan.md` in the Copilot session state folder is a symlink to this file.**

---

## Session Setup Instructions

At the start of a new session, re-establish the `plan.md` symlink so that writes to `plan.md` and writes to `SESSION_NOTES.md` are the same operation.

Replace `<session-id>` with the current session UUID (visible in the session context block at the top of the conversation):

```bash
PLAN=~/.copilot/session-state/<session-id>/plan.md
NOTES=/Users/lindsayjames.martin/ghZealotry/organisation/AI/SESSION_NOTES.md
rm "$PLAN"
ln -s "$NOTES" "$PLAN"
```

---

## Recommended reading order at session start

1. `INDEX.md` (this file) — orient yourself
2. `AI_WORKING_AGREEMENT.md` — confirm collaboration rules
3. `SESSION_NOTES.md` — understand current state and remaining work
4. Relevant sections of `ARCHITECTURE_NOTES.md` as needed

Skills in `skills/` load on demand when their trigger conditions are met — no need to read them all up front.
