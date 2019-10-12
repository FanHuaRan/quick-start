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
	//Mat�����������ͼ������ݽṹ��ͼ����Ϣ �Լ����ؾ���)
	// ����ͼ
	Mat image = imread("F:\\myspace\\imgtoolkit\\src\\test\\resources\\1920_1080.jpeg");
	// ����logͼ
	Mat logoImage = imread("F:\\myspace\\imgtoolkit\\src\\test\\resources\\water.jpg");

	Mat* imagePointer = &image;

	Rect rect(0, 0, logoImage.cols, logoImage.rows);

	// ����һ��ROI(��һ��������ʾ����Ŀ��ͼƬ��Rect���캯����ǰ������������ͼƬ�����Ͻ�λ�ã�������������ʾ��Ⱥ͸߶�)
	Mat imageROI(image, rect);
	//ͼƬ�������Ȥ��
	logoImage.copyTo(imageROI);

	// ������ˮӡ��opencv_imgproc.putText��ͼƬ��ˮӡ���֣�����λ�ã����壬�����С��������ɫ������ֶȣ����ַ���ݣ��Ƿ�ת���֣�
	Point point(310, 350);
	Scalar scalar(255, 0, 0, 0);

	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);
	putText(image, "eguid!", point, FONT_ITALIC, 23, scalar, 10, 2, false);


	//namedWindow("test");//���崰��
	//imshow("test", image);//��ʾ����
	//waitKey(0); // �ȴ�����

	// ����ͼƬ
	imwrite("F:\\overlay_text.png", image);
}
