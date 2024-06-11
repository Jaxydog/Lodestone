# Lodestone

![Build status](https://github.com/Jaxydog/Lodestone/actions/workflows/build.yml/badge.svg)

An automatic content loader for Fabric mods.

### Usage

In order to use Lodestone directly, please ensure that
you [add it as a dependency for your mod](#depending-on-lodestone). This mod does not add any content by itself.

Lodestone provides basic interfaces and APIs for easily registering modded content during initialization. The most
important type provided is the `Loaded` interface, which the mod is built around. For a basic example, see the
basic [test mod](https://github.com/Jaxydog/Lodestone/tree/main/src/test).

To create an automatically registered type, simply implement the target environment's associated interface.

```java
/** An item that is loaded at runtime. */
public class LoadedItem extends Item implements CommonLoaded {

    // Required for all instances of `Loaded`. Allow Lodestone to sort registered values by mod identifier.
    @Override
    public Identifier getLoaderId() {
        return Identifier.of("your_mod", "your_item");
    }

    // A function that registers the value at runtime.
    @Override
    public void loadCommon() {
        Registry.register(Registries.ITEM, this.getLoaderId(), this);
    }

}
```

Each mod environment has its own dedicated interface:

- `CommonLoaded` loads the value on the "common" environment, meaning both the client *and* the server.
- `ClientLoaded` *only* loads the value on the client instance.
- `ServerLoaded` *only* loads the value on the server instance.
- `DataGenerating` *only* loads the value during data generation.

These are intended to be used within each mod initializer to load it properly.

```java
public class YourMod implements ModInitializer {

    public static final LoadedItem YOUR_ITEM = new LoadedItem(new Settings());

    @Override
    public void onInitialize() {
        // Registers this item for later loading.
        Lodestone.register(CommonLoaded.class, YOUR_ITEM);

        // Which is then done here.
        Lodestone.load(CommonLoaded.class, "your_mod");
    }

}
```

Lodestone also provides an abstract class and two annotations for automatic registration of values.

```java
public final class ItemLoader extends AutoLoader {

    public static final LoadedItem ITEM_1 = new LoadedItem("item_1", new Settings());
    public static final LoadedItem ITEM_2 = new LoadedItem("item_2", new Settings());
    public static final LoadedItem ITEM_3 = new LoadedItem("item_3", new Settings());
    public static final LoadedItem ITEM_4 = new LoadedItem("item_4", new Settings());
    public static final LoadedItem ITEM_5 = new LoadedItem("item_5", new Settings());

    @Override
    public Identifier getLoaderId() {
        return Identifier.of("your_mod", "items");
    }

}
```

This is then registered in a very similar way.

```java
public class YourMod implements ModInitializer {

    public static final ItemLoader ITEMS = new ItemLoader();

    @Override
    public void onInitialize() {
        // Registers all items for later loading.
        ITEMS.register();

        // Which is then done here.
        Lodestone.load(CommonLoaded.class, "your_mod");
    }

}
```

### Installation

Lodestone's most up-to-date JAR file may be downloaded from
its [GitHub Releases page](https://github.com/Jaxydog/Lodestone/releases). This mod will also be available
on [Modrinth](https://modrinth.com/mod/lodestone-lib), if that is preferred, once it is more feature-complete.

Once downloaded, the JAR file must be placed within your client's `mods` directory, and the game should be run using
the [Fabric Loader](https://fabricmc.net/).

Alternatively, you may build Lodestone from its source code by running the following commands in order. To build
from Lodestone's source code, you must have both Java 21 and Git installed.

```sh
git clone https://github.com/Jaxydog/Lodestone.git
cd ./Lodestone/
./gradlew build
```

The compiled JAR files will be located within the `./build/libs/` directory.

### Depending on Lodestone

Lodestone's sole purpose is to be used as a library for other mods. If you would like to depend on Lodestone for your
Fabric mod, add the following to your Gradle manifest:

```properties
# gradle.properties

lodestone_version = 1.0.1
```

```groovy
// build.gradle

repositories {
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
