# Lodestone

![Build status](https://github.com/Jaxydog/Lodestone/actions/workflows/build.yml/badge.svg)

An automatic content loader for Fabric mods.

### Usage

In order to use Lodestone directly, please ensure that
you [add it as a dependency for your mod](#depending-on-lodestone).

### Installation

Lodestone's most up-to-date JAR file may be downloaded from
its [GitHub Releases page](https://github.com/Jaxydog/Lodestone/releases). This mod will also be available
on [Modrinth](https://modrinth.com/mod/lodestone-lib), if that is preferred, once it is more feature-complete.

Once downloaded, the JAR file must be placed within your client's `mods` directory, and the game should be run using
the [Fabric Loader](https://fabricmc.net/).

Lodestone requires the following mods to run properly, which, if not present along-side it, will prevent the game from
launching:

- [Fabric API](https://modrinth.com/mod/fabric-api)

Alternatively, you may build Lodestone from its source code by running the following commands in order. To build
from Lodestone's source code, you must have both Java 21 and Git installed.

```sh
git clone https://github.com/Jaxydog/Lodestone.git
cd ./Lodestone/
./gradlew build
```

The compiled JAR files will be located within the `./build/libs/` directory.

### Depending on Lodestone

Lodestone's main purpose is to be used as a library for other mods. If you would like to depend on Lodestone for your
Fabric mod, add the following to your Gradle manifest:

```properties
# gradle.properties
lodestone_version = 1.0.0
```

```groovy
// build.gradle

repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    modImplementation 'com.github.jaxydog:Lodestone:${project.lodestone_version}'
}
```

### Contributing

Contributions are always welcome! If you're interested in helping development, please read this
project's [Contribution Guidelines](docs/CONTRIBUTING.md). If you ever need help, feel free to contact me here or on
Discord, and I'll do my best to aid you.

### License

Lodestone is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
version.

Lodestone is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
details.

You should have received a copy of the GNU Affero General Public License along with Lodestone (located
within [LICENSE](./LICENSE)). If not, see <[https://www.gnu.org/licenses/](https://www.gnu.org/licenses/)>.
