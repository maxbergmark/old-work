# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.2

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list

# Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files (x86)\CMake\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files (x86)\CMake\bin\cmake.exe" -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars

# Include any dependencies generated for this target.
include CMakeFiles/Starlab.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Starlab.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Starlab.dir/flags.make

CMakeFiles/Starlab.dir/skeleton.cpp.obj: CMakeFiles/Starlab.dir/flags.make
CMakeFiles/Starlab.dir/skeleton.cpp.obj: CMakeFiles/Starlab.dir/includes_CXX.rsp
CMakeFiles/Starlab.dir/skeleton.cpp.obj: skeleton.cpp
	$(CMAKE_COMMAND) -E cmake_progress_report C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars\CMakeFiles $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building CXX object CMakeFiles/Starlab.dir/skeleton.cpp.obj"
	C:\MinGW\bin\g++.exe   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles\Starlab.dir\skeleton.cpp.obj -c C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars\skeleton.cpp

CMakeFiles/Starlab.dir/skeleton.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Starlab.dir/skeleton.cpp.i"
	C:\MinGW\bin\g++.exe  $(CXX_DEFINES) $(CXX_FLAGS) -E C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars\skeleton.cpp > CMakeFiles\Starlab.dir\skeleton.cpp.i

CMakeFiles/Starlab.dir/skeleton.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Starlab.dir/skeleton.cpp.s"
	C:\MinGW\bin\g++.exe  $(CXX_DEFINES) $(CXX_FLAGS) -S C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars\skeleton.cpp -o CMakeFiles\Starlab.dir\skeleton.cpp.s

CMakeFiles/Starlab.dir/skeleton.cpp.obj.requires:
.PHONY : CMakeFiles/Starlab.dir/skeleton.cpp.obj.requires

CMakeFiles/Starlab.dir/skeleton.cpp.obj.provides: CMakeFiles/Starlab.dir/skeleton.cpp.obj.requires
	$(MAKE) -f CMakeFiles\Starlab.dir\build.make CMakeFiles/Starlab.dir/skeleton.cpp.obj.provides.build
.PHONY : CMakeFiles/Starlab.dir/skeleton.cpp.obj.provides

CMakeFiles/Starlab.dir/skeleton.cpp.obj.provides.build: CMakeFiles/Starlab.dir/skeleton.cpp.obj

# Object files for target Starlab
Starlab_OBJECTS = \
"CMakeFiles/Starlab.dir/skeleton.cpp.obj"

# External object files for target Starlab
Starlab_EXTERNAL_OBJECTS =

Starlab.exe: CMakeFiles/Starlab.dir/skeleton.cpp.obj
Starlab.exe: CMakeFiles/Starlab.dir/build.make
Starlab.exe: c:/MinGW/lib/libSDLmain.a
Starlab.exe: c:/MinGW/lib/libSDL.dll.a
Starlab.exe: CMakeFiles/Starlab.dir/linklibs.rsp
Starlab.exe: CMakeFiles/Starlab.dir/objects1.rsp
Starlab.exe: CMakeFiles/Starlab.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --red --bold "Linking CXX executable Starlab.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\Starlab.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Starlab.dir/build: Starlab.exe
.PHONY : CMakeFiles/Starlab.dir/build

CMakeFiles/Starlab.dir/requires: CMakeFiles/Starlab.dir/skeleton.cpp.obj.requires
.PHONY : CMakeFiles/Starlab.dir/requires

CMakeFiles/Starlab.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\Starlab.dir\cmake_clean.cmake
.PHONY : CMakeFiles/Starlab.dir/clean

CMakeFiles/Starlab.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars C:\Users\Max\Dropbox\Programmering\DD2323\CgLab1\Stars\CMakeFiles\Starlab.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Starlab.dir/depend

