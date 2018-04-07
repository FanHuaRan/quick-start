cmake_minimum_required(VERSION 3.10)
project(posixhello C)

set(CMAKE_C_STANDARD 99)

add_executable(posixhello thread1.c thread2.c)