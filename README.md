engine
------


Experimental cross platform 2d game engine for tank style games. Primary target is the Sony Playstation (psx) but should be adaptable to other platforms.

This repository contains both the code for the engine and GUI-editor.

## Setup

Integration into your psx project is quite easy, just symlink (or copy) src/c/engine.c and src/c/engine.h into your project. 

Building the editor and testing is almost as simple:

**Test suite**

`
make engine
`

**Editor**

The first time: If no JAVA_HOME environment variable is defined, either define it or create a file `src/c/.java_home` with the full path inside.

`
make
`

Notice! The editor part only builds with GNU Make, on BSD-Like Operating systems it has to be built with `gmake editor`
