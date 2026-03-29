# Stream Studio (Android)

A starter Android app that implements an **OBS-lite mobile workflow** for creators who want to stream directly from a phone.

## Implemented in this prototype

- Platform target presets for Twitch, Kick, YouTube, YouTube Shorts, TikTok.
- Orientation toggle (horizontal / vertical profiles).
- Scene + source model with layer properties:
  - opacity
  - chroma key flags
  - alpha/transparency flags
  - position/scale fields
- Overlay source categories for image/video/url embeds (PNG/GIF/MP4/WEBM/URL metadata).
- Foreground streaming service skeleton.
- Android internal audio capture helper (`AudioPlaybackCaptureConfiguration`) for device audio.
- UI for quickly adding scene overlays and adjusting layer opacity.

## Not yet fully wired (next iteration)

This repo provides a functioning app shell and editing flow, but not a complete production encoder/stream muxer stack yet.

Remaining production work:

1. MediaProjection permission flow + virtual display setup.
2. MediaCodec video encoder pipeline.
3. Real audio mixing (internal audio + mic) and sync.
4. RTMP/SRT output pipelines for each target (parallel publish).
5. Rich scene compositor (GPU pipeline for transforms/chroma key/alpha blends).
6. Browser/URL source rendering surface.

## Build

```bash
./gradlew assembleDebug
```

## Why this architecture

- Keeps streaming engine separate from UI so we can later swap to native/GPU accelerated pipelines.
- Scene/source model mirrors common desktop OBS concepts while staying mobile-first.
- Internal audio capture support starts at Android 10+, aligning with your device-audio requirement.


## Download APK from your phone (no PC needed)

Once this repo is on GitHub, you can get the APK directly on mobile:

1. Open **Actions** tab in GitHub app/browser.
2. Run workflow **Build Android APK** using **Run workflow**.
3. After it finishes:
   - Option A (recommended): open **Releases** and download `app-debug.apk` from the latest `StreamStudio APK ...` release.
   - Option B: open the workflow run and download artifact `streamstudio-debug-apk`.

Direct URL patterns (replace `<owner>` and `<repo>`):

- Actions page: `https://github.com/<owner>/<repo>/actions/workflows/build-apk.yml`
- Releases page: `https://github.com/<owner>/<repo>/releases`

If Android blocks install, enable **Install unknown apps** for your browser/GitHub app, then install the APK.
