# Contribution Guidelines

Thank you for your interest in contributing to Lodestone! Below is a list of the general rules to follow when submitting
code, data, assets, or other content to this mod.

I will be significantly more lenient with contributions from new users, just be aware that I may modify any committed
content to better fit the general guidelines. And of course, as always, I'll be around if you need any help.

## Commits and Pull Requests

Commit messages should be clear and concise. They should properly describe what was added, removed, or otherwise
modified within the commit. It is also highly preferred that your commit does not contain several large changes. Please
do your best to split large commits into several smaller ones so that the repository history is easier to understand.

Pull requests should describe in detail what is being added, removed, or otherwise changed, and why. It's expected that
they will be as sensible and informative as reasonably possible in the current context.

Neither commits nor pull requests should cause the build to fail, under any circumstances. If a change does cause a
build failure, it's expected that you will submit a fix in a follow-up commit. Pull requests that cause a build failure
will be denied until the issue is solved.

## Project Licensing and Copyright Notices

Due to this project's chosen license, all submitted content falls under
the [GNU Affero General Public License version 3 or later](../LICENSE). If you wish to submit content under another
license, please contact me directly beforehand.

It is also important that, when creating new file with an applicable content type, they should have a license disclaimer
prepended to the file contents. Applicable file types include (but are not limited to) Java source files (`.java`),
Gradle build files (`.gradle`), or script files (`.sh`, `.bat`). There is a copyright header template available to you
within the [`LICENSE_TEMPLATE`](../assets/LICENSE_TEMPLATE.txt) file in the `assets` directory.

## Changelog Updates

Any and all changes made to the project should be reflected within the [`CHANGELOG.md`](CHANGELOG.md) file contained
within this directory. Any changes that impact external content, such as added blocks, items, or data types, should be
reflected within the current version's "External Changes" section. Any changes that impact internal content, such as
data generators, packet types, or other content that is not exposed to the end-user should be reflected within the
"Internal Changes" section.

Version headers should also contain an accurate summary of what changed in the current version. If the description is no
longer correct after a change, please update it.

This project follows [Semantic Versioning 2.0.0](https://semver.org/spec/v2.0.0.html), and any version changes must
follow its rules.

## Asset Conventions

This is a non-exhaustive list of the expected rules for contributed assets. This exists to ensure that added content is
both high quality and consistent. If you feel that these rules are incomplete or should be modified, feel free to make a
pull request.

### Images

- Textures must be sized to a power of two (e.g., 16×16, 32×32, 64×64, etc.).
- Art must be considered "higher quality". This is a relatively subjective rule, so all that I ask is that you do your
  best. I may modify art to better suit this project if you give me permission to do so.
- Images should attempt to appear vanilla-like. If it feels out of place, I may ask you to change the texture.
- Images should be in the PNG format (`.png`).

### Models

- Custom models should appear vanilla-like. If it feels out of place, I may ask you to update the model.
- Models must have a clear amount of effort put into them.
- File sizes should generally not exceed 50 KB for the sake of mod size.
- All models should be in the JSON format (`.json`).

### Sounds

- Audio should be high quality (no buzzing, popping, etc.).
- Sounds should be trimmed to have as little bounding silence as possible.
- File sizes should generally not exceed 2 MB for the sake of mod size.
- All sounds should be in the Ogg format (`.ogg`).

## Programming Conventions

This is a non-exhaustive and relatively pedantic list of the expected rules for contributed source code. This exists to
ensure that added code is high quality, consistent, performant, and easy to understand. If you feel that these rules are
incomplete or should be modified, feel free to make a pull request, and if there is any confusion, feel free to ask me
directly or view pre-existing files within the repository.

If your editor supports IntelliJ code styling, a [Lodestone profile](../assets/intellij-code-style.xml) is provided
for you within the `assets` directory.

If your editor supports Eclipse Java code styling, a [Lodestone profile](../assets/eclipse-code-style.xml) is provided
for you within the `assets` directory.

### Data

- All data files should be in the JSON format (`.json`).
- Always indent using four spaces, not tabs.
- Lines should (almost) never exceed 120 characters. Anything that exceeds this limit should be 'chopped down'.
- Objects may be single-line if they only contain one entry.
- Arrays may be single-line if they do not exceed the character limit.
- Single-line objects and arrays should contain spaces as padding.

### Source Code

- All source files should be in the Java format (`.java`)
- Code should be as clear and concise as possible.
    - All types, fields, and methods should be fully documented.
    - Logic should be easy to follow. If something is confusing or obscure, it should be explained in a comment.
    - The usage of `var` is completely prohibited, unless dealing with an absurdly long or complex type.
    - Non-static field and member accesses should always be prefixed with `this.` when within the source object.
        - e.g., `this.callOtherMethod()` and `this.containedField += 1`.
    - Static field and member accesses should never be prefixed with the class' name when within the source type.
        - e.g., `callStaticMethod()` and `staticField.callMethod()`.
    - Due to the possible confusion due to pre-fixed vs post-fixed `++`, `+= 1` should always be used instead.
- Code should have consistent formatting.
    - Indentations should always be 4 spaces, and doubled-up indentation is never used.
    - Lines should (almost) never exceed 120 characters. Anything that exceeds this limit should be 'chopped down'.
    - Line endings are always Unix-style (LF), *not* Windows-style (CRLF).
    - Empty brace pairs never extend onto multiple lines, with the exception of type definitions such as classes.
    - If / For / While statements may *only* be single line if they could be considered 'simple'.
        - e.g., `if (predicate.test()) return`, `while (list.size() < 5) list.add(0)`.
    - Ternary statements must also be considered 'simple', and may never be nested.
    - Multi-line If / For / While statements *must* use enclosing brace pairs. This is mostly for my own sake, as I tend
      to have trouble reading these without the pairs.
    - Binary operators, such as `+`, `%`, `^=`, etc., must always be surrounded by spaces.
    - Commas and keywords that are followed by another token should always be followed by a space.
    - Multi-line items must *never* be aligned using any kind of padding. I find that this tends to hinder readability.
- Names must be consistent and clear.
    - All methods and fields must be named in the camelCase format, with the exception of static constants and enum
      constants, which use SCREAMING_SNAKE_CASE.
    - Interfaces *do not* start with the letter `I`.
    - Mixin class names should always be the name of the target class with `Mixin` appended onto it.
        - e.g., `ItemStackMixin` and `LightningEntityMixin`.
    - Accessors also follow this rule, having `Accessor` appended onto the target class.
        - e.g., `PlayerEntityAccessor` and `AbstractFurnaceBlockEntityAccessor`.
    - Method names should accurately describe their functionality. This is most important for Mixin methods.
        - e.g., `getLoaderId` and `redirectGetIconCall`.
- Code should be as safe and performant as possible.
    - Fields, methods, and inner classes should be marked as `private` unless wider access is necessary.
    - Methods that are known to throw must provide a `throws` clause.
    - Repeated or unnecessary allocations should be avoided if at all possible.
    - Supplier fields should be memoized if at all possible.
    - Any field that is allowed to be null should be annotated with `@Nullable`, and any field that *must never* be null
      should be annotated with `@NotNull`.
    - The usage of `Optional` over `@Nullable` is greatly encouraged.
    - Classes that should not be extended should be marked as `final` or `sealed`.
    - Interfaces that should not be implemented should be annotated with `@NonExtendable`.
    - Methods that should not be overridden should be marked as `final`.
    - Fields or variables that should not be re-assigned to should be marked as `final`.
    - Types used within `catch` blocks should be as exact as possible.
        - *Never* do this: `catch (Throwable throwable) {}`
