# Gio KeyMapper v0.1 — Android hardware input tester

This is an **honest first prototype**, not a PUBG keymapper. It lists keyboard/mouse devices, shows pressed keyboard keys, mouse clicks and cursor movement *inside its own app*, and displays a proposed PUBG control profile.

## Build an APK
1. Install Android Studio (with Android SDK 35).
2. Open the `GioKeyMapper` directory as a project.
3. Allow Gradle sync (requires internet for the Android Gradle plugin).
4. Build > Build APK(s).
5. Install `app/build/outputs/apk/debug/app-debug.apk` on the phone.

No SDK is present in the current execution environment, so an APK could not be compiled here. This code has not been compiled or tested on a physical device.

## Limitations
- Does not send touches or mouse movement into PUBG Mobile.
- Does not capture mouse input while another app is focused.
- Android accessibility gesture APIs are not a reliable solution for FPS-style relative mouse input.
- Third-party mapping may violate PUBG Mobile rules and risk account restrictions.
