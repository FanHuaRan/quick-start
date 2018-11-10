package main

import "fmt"

func counter(out chan<- int) {
	for x := 0; x < 10; x++ {
		out <- x
	}
	close(out)
}

func squarer(out chan<- int, in <-chan int) {
	for v := range in {
		out <- v * v
	}
	close(out)
}

func printer(in <-chan int) {
	for v := range in {
		fmt.Println(v)
	}
}

func main() {
	//定义两个int类型的channel
	naturals := make(chan int)
	squares := make(chan int)

	//产生两个Fiber,用go关键字
	go counter(naturals)
	go squarer(squares, naturals)
	//获取计算结果
	printer(squares)
}

