# AgroMechanics Pocket — Android application specification

**Project:** ATLAS pilot / Машиноведение  
**Version target:** 0.1 (offline reader)  
**Platform:** Android  
**AI:** intentionally excluded from the first release.

## Product goal

Deliver an installable Android APK that works as an offline learning handbook for agricultural machinery students in CIS countries. The app is not just a WebView wrapper: content, search, lessons and learning progress must work locally.

## First release scope

1. Home screen with subject categories.
2. Offline article reader with headings, paragraphs, lists and technical notes.
3. Search across article titles, tags and text.
4. Related-topic navigation.
5. Bookmarks and locally saved learning progress.
6. Simple multiple-choice quizzes with explanations.
7. Dark-first interface, readable typography and phone-friendly layout.
8. No account, analytics requirement, network permission or AI runtime.

## Initial content modules

- Foundations of machine studies.
- Tractors: purpose, general layout, main systems, PTO and implements.
- Combine harvesters: general layout and crop-material path.
- Engines: diesel engine, four-stroke cycle, lubrication and cooling.
- Transmission and running gear.
- Hydraulics.
- Electrical equipment.
- Maintenance and safety.

## Technical direction

- Native Android app, Kotlin.
- Jetpack Compose UI.
- Room / SQLite for structured local content and learner progress.
- FTS5 or Room-supported SQLite full-text index for offline search.
- Content distributed as a versioned local data pack. For the first prototype, a small seed dataset may be bundled in app assets and imported on first launch.
- SVG or vector drawings for simple diagrams; image assets only where they add real technical value.
- Minimum Android version: choose after build verification; initial target Android 8.0 (API 26) to cover a broad range of devices.
- No internet dependency at runtime. Build-time dependencies may be downloaded by CI.

## Data model (minimum)

- Article(id, title, section, level, summary, body, tags, sourceIds, version)
- Section(id, title, sortOrder)
- Relation(fromArticleId, toArticleId, relationType)
- Quiz(id, articleId, question, options, correctOption, explanation)
- Bookmark(articleId, createdAt)
- Progress(articleId, status, lastOpenedAt, completedAt)

Stable IDs must not depend on filenames or translated titles.

## Content quality and source policy

- Explain universal mechanical principles separately from model-specific specifications.
- Use SI units; add common legacy units only as secondary references.
- Model-specific service intervals, fluids, pressures, adjustments and repair procedures must be checked against the exact manufacturer's manual.
- Safety-critical instructions require a reliable source and editorial review.
- Cite public educational sources and manufacturer documentation. Do not copy copyrighted manuals into the package.
- Mark each article draft / reviewed / verified.

## APK delivery

- Source code stays in this repository.
- GitHub Actions builds a debug APK on pushes to the app source and allows manual runs.
- APK is uploaded as a workflow artifact. A signed release APK can be added later after a signing key is configured by the owner; never commit signing keys or passwords.
- A release package must include the APK version, content-pack version and source list.

## Acceptance criteria for v0.1

- Installs on a real Android phone.
- Opens without internet access.
- Search returns relevant local articles.
- Article reader handles long text without horizontal overflow.
- Bookmarks and progress survive app restart.
- No AI model is downloaded or loaded.
- Debug APK is downloadable from the successful GitHub Actions run.

## Development sequence

1. Build minimal native shell and CI APK workflow.
2. Add local article import and reader.
3. Add search, bookmarks and progress.
4. Add quiz engine.
5. Expand content and technical diagrams.
6. Test offline on target Android devices and measure APK / database size.
