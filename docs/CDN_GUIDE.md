# Pokemon Rumble Rush — CDN Recreation & Patching Guide

This guide details how to build and maintain the CDN asset trees for both **Android** and **iOS** clients running against the generic Pokeland C# server.

---

## 1. Background & Architecture

Only the iOS asset tree (`<AssetVer>/iOS`) of *Pokemon Rumble Rush* version 1.6.0 was captured by the Wayback Machine. The official Android asset tree was lost.

To support Android clients, the Android asset tree must be transcoded from the iOS bundles. Additionally, several asset bugs existed in the final retail release (missing localized welcome calendars, text bugs, missing gear sprite references) that must be patched for both platforms.

```
                      Archived 1.6.0 iOS Tree
                                 │
           ┌─────────────────────┴─────────────────────┐
           ▼                                           ▼
   Android Transcoder                          iOS Patcher
(dokiscramble_repack_cdn.py)                (patch_ios_cdn.py)
   ├── PVRTC -> ETC2_RGBA8                     ├── Clone l10n variants
   ├── GLES20 -> GLES3Plus shaders             ├── Patch MSBT texts
   ├── Clone l10n variants                     ├── Generate Welcal bospw2..4
   ├── Content bugfixes                        ├── Add GEAR sprite
   └── size_manifest.json                      └── size_manifest.json
           │                                           │
           ▼                                           ▼
  <AssetVer>/Android/                         <AssetVer>/iOS/
```

---

## 2. Android CDN Recreation (`standalone/dokiscramble_repack_cdn.py`)

The standalone script `dokiscramble_repack_cdn.py` reproduces the entire Android CDN pipeline autonomously.

### Prerequisites

Python 3.10+ with the following packages:
```bash
pip install UnityPy==1.25.4 etcpak texture2ddecoder pillow lz4
```

### Usage

```bash
python standalone/dokiscramble_repack_cdn.py \
    --ios /path/to/archived/iOS \
    --out /path/to/output/Android \
    --fixes dokiheroes_py \
    --jobs 8
```

### Parameters

- `--ios`: Directory containing the archived iOS tree (where the root manifest `iOS` is located).
- `--out`: Destination directory for the Android CDN tree. Must be empty or nonexistent unless `--overwrite` is specified.
- `--fixes`: Path containing game master data (`dokiheroes_py`), required for Step 5 (content fixes).
- `--jobs`: Number of parallel worker processes (defaults to CPU core count).
- `--overwrite`: Allow overwriting an existing `--out` directory.
- `--no-verify`: Skip bundle verification check at the end.

### Pipeline Stages

1. **Format Conversion (`PVRTC` -> `ETC2`)**:
   - Updates `target_platform` in every `SerializedFile` from `iOS` (9) to `Android` (13).
   - Re-encodes all PowerVR (`PVRTC_RGB2`, `PVRTC_RGBA2`, `PVRTC_RGB4`, `PVRTC_RGBA4`) textures into `ETC2_RGBA8` compatible with standard Android GPUs.
   - Packs assets using the original single LZMA block format.

2. **Shader Promotion (`GLES20` -> `GLES3Plus`)**:
   - Patches Unity shaders targeting `GLES20` to `GLES3Plus`:
     - Platform tag `5` (`GLES20`) -> `9` (`GLES3Plus`).
     - Subprogram program type `5` -> `4` (re-compressed with LZ4HC).
     - Retains Metal shader data for cross-platform fidelity.

3. **Manifest Creation**:
   - The converted root manifest is generated as both `iOS` and `Android` (`<AssetVer>/Android/Android`), matching client download requests.

4. **Localization Variants**:
   - Fills unarchived language illustration bundles (`illustrations/l10n.<lang>`) from the base English art (`illustrations/l10n.en-us`), retaining proper CAB identities.

5. **Content Fixes**:
   - **Text Bundles**: Patches 8-language MSBT bundles in `text/<lang>` with missing strings (zones, tournament cups, species names, gear, honors).
   - **Welcome Calendars**: Builds `welcal/bospw2`, `welcal/bospw3`, and `welcal/bospw4` bundles from `welcal/bosp` with dedicated CABs, prefabs, and day layouts.
   - **Gaiji & Gear Sprites**: Injects the `GEAR` sprite into the TextMeshPro sprite asset in `fonts/common`, and re-points the battle scene drop label (`scene-assets/battle`).
   - Recomputes MD5 file hashes for modified bundles into the `Android` root manifest.

6. **Size Manifest (`size_manifest.json`)**:
   - Generates compact, alphabetical `size_manifest.json` indexing every asset with prefix `Android/<path>`.

7. **Verification**:
   - Validates that no PVRTC textures remain, all shaders report `GLES3Plus`, and all file sizes match `size_manifest.json`.

---

## 3. iOS CDN Patching (`tools/patch_ios_cdn.py`)

For native iOS clients, re-encoding textures or modifying shader platforms is **not** necessary. However, the content bug fixes must still be applied.

### Usage

```bash
python tools/patch_ios_cdn.py \
    --ios /path/to/archived/iOS \
    --out /path/to/output/iOS
```

Or patch an existing directory in-place:
```bash
python tools/patch_ios_cdn.py \
    --ios /path/to/iOS \
    --out /path/to/iOS \
    --in-place
```

### What It Does

1. Clones missing `l10n` illustration variants without altering target platform (remains iOS 9).
2. Injects MSBT text fixes, calendar bundles (`bospw2..4`), and the TextMeshPro `GEAR` sprite.
3. Updates bundle hashes in the `iOS` root manifest.
4. Generates `size_manifest.json` with the `iOS/<path>` prefix.
5. Verifies all files remain tagged for iOS (platform 9).

---

## 4. Serving the CDN Assets

### Directory Layout

Configure your web server to serve the following hierarchy under the CDN root:

```
cdn-root/
└── <AssetVer>/
    ├── Android/
    │   ├── Android                  <- Root manifest
    │   ├── size_manifest.json
    │   ├── scene-assets/
    │   ├── fonts/
    │   ├── text/
    │   └── ...
    └── iOS/
        ├── iOS                      <- Root manifest
        ├── size_manifest.json
        ├── scene-assets/
        ├── fonts/
        ├── text/
        └── ...
```

### Nginx Example Configuration

```nginx
server {
    listen 443 ssl http2;
    server_name cdn.pokeland.example.com;

    root /var/www/cdn;

    # Gzip / Brotli settings
    gzip on;
    gzip_types application/json application/octet-stream;

    # Asset bundle caching
    location ~* \.(json)$ {
        add_header Cache-Control "public, max-age=300";
        add_header Access-Control-Allow-Origin *;
    }

    location / {
        add_header Cache-Control "public, max-age=86400, immutable";
        add_header Access-Control-Allow-Origin *;
        try_files $uri =404;
    }
}
```

### Pointing the Server to the CDN

The C# server (`Pokeland.Server`) communicates CDN paths to clients during `Login`:
- `Res.AssetHost`: Hostname/base URL of your CDN (e.g., `cdn.pokeland.example.com`).
- `Res.AssetVer`: Asset version directory (matches `<AssetVer>`).
- `Res.AssetPath`: Subpath template or directory.

Clients download `size_manifest.json` on boot, check hashes against their local cache, and fetch modified or missing bundles.
