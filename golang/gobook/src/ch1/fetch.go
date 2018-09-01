// fetchall 依次获取url内容
package main

import (
	"os"
	"net/http"
	"fmt"
	"io/ioutil"
)

func main() {
	// 依次处理每个命令行参数
	for _, url := range  os.Args[1:]{
		// 通过http get方法获取数据
		rep, err := http.Get(url)
		// 打印错误
		if err != nil{
			fmt.Fprintf(os.Stderr, "fetch: %v\n",err)
			// 程序退出
			os.Exit(1)		}
		// 读取body
		body, err := ioutil.ReadAll(rep.Body)
		// 关闭流
		rep.Body.Close()
		if err != nil{
			fmt.Fprintf(os.Stderr, "fetch: reading %s: %v\n", url, err)
			os.Exit(1)
		}

		fmt.Printf("%s", body)
	}
}
