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
CMAKE_SOURCE_DIR = C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2

# Include any dependencies generated for this target.
include CMakeFiles/SecondLab2.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/SecondLab2.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/SecondLab2.dir/flags.make

CMakeFiles/SecondLab2.dir/skeleton.cpp.obj: CMakeFiles/SecondLab2.dir/flags.make
CMakeFiles/SecondLab2.dir/skeleton.cpp.obj: CMakeFiles/SecondLab2.dir/includes_CXX.rsp
CMakeFiles/SecondLab2.dir/skeleton.cpp.obj: skeleton.cpp
	$(CMAKE_COMMAND) -E cmake_progress_report C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2\CMakeFiles $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building CXX object CMakeFiles/SecondLab2.dir/skeleton.cpp.obj"
	C:\MinGW\bin\g++.exe   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles\SecondLab2.dir\skeleton.cpp.obj -c C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2\skeleton.cpp

CMakeFiles/SecondLab2.dir/skeleton.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/SecondLab2.dir/skeleton.cpp.i"
	C:\MinGW\bin\g++.exe  $(CXX_DEFINES) $(CXX_FLAGS) -E C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2\skeleton.cpp > CMakeFiles\SecondLab2.dir\skeleton.cpp.i

CMakeFiles/SecondLab2.dir/skeleton.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/SecondLab2.dir/skeleton.cpp.s"
	C:\MinGW\bin\g++.exe  $(CXX_DEFINES) $(CXX_FLAGS) -S C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2\skeleton.cpp -o CMakeFiles\SecondLab2.dir\skeleton.cpp.s

CMakeFiles/SecondLab2.dir/skeleton.cpp.obj.requires:
.PHONY : CMakeFiles/SecondLab2.dir/skeleton.cpp.obj.requires

CMakeFiles/SecondLab2.dir/skeleton.cpp.obj.provides: CMakeFiles/SecondLab2.dir/skeleton.cpp.obj.requires
	$(MAKE) -f CMakeFiles\SecondLab2.dir\build.make CMakeFiles/SecondLab2.dir/skeleton.cpp.obj.provides.build
.PHONY : CMakeFiles/SecondLab2.dir/skeleton.cpp.obj.provides

CMakeFiles/SecondLab2.dir/skeleton.cpp.obj.provides.build: CMakeFiles/SecondLab2.dir/skeleton.cpp.obj

# Object files for target SecondLab2
SecondLab2_OBJECTS = \
"CMakeFiles/SecondLab2.dir/skeleton.cpp.obj"

# External object files for target SecondLab2
SecondLab2_EXTERNAL_OBJECTS =

SecondLab2.exe: CMakeFiles/SecondLab2.dir/skeleton.cpp.obj
SecondLab2.exe: CMakeFiles/SecondLab2.dir/build.make
SecondLab2.exe: c:/MinGW/lib/libSDLmain.a
SecondLab2.exe: c:/MinGW/lib/libSDL.dll.a
SecondLab2.exe: CMakeFiles/SecondLab2.dir/linklibs.rsp
SecondLab2.exe: CMakeFiles/SecondLab2.dir/objects1.rsp
SecondLab2.exe: CMakeFiles/SecondLab2.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --red --bold "Linking CXX executable SecondLab2.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\SecondLab2.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/SecondLab2.dir/build: SecondLab2.exe
.PHONY : CMakeFiles/SecondLab2.dir/build

CMakeFiles/SecondLab2.dir/requires: CMakeFiles/SecondLab2.dir/skeleton.cpp.obj.requires
.PHONY : CMakeFiles/SecondLab2.dir/requires

CMakeFiles/SecondLab2.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\SecondLab2.dir\cmake_clean.cmake
.PHONY : CMakeFiles/SecondLab2.dir/clean

CMakeFiles/SecondLab2.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2 C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2 C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2 C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2 C:\Users\Max\Dropbox\Programmering\DD2323\CgLab2\CMakeFiles\SecondLab2.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/SecondLab2.dir/depend

