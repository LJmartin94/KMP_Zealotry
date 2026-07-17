#!/bin/bash
# Enforces rules from organisation/AI/AI_WORKING_AGREEMENT.md:
#
#   Path Handling — don't `cd` to an absolute path already inside this repo
#   when a relative path (or no `cd` at all) would do.
#
#   Script Transparency — commands that invoke an interpreter (python3, node,
#   etc.) must include at least one `echo` call so the intent of the script is
#   visible in the terminal output.
set -euo pipefail

INPUT="$(cat)"

TOOL_NAME="$(echo "$INPUT" | jq -r '.toolName // empty')"
if [ "$TOOL_NAME" != "bash" ]; then
  exit 0
fi

TOOL_ARGS_RAW="$(echo "$INPUT" | jq -r '.toolArgs // empty')"
if ! echo "$TOOL_ARGS_RAW" | jq -e . >/dev/null 2>&1; then
  exit 0
fi

COMMAND="$(echo "$TOOL_ARGS_RAW" | jq -r '.command // empty')"

deny() {
  jq -n --arg r "$1" '{permissionDecision:"deny", permissionDecisionReason:$r}'
  exit 0
}

# Repo root as resolved from this hook's own location (.github/hooks/scripts),
# not from the session's reported cwd, so it's stable regardless of where the
# calling shell currently is.
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../.." && pwd -P)"

# ── Rule: Path Handling ───────────────────────────────────────────────────────
# Match a leading `cd <absolute path>` followed by `&&`, e.g.
# `cd /Users/x/repo && git status` or `cd /Users/x/repo/sub && ls`.
if [[ "$COMMAND" =~ ^[[:space:]]*cd[[:space:]]+(/[^[:space:]]+)[[:space:]]*\&\& ]]; then
  TARGET="${BASH_REMATCH[1]}"
  # Resolve the target if it exists; otherwise compare the raw string.
  RESOLVED="$(cd "$TARGET" 2>/dev/null && pwd -P || echo "$TARGET")"
  if [[ "$RESOLVED" == "$REPO_ROOT" || "$RESOLVED" == "$REPO_ROOT"/* ]]; then
    deny "Redundant absolute cd into a path already inside this repo ($REPO_ROOT). Use a relative path instead, per the Path Handling section of organisation/AI/AI_WORKING_AGREEMENT.md."
  fi
fi

# ── Rule: Script Transparency ─────────────────────────────────────────────────
# Commands that invoke an interpreter must include at least one echo so the
# intent of the script is visible in the terminal. Matches python3, python,
# node, ruby, perl as whole words to avoid false positives on path segments.
if echo "$COMMAND" | grep -qE '\b(python3?|node|ruby|perl)\b'; then
  if ! echo "$COMMAND" | grep -qE '\becho\b'; then
    deny "Command invokes an interpreter without any explanation. Add a brief echo describing what the script does before running it, per the Script Transparency section of organisation/AI/AI_WORKING_AGREEMENT.md."
  fi
fi

exit 0
