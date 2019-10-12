cmake_minimum_required(VERSION 3.10)
project(opencv_quickstart)

set(CMAKE_CXX_STANDARD 14)

set(OPEN_CV_INCLUDE E:\\green\\opencv\\build\\include)
set(OPEN_CV_LIB E:\\green\\opencv\\build\\x64\\vc15\\lib)
set(OpenCV_LIBS opencv_core opencv_imgproc opencv_highgui opencv_imgcodecs)


include_directories(${OPEN_CV_INCLUDE})
link_directories(${OPEN_CV_LIB})
link_libraries(${OpenCV_LIBS})

add_executable(opencv_quickstart main.cpp)
target_link_libraries(opencv_quickstart ${OpenCV_LIBS})