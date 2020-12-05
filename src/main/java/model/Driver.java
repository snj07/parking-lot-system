package model;

/**
 * Driver data model for storing driver's data
 */
public class Driver {

    public Driver(int age) {
        this.age = age;
    }

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
