#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
OUT_NAME="${1:-StreamStudio-mobile-upload.zip}"
OUT_PATH="$ROOT_DIR/$OUT_NAME"

cd "$ROOT_DIR"
rm -f "$OUT_PATH"

zip -r "$OUT_PATH" . \
  -x '.git/*' \
  -x 'build/*' \
  -x 'app/build/*' \
  -x '.gradle/*' \
  -x '*.zip'

echo "Created: $OUT_PATH"
