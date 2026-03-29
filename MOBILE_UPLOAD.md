# Upload StreamStudio to GitHub from mobile (no PC)

## 1) Use the prepared ZIP

This repository can be exported as a single ZIP file and uploaded from your phone.

Expected ZIP filename:

- `StreamStudio-mobile-upload.zip`

Contents include the Android app source, Gradle wrapper, and GitHub Actions APK build workflow.


### Regenerate ZIP anytime

```bash
./scripts/make_mobile_zip.sh
```

This creates `StreamStudio-mobile-upload.zip` in the project root.

## 2) Create a GitHub repo on phone

1. Open the GitHub app or mobile browser.
2. Tap **+** → **New repository**.
3. Name it (example: `streamstudio-android`).
4. Keep it empty (do not initialize with README/license/gitignore).

## 3) Upload files from phone

### Option A — GitHub mobile web upload (recommended)

1. Open your new repository page.
2. Tap **Add file** → **Upload files**.
3. Select and upload the unzipped project files from your phone storage.
4. Commit to `main`.

### Option B — Use a mobile Git client

Use apps like **MGit** or **Working Copy** (iOS) to clone/push from phone.

## 4) Build APK in GitHub Actions

1. Open: `Actions` tab.
2. Run workflow: **Build Android APK**.
3. After success, download from:
   - **Artifacts**:
     - `streamstudio-debug-apk` (APK)
     - `streamstudio-project-zip` (full project ZIP)
   - **Releases** on manual runs:
     - `app-debug.apk`
     - `StreamStudio-mobile-upload.zip`

## 5) Install on Android

1. Download `app-debug.apk` on phone.
2. Enable **Install unknown apps** for your browser/GitHub app.
3. Install APK.
