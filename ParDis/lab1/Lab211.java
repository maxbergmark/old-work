/*
Write a short program that prints "Hello world" from 
an additional thread using the Java Thread API.
*/

public class Lab211 implements Runnable {

	public void run() {
		System.out.println("Hello, World!");
	}

	public static void main(String[] args) {
		new Thread(new Lab211()).start();
	}
}