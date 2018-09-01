package main

import (
	"bufio"
	"os"
	"fmt"
)

func main() {
	// 构建一个map变量，key为string value为int
	counts := make(map[string]int)

	// 获取所有文件名参数
	fileNames := os.Args[1:]

	// 无文件名参数
	if len(fileNames) == 0 {
		// 直接从标准输入流中读
		countLines(os.Stdin, counts)
	} else {
		for _, fileName := range fileNames {
			// 读取一个文件
			f, err := os.Open(fileName)
			// 含有错误就打印输出
			if err!= nil{
				fmt.Fprintf(os.Stderr, "dup2:%v\n", err)
				continue
			}
			// 从文件中读
			countLines(f,counts)
		}
	}

	// 输出重复行
	for line, n := range counts {
		if n > 1 {
			// 打印一行并格式化
			fmt.Printf("%d\t%s", n, line)
		}
	}
}

/**
 计算文件行
 */
func countLines(f *os.File, counts map[string]int) {
	// 得到标准输入流的阅读器
	input := bufio.NewScanner(f)
	// 循环得到输入，并记录到map当中
	for input.Scan() {
		counts[input.Text()]++
	}
}
