package com.fhr.basejnidemo.complex;

import java.io.Serializable;

public class Employee implements Serializable {
	private static final long serialVersionUID = 3813074477864666655L;
	
	static {
		System.loadLibrary("jnidemo");
	}
	
	public static void main(String[] args) {
		Employee employee = new Employee();
		employee.setAge(20);
		employee.setName("Tom");
		employee.setSalary(2000D);
		
		employee.raiseSalary(20D);
	}
	
	private String name;

	private Integer Age;

	private Double salary;

	public void printSalary(double salary) {
		System.out.println("current salary is " + salary);
	}

	public native double raiseSalary(double byPecent);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return Age;
	}

	public void setAge(Integer age) {
		Age = age;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
}
