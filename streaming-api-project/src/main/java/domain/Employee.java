package domain;

public class Employee {
    private int emp_id;
    private String emp_name;
    private String designation;
    private int salary;

    public Employee() {
    }

    public Employee(int emp_id, String emp_name, String designation, int salary) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.designation = designation;
        this.salary = salary;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
