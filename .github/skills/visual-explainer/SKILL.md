---
name: visual-explainer
description: Create visual explanations (Mermaid diagrams, concise architecture maps, and flow summaries) for tickets, features, and code paths.
---

# Visual Explainer

Use this skill when the user asks for:
- architecture diagrams
- sequence or flow explanations
- visual ticket recaps
- component relationship maps

## Workflow

1. Read the relevant source files and/or ticket artifacts.
2. Extract entities, boundaries, and data/control flow.
3. Generate a Mermaid diagram that is accurate and minimal.
4. Add a short written explanation focused on decisions and tradeoffs.

## Output Guidelines

- Prefer `flowchart` for architecture and handler/router relationships.
- Prefer `sequenceDiagram` for request lifecycles.
- Use short node labels and avoid duplicating prose from docs.
- Keep diagrams deterministic and directly grounded in repository files.
