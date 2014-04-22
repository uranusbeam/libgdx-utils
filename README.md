# libGDX Utils

As I progress with my game development with
[libGDX](http://libgdx.badlogicgames.com/) I will gradually add generic helper
classes here that might be useful in other game projects based on libgdx as
well.

At this point, I have absolutely no idea what I am doing here. I don't know
what helpers I will need in the future and I don't know how the helpers that
I already have will evolve. I'm currently using this in one game only, so
expect breaking API changes all the time. ;)

## Usage

When I created my libGDX project, I downloaded the latest nightly build and
executed `java -jar gdx-setup.jar`. My Java-fu is still very limited, so I
don't know any good way to "publish" this package. If you want to use this,
I suggest you clone this repository and build it yourself, then add it as
a dependency to the `core/build.gradle` file.

If anyone would like to walk me through the process of publishing this package
properly, I will be most grateful!

In order to use this:

1. Clone this repository and cd into the repository.
2. Run `gradle clean build`. You should now have a `build/libs/` folder.
3. In your libGDX game project, manipulate `core/build.gradle` and add a
   "fileTree" dependency:

```
...

dependencies {
    compile "com.badlogicgames.gdx:gdx:$gdxVersion"
    compile fileTree(dir: '../../../libgdx-utils/build/libs', include: '*.jar')
}

...
```

Note: Currently this breaks the GWT project. I know that this has something to
do with the `*.gwt.xml` files (either in the libGDX project or in this project)
but I don't know how to fix this.

## Javadoc

The javadocs are hosted on github as well and can be found [here](http://mbrochh.github.io/libgdx-utils/).
