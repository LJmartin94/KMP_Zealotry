---
name: verify-agent-claims
description: Use whenever the owner asks to verify, prove, or double-check a claim the AI has just made — "how do I know this is true", "show me", "give me a command to check this myself", or any request for independent confirmation. Also self-trigger this proactively after any claim that is consequential (a file was migrated correctly, a bug is fixed, tests pass, no data was lost) and not trivially checkable by eye. Produces a verification path the owner can run without relying on anything the AI stated.
---

# Verifying Agent Claims Independently

**Credo: trust, but verify.** Treat every prior claim the AI made — including this session's own summaries, diffs, and conclusions — as probably true but potentially false. Possible failure modes are not hypothetical edge cases, they are the default reason this skill exists:
- The AI's knowledge base may be outdated or wrong for this specific codebase/tool version.
- The AI may have misunderstood the owner's intent and solved a subtly different problem.
- The AI may have hallucinated a detail that sounds plausible but isn't grounded in anything real.
- The underlying model may have reasoned incorrectly, or a different (weaker) model may have handled an earlier step in the same session.

None of this means the AI's claim is wrong — most of the time it isn't. It means the owner should never have to take the AI's word for it when the claim is consequential and independently checkable.

## What "verification" means here

Not a restated summary, and not the AI re-running the check itself and reporting the result in prose. The owner must be able to reproduce the check **themselves**, in their own terminal, and look at the raw output with their own eyes — without reading it through the AI's paraphrase of what it means.

## The required shape of a verification response

1. **Reasoning trail first.** Briefly lay out the actual steps that led to the claim — what was compared, what was searched, what was run — so the owner can judge whether the *method* was sound, not just the conclusion.
2. **Self-contained commands.** Provide the exact command(s) needed, as a single copy-pasteable block, with no dependency on files, variables, or state that only exists in the AI's own tool session (e.g. regenerate any temp files from source-of-truth locations like `git show HEAD:<path>` rather than assuming `/tmp/whatever` the AI created earlier still exists for the owner).
3. **Primary sources only.** Ground the check in something the AI does not control the narrative of: git history (`git show`, `git log`, `git diff`), the actual file contents on disk, real command output (test runners, build tools, linters). Never make the "proof" rest on the AI's own prior message as the source of truth.
4. **Raw output, not narrated output.** When asked for a command, give the command — don't run it and paste a cleaned-up description of what it showed. Let the owner run it and read the actual output.
5. **A clear pass/fail signal.** State plainly what output would confirm the claim and what output would contradict it, so the owner doesn't have to reverse-engineer what "success" looks like from the command alone.

## Anti-patterns to avoid

- Presenting a diff or command output that the AI generated and summarizing it as proof, instead of handing over the reproduction steps.
- Verification commands that silently depend on the AI's own session state (temp files, shell variables, cached results) and quietly fail or behave differently for the owner.
- Treating "I already checked this" as equivalent to "here is how you can check this."
- Scoping the verification to only the narrow claim asked about when the underlying method would let the owner check adjacent claims too — prefer the more general, reusable check when it's just as cheap to provide.
