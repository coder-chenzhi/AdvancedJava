package me.chenzhi.io.serialization;

import java.io.*;
import java.util.Date;
import java.util.GregorianCalendar;




public class SerializationExample {

    public static void simple() throws IOException, ClassNotFoundException {
	Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);
	Manager carl = new Manager("Carl Cracker", 80000, 1987, 12, 15);
	carl.setSecretary(harry);
	Manager tony = new Manager("Tony Tester", 40000, 1990, 3, 15);
	tony.setSecretary(harry);
    
	Employee[] staff = new Employee[3];
    
	staff[0] = carl;
	staff[1] = harry;
	staff[2] = tony;
    
	// save all employee records to the file employee.dat         
	try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.dat"))) 
	{
	    out.writeObject(staff);
	}
    
	try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.dat")))
	{
	    // retrieve all records into a new array
    	         
	    Employee[] newStaff = (Employee[]) in.readObject();
    
	    // raise secretary's salary
	    newStaff[1].raiseSalary(10);
    
	    // print the newly read employee records
	    for (Employee e : newStaff)
		System.out.println(e);
	}
    }
    
    /**
     * Test transient keyword and override writeObject()/readObject() methods
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void testTransient() throws IOException, ClassNotFoundException {
	Person person = new Person("Bob", "male", 20);
	TransientPerson transientPerson = 
		new TransientPerson("Bob", "male", 20);
	OverrideTransientPerson overrideTransientPerson = 
		new OverrideTransientPerson("Bob", "male", 20);
	       
	try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("person.dat"))) 
	{
	    out.writeObject(person);
	}
    
	try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("person.dat")))
	{
	    // retrieve all records into a new array
    	         
	    Person retrivedPerson = (Person) in.readObject();
	    System.out.println(retrivedPerson);
	}
	
	try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("person.dat"))) 
	{
	    out.writeObject(transientPerson);
	}
    
	try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("person.dat")))
	{
	    // retrieve all records into a new array
    	         
	    TransientPerson retrivedPerson = (TransientPerson) in.readObject();
	    System.out.println(retrivedPerson);
	}
	
	try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("person.dat"))) 
	{
	    out.writeObject(overrideTransientPerson);
	}
    
	try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("person.dat")))
	{
	    // retrieve all records into a new array
    	         
	    OverrideTransientPerson retrivedPerson = 
		    (OverrideTransientPerson) in.readObject();
	    System.out.println(retrivedPerson);
	}
	
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
	testTransient();
    }
}

class Employee implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8986245573718945681L;
    private String name;
    private double salary;
    private Date hireDay;
    
    public Employee() {}
    
    public Employee(String n, double s, int year, int month, int day) {
	name = n;
	salary = s;
    	GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
    	hireDay = calendar.getTime();
    }
    
    public String getName() {
	return name;
    }
    
    public double getSalary() {
	return salary;
    }
    
    public Date getHireDay() {
	return hireDay;
    }
    
    public void raiseSalary(double byPercent) {
	double raise = salary * byPercent / 100;
	salary += raise;
    }
    
    public String toString() {
    return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay
	    + "]";
    }
}

class Manager extends Employee {
    /**
     * 
     */
    private static final long serialVersionUID = -8844146654292129552L;
    private Employee secretary;
        
    /**
     * Constructs a Manager without a secretary
     * @param n the employee's name
     * @param s the salary
     * @param year the hire year
     * @param month the hire month
     * @param day the hire day
     */
    public Manager(String n, double s, int year, int month, int day) {
	super(n, s, year, month, day);
	secretary = null;
    }
        
    /**
     * Assigns a secretary to the manager.
     * @param s the secretary
     */
    public void setSecretary(Employee s) {
	secretary = s;
    }
        
    public String toString() {
	return super.toString() + "[secretary=" + secretary + "]";
    }
}


class Person implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 8962509471632111253L;
    private String name;
    private String sex;
    private int age;
      
    public Person(String name, String sex, int age) {
	super();
	this.name = name;
	this.sex = sex;
	this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
	return "Person [name=" + name + ", sex=" + sex + ", age=" + age + "]";
    }
    
}

class TransientPerson implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 5000974449876707822L;
    private String name;
    private String sex;
    transient private int age;
      
    public TransientPerson(String name, String sex, int age) {
	super();
	this.name = name;
	this.sex = sex;
	this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
	return "TransientPerson [name=" + name + ", sex=" + sex + ", age=" + age + "]";
    }
}

class OverrideTransientPerson implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -6681222106658015833L;
    private String name;
    private String sex;
    transient private int age;
      
    public OverrideTransientPerson(String name, String sex, int age) {
	super();
	this.name = name;
	this.sex = sex;
	this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
	out.defaultWriteObject();
	out.writeInt(age);
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	in.defaultReadObject();
	age = in.readInt();
    }

    @Override
    public String toString() {
	return "OverrideTransientPerson [name=" + name + ", sex=" + sex + ", age=" + age + "]";
    }
}

