// fetchall 并发获取url内容并报告时间和大小，这儿使用了协程做并发和channel做同步
package main

import (
	"os"
	"net/http"
	"fmt"
	"io/ioutil"
	"time"
	"io"
)

func main() {
	// 获取当前时间
	start := time.Now()
	// 定义一个通道（传递字符串类型），
	ch := make(chan string)

	// 依次处理每个命令行参数，都开辟一个新的协程来处理
	for _, url := range os.Args[1:] {
		go fetch(url, ch)
	}

	for range os.Args[1:] {
		// 从chanel中接收
		fmt.Println(<-ch)
	}

	fmt.Printf("%.2fs elapsed\n", time.Since(start).Seconds())
}

func fetch(url string, ch chan<- string) {
	// 获取当前时间
	start := time.Now()
	// 通过http get方法获取数据
	rep, err := http.Get(url)
	// 打印错误
	if err != nil {
		// 将错误信息发送到通道
		ch <- fmt.Sprint(err)
	}
	// 读取body
	nbytes, err := io.Copy(ioutil.Discard, rep.Body)
	// 关闭流
	rep.Body.Close()
	if err != nil {
		ch <- fmt.Sprintf("while reading %s: %v\n", url, err)
		os.Exit(1)
	}

	// 计算耗时
	secs := time.Since(start).Seconds()

	ch <- fmt.Sprintf("%.2fs %7d %s", secs, nbytes, url)
}
