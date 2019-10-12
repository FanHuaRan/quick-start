#include <opencv2/opencv.hpp>
#include <iostream>

using namespace std;
using namespace cv;

int main() {
    Mat img = imread("E:\\discountposter\\test.png");
	if (img.empty()) {
		cout << "打开图像失败！" << endl;
		return -1;
	}
	namedWindow("image");
	imshow("image", img);
	waitKey();

	return 0;
}