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
CMAKE_SOURCE_DIR = "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3"

# Include any dependencies generated for this target.
include CMakeFiles/ThirdLab.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/ThirdLab.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/ThirdLab.dir/flags.make

CMakeFiles/ThirdLab.dir/skeleton.cpp.obj: CMakeFiles/ThirdLab.dir/flags.make
CMakeFiles/ThirdLab.dir/skeleton.cpp.obj: CMakeFiles/ThirdLab.dir/includes_CXX.rsp
CMakeFiles/ThirdLab.dir/skeleton.cpp.obj: skeleton.cpp
	$(CMAKE_COMMAND) -E cmake_progress_report "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3\CMakeFiles" $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building CXX object CMakeFiles/ThirdLab.dir/skeleton.cpp.obj"
	C:\MinGW\bin\g++.exe   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles\ThirdLab.dir\skeleton.cpp.obj -c "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3\skeleton.cpp"

CMakeFiles/ThirdLab.dir/skeleton.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/ThirdLab.dir/skeleton.cpp.i"
	C:\MinGW\bin\g++.exe  $(CXX_DEFINES) $(CXX_FLAGS) -E "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3\skeleton.cpp" > CMakeFiles\ThirdLab.dir\skeleton.cpp.i

CMakeFiles/ThirdLab.dir/skeleton.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/ThirdLab.dir/skeleton.cpp.s"
	C:\MinGW\bin\g++.exe  $(CXX_DEFINES) $(CXX_FLAGS) -S "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3\skeleton.cpp" -o CMakeFiles\ThirdLab.dir\skeleton.cpp.s

CMakeFiles/ThirdLab.dir/skeleton.cpp.obj.requires:
.PHONY : CMakeFiles/ThirdLab.dir/skeleton.cpp.obj.requires

CMakeFiles/ThirdLab.dir/skeleton.cpp.obj.provides: CMakeFiles/ThirdLab.dir/skeleton.cpp.obj.requires
	$(MAKE) -f CMakeFiles\ThirdLab.dir\build.make CMakeFiles/ThirdLab.dir/skeleton.cpp.obj.provides.build
.PHONY : CMakeFiles/ThirdLab.dir/skeleton.cpp.obj.provides

CMakeFiles/ThirdLab.dir/skeleton.cpp.obj.provides.build: CMakeFiles/ThirdLab.dir/skeleton.cpp.obj

# Object files for target ThirdLab
ThirdLab_OBJECTS = \
"CMakeFiles/ThirdLab.dir/skeleton.cpp.obj"

# External object files for target ThirdLab
ThirdLab_EXTERNAL_OBJECTS =

ThirdLab.exe: CMakeFiles/ThirdLab.dir/skeleton.cpp.obj
ThirdLab.exe: CMakeFiles/ThirdLab.dir/build.make
ThirdLab.exe: c:/MinGW/lib/libSDLmain.a
ThirdLab.exe: c:/MinGW/lib/libSDL.dll.a
ThirdLab.exe: CMakeFiles/ThirdLab.dir/linklibs.rsp
ThirdLab.exe: CMakeFiles/ThirdLab.dir/objects1.rsp
ThirdLab.exe: CMakeFiles/ThirdLab.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --red --bold "Linking CXX executable ThirdLab.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\ThirdLab.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/ThirdLab.dir/build: ThirdLab.exe
.PHONY : CMakeFiles/ThirdLab.dir/build

CMakeFiles/ThirdLab.dir/requires: CMakeFiles/ThirdLab.dir/skeleton.cpp.obj.requires
.PHONY : CMakeFiles/ThirdLab.dir/requires

CMakeFiles/ThirdLab.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\ThirdLab.dir\cmake_clean.cmake
.PHONY : CMakeFiles/ThirdLab.dir/clean

CMakeFiles/ThirdLab.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3" "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3" "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3" "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3" "C:\Users\Max\Google Drive\Programmering\DD2323\CgLab3\CMakeFiles\ThirdLab.dir\DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/ThirdLab.dir/depend

