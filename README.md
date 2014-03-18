JavaOne2013 (Democode for TUT6705)
==================================

[![Build Status](https://travis-ci.org/miho/JavaOne2013.png?branch=master)](https://travis-ci.org/miho/JavaOne2013)

**Webpage:** http://javaone2013.mihosoft.eu

<img src="http://farm4.staticflickr.com/3728/9966367865_548ef4e313_z.jpg">

## Requirements:

- Java >= 1.8 b128
- Recommended: NetBeans >= 7.4 + Gradle Plugin

## How To Build & Run From The Command-line

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

The `PlotFunction2D` and `PlotFunction3D` subprojects can be executed separately by calling `./gradlew run` in the corresponding subdirectory.

## Dependencies

The window control comes from my VFXWindows project that is now part of the [JFXtras](http://jfxtras.org/).

The workflow visualization was done with [VWorkflows](http://vworkflows.mihosoft.eu) that will eventually replace the current UI
of [VRL-Studio](http://vrl-studio.mihosoft.eu) (an innovative visual programming IDE I develop since a few years).

## Contributions:

This project makes use of third-party libraries. Thanks to all authors for sharing their code!

The LCD display has been created by Gerrit Grunwald (@hansolo_) and is available as part of his
[Enzo](https://github.com/HanSolo/Enzo) library.

