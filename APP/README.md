# AgroMechanics Pocket — Android app

This folder contains the native Android application source for the offline machine-studies learning archive.

The first milestone is a small, installable APK, not a full ATLAS implementation. AI is deliberately out of scope.

See [APP_SPEC.md](APP_SPEC.md) for the product scope, local data model, source policy and APK delivery requirements.

## Build

The Android project and GitHub Actions workflow are being added in stages. The intended output is a debug APK attached to a successful Actions run. A release-signed APK will require a signing key supplied securely by the repository owner; secrets must never be committed.
