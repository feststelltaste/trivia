# Transcript Viewer

`transcript-viewer.html` is a self-contained single-file HTML page that parses and displays Claude Code session transcripts.

## Usage

1. Open `transcript-viewer.html` in any browser
2. Drag and drop a `.jsonl` transcript file onto it, or use the file picker

Transcript files are stored at:
```
~/.claude/projects/<project-slug>/<session-id>.jsonl
```

## What it shows

- Chat-style view of the full conversation (user and assistant turns)
- Tool calls (Edit, Write, Read, Bash, …) as collapsible blocks showing input and output
- Hook output highlighted with **PASS/FAIL badges** and color-coded diffs
