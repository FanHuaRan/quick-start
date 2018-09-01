// dup1
package main

import (
	"bufio"
	"os"
	"fmt"
)

func main() {
	// 构建一个map变量，key为string value为int
	counts := make(map[string]int)
	// 得到标准输入流的阅读器
	input := bufio.NewScanner(os.Stdin)
	// 循环得到输入，并记录到map当中
	for input.Scan() {
		counts[input.Text()]++
	}

	for line, n := range counts {
		if n >1 {
			// 打印一行并格式化
			fmt.Printf("%d\t%s",n,line)
		}
	}
}
