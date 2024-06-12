# Minor Patch 1.2.1

Reduce unnecessary registrations.

## Internal Changes

- Only register DataGenerating environment if the data generator is actually enabled.

# Minor Release 1.2.0

Better enforce loader restrictions.

## Content Changes

- Loading methods now throw if no mod identifiers are supplied.
- Throw an exception if the mod is initialized without any environments.
- Throw exceptions when passing null values.

---

# Minor Release 1.1.0

Better ensures that basic environments are present when loading, and improves debug logging.

## Content Changes

- Added basic startup logging.
- Logs now have a capitalized logger name.
- The `Lodestone` class is no longer extendable.
- Added basic debug logging for the environment registry.

## Internal Changes

- Basic environment registration now happens during the pre-launch stages of loading.
- Improved basic testing.
- Removed unnecessary mixin manifest.

# Minor Patch 1.1.1

Ensure that `AutoLoader` instances are skipped if annotated with `IgnoreLoading`.

## Content Changes

- Mark the mod as a library in ModMenu.

## Internal Changes

- Properly handle `IgnoreLoading` for `AutoLoader` instances.

# Minor Patch 1.1.2

Ensure that `AutoLoader` instances play nicely when also implementing a `Loaded` interface.

## Internal Changes

- Check for instances of `AutoLoader` before checking for `Loaded` inheritance.

---

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

# Minor Patch 1.0.1

Fixes JitPack builds.

## Internal Changes

- Added `jitpack.yml` to configure the Java version.
