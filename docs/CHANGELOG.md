# Major Release 1.0.0

The initial release of Lodestone.

## External Changes

- Added the `Loaded` interface, and all of its standard variants.
    - Added `CommonLoaded` interface.
    - Added `ClientLoaded` interface.
    - Added `ServerLoaded` interface.
    - Added `DataGenerating` interface.
- Provided methods for safely interacting with the environment registry.
- Pre-registered environments for each added loaded interface.
- Added the `AutoLoader` class.
- Added the `LoadingPriority` annotation.
- Added the `IgnoreLoading` annotation.

## Internal Changes

- Added the `LoaderEnvironment` class.
- Added the `LoaderEnvironmentRegistry` class.
- Added a basic test mod.
