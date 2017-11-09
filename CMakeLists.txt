cmake_minimum_required(VERSION 2.8.4)
project(DriverTests)

set(CMAKE_CXX_STANDARD 11)

# Gogole Test
# Download and unpack googletest at configure time
configure_file(CMakeLists.txt.in googletest-download/CMakeLists.txt)
execute_process(COMMAND ${CMAKE_COMMAND} -G "${CMAKE_GENERATOR}" .
        RESULT_VARIABLE result
        WORKING_DIRECTORY ${CMAKE_BINARY_DIR}/googletest-download )
if(result)
    message(FATAL_ERROR "CMake step for googletest failed: ${result}")
endif()
execute_process(COMMAND ${CMAKE_COMMAND} --build .
        RESULT_VARIABLE result
        WORKING_DIRECTORY ${CMAKE_BINARY_DIR}/googletest-download )
if(result)
    message(FATAL_ERROR "Build step for googletest failed: ${result}")
endif()

# Add googletest directly to our build. This defines
# the gtest and gtest_main targets.
add_subdirectory(${CMAKE_BINARY_DIR}/googletest-src
        ${CMAKE_BINARY_DIR}/googletest-build
        EXCLUDE_FROM_ALL)

# Andor SDK 3
find_path(
        ANDOR_INC
        NAMES "atcore.h")
find_library(
        ANDOR_LIB
        NAMES "atcore")


set(SOURCE_FILES
        andor3/tests.cc
        andor3/connection.cpp)

include_directories(${ANDOR_INC} ${gtest_SOURCE_DIR}/include)
add_executable(DriverTests ${SOURCE_FILES})
target_link_libraries(DriverTests gtest_main ${ANDOR_LIB})
