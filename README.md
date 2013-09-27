JavaOne2013 (Democode for TUT6705)
==================================

**Webpage:** http://javaone2013.mihosoft.eu

<img src="http://farm4.staticflickr.com/3728/9966367865_548ef4e313_z.jpg">

## Requirements:

- Java >= 1.8 b106
- Recommended: NetBeans >= 7.4-RC1 + Gradle Plugin

## How To Build & Run

On Linux/OS X:

    cd path/to/JavaOne2013/
    ./gradlew clean run
    
On Windows:

    cd path\to\JavaOne2013
    gradlew.bat clean run
    

## Subprojects:

- MathUtils: contains utility functions for evaluating 1D & 2D functions
- SyntaxHighlighter: contains webbased text editor (based on projects by Tom Schindl)

- PlotFunction2D: plots 1D functions with Chart API
- PlutFunction3D: plots 2D functions with JavaFX 3D meshes

## Running The Subprojects

Each subproject can be executed separately by calling `./gradlew run` in the corresponding subdirectory.

## Contributions

This project makes use of third-party libraries. Thanks to all authors for sharing their code!

The LCD display has been created by Gerrit Grunwald (@hansolo_) and is available in his
[Enzo](https://github.com/HanSolo/Enzo) library.
