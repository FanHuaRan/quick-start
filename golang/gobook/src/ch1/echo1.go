package main

import (
	"os"
	"fmt"
	"strings"
)

func main() {
	sep, s := " ", ""
	for i := 1; i < len(os.Args); i++ {
		s += sep + os.Args[i]
	}

	// 打印程序名 args的首元素
	fmt.Println(os.Args[0])

	// 打印参数 args从第二个元素开始
	fmt.Println(strings.Join(os.Args[1:]," "))
}
