# libGDX Utils

Currently there is nothing to see here. As I progress with my game development
with [libGDX](http://libgdx.badlogicgames.com/) I will gradually add generic
helper classes here that might be useful in other game projects based on libgdx
as well.

In order to use this:

1. Clone this repository and cd into the repository.
2. Run `gradle clean build`. You should now have a `build/libs/` folder.
3. In your libGDX game project, manipulate `core/build.gradle` and add a file
   tree dependency:

    #!txt
    // ...
    dependencies {
        compile "com.badlogicgames.gdx:gdx:$gdxVersion"
        compile fileTree(dir: '../../../libgdx-utils/build/libs', include: '*.jar')
    }
    // ...
