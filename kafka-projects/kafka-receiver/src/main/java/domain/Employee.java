package main.java.domain;

public class Employee {
	private int empId;
	private String name;
	private String designation;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public Employee(int empId, String name, String designation) {
		super();
		this.empId = empId;
		this.name = name;
		this.designation = designation;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
