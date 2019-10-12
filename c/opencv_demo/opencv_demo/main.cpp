#include <opencv2/opencv.hpp>
#include <iostream>
#include "opencv2/opencv.hpp"
#include <time.h>
#include "main.h"


using namespace std;
using namespace cv;

int main(int argc, char **argv)
{
	clock_t start = clock();
	for (int i = 0; i < 1000; i++) {
		generate_image();
	}
	clock_t end = clock();
	printf("cost:%d ms \n", end - start);
	system("pause");
}







void generate_image()
{
	//Mat类是用来存放图像的数据结构（图像信息 以及像素矩阵)
	// 背景图
	Mat image = imread("F:\\myspace\\imgtoolkit\\src\\test\\resources\\1920_1080.jpeg");
	// 叠加log图
	Mat logoImage = imread("F:\\myspace\\imgtoolkit\\src\\test\\resources\\water.jpg");

	Mat* imagePointer = &image;

	Rect rect(0, 0, logoImage.cols, logoImage.rows);

	// 场景一个ROI(第一个参数表示插入目标图片，Rect构造函数的前两个参数插入图片的左上角位置，后两个参数表示宽度和高度)
	Mat imageROI(image, rect);
	//图片插入感兴趣区
	logoImage.copyTo(imageROI);

	// 加文字水印，opencv_imgproc.putText（图片，水印文字，文字位置，字体，字体大小，字体颜色，字体粗度，文字反锯齿，是否翻转文字）
	Point point(310, 350);
	Scalar scalar(255, 0, 0, 0);

	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);


	//namedWindow("test");//定义窗口
	//imshow("test", image);//显示窗口
	//waitKey(0); // 等待输入

	// 保存图片
	imwrite("F:\\overlay_text.png", image);
}
