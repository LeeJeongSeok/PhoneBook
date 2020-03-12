package com.lee.phonebook;

public class Employee {

    String name;
    String age;
    String department;
    String phone;


    public Employee(String[] employee) {
        super();
        this.name = employee[0];
        this.age = employee[1];
        this.department = employee[2];
        this.phone = employee[3];
    }

    @Override
    public String toString() {
        return "[" + name + ", " + age + ", " + department + ", " + phone + "]";
    }
}
