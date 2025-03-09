# Patch Release 1.7.1

Fix the way that the library determines whether data generation is enabled.

### Internal Changes

- No longer assume that data generation can only happen server-side.
- Check whether the `fabric-api.datagen` property exists at all, not just that it's non-null.

---

# Minor Release 1.7.0

Adjust internals to fix support for previous game versions.

### Content Changes

- Fix string type used in the readme's dependency example.
- Add example for using latest commit as a dependency.
- Removed the LodestoneTest mod.

### Internal Changes

- Lodestone no longer depends on `fabric-api`.
- Bumped Gradle version to 8.12.

---

# Minor Release 1.6.0

Minor improvements and validation improvements.

### Content Changes

- Improved the clarity and consistency of some log messages.
- Slightly refactored code examples in the readme.

### Internal Changes

- Minor code style fixes.
- Custom environments whose interface is annotated with `@BundledLoader` now cause an exception during runtime.

---

# Patch Release 1.5.3

Updates Gradle tasks & versions.

### Content Changes

- Modrinth publishing task now sets all versions past 1.18.
- Updated Gradle to 8.8.
- Updated Loom to 1.7-SNAPSHOT.
- Fix JitPack builds.

### Internal Changes

- Resolves two of the three documentation warnings during building.

---

# Patch Release 1.5.2

Makes dependency version requirements less strict.

## Content Changes

- Lodestone now runs on 1.18 or later, as long as you have sufficient dependency versions.
- Added an explicit requirement for any version of `fabric-data-generation-api-v1`.
- Lodestone now requires any Fabric loader version past 0.4.0.
- Lodestone now requires at least Java 17 due to the usage of sealed types.

---

# Patch Release 1.5.1

Update to 1.21.1

## Content Changes

- Updated required loader version to 0.16.3.

---

# Minor Release 1.5.0

Re-license to LGPL-3.0. Improve mappings, use latest Fabric API for the test mod.

## Content Changes

- Changed project license to `LGPL-3.0-or-later`.

## Internal Changes

- Added the `release` Gradle task to automate both GitHub and Modrinth publishing.
- Updated Minecraft mappings.
- Updated Fabric API version.

---

# Minor Release 1.4.0

Update to 1.21. Set up Gradle tasks for publishing to both GitHub and Modrinth.

## Internal Changes

- Added and configured the `minotaur` plugin.
- Added and configured the `github-release` plugin.

---

# Minor Release 1.3.0

Reduces unnecessary registrations and distinguishes between bundled and added loader interfaces.

## Content Changes

- Each bundled environment interface is now annotated with `@BundledLoader`.
- Marked the entire `impl` package as `@Internal`.
- Added package-level documentation.

## Internal Changes

- Only register `DataGenerating` environment if the data generator is actually enabled.

---

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

# Patch 1.1.1

Ensure that `AutoLoader` instances are skipped if annotated with `IgnoreLoading`.

## Content Changes

- Mark the mod as a library in ModMenu.

## Internal Changes

- Properly handle `IgnoreLoading` for `AutoLoader` instances.

# Patch 1.1.2

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

# Patch 1.0.1

Fixes JitPack builds.

## Internal Changes

- Added `jitpack.yml` to configure the Java version.
