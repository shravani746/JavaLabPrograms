package smartaid.model;

public class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void showDetails() {
        System.out.println("Name: " + name + ", Age: " + age);
    }

    public String getName() {
        return name;
    }
}
