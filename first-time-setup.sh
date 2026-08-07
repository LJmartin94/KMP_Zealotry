#!/usr/bin/env bash
# Machine bootstrap (no argument): safe to re-run any time, every step below
# checks current state first and skips if already done.
#
# AI session setup (optional argument): pass the Copilot CLI session UUID
# (visible in the session context block at the top of the conversation) to
# also (re-)establish the plan.md -> SESSION_NOTES.md symlink for that
# session. Run this at the start of every AI session, not just once.
set -euo pipefail
cd "$(dirname "$0")"

echo "Creating skills-library symlinks (.claude, .github, .agents -> organisation/AI/skills)"
mkdir -p .github .agents .claude
[ -e .claude/skills ] || ln -s ../organisation/AI/skills .claude/skills
[ -e .github/skills ] || ln -s ../organisation/AI/skills .github/skills
[ -e .agents/skills ] || ln -s ../organisation/AI/skills .agents/skills

echo "Pointing git at the tracked .githooks directory (local repo config only)"
if [ "$(git config --get core.hooksPath || true)" != ".githooks" ]; then
  git config core.hooksPath .githooks
fi

if [ "${1:-}" != "" ]; then
  echo "Re-establishing plan.md symlink for AI session $1"
  PLAN="$HOME/.copilot/session-state/$1/plan.md"
  NOTES="$(pwd)/organisation/AI/SESSION_NOTES.md"
  rm -f "$PLAN"
  ln -s "$NOTES" "$PLAN"
else
  echo "No session ID given — skipping plan.md symlink (pass one to also do AI session setup)"
fi

echo "Setup complete."

