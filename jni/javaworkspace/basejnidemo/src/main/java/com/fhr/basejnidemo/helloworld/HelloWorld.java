package com.fhr.basejnidemo.helloworld;

public class HelloWorld {

	static {
		System.loadLibrary("jnidemo");
	}

	public static void main(String[] args){
		HelloWorld helloWorld = new HelloWorld();
		
		helloWorld.sayHi();
		String name = "java";
		System.out.println(name);
		
		System.out.println("done");
	}

	public native void sayHi();

	public native String hello(String name);
}
