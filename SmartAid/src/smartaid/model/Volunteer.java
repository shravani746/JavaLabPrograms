package smartaid.model;

import smartaid.interfaces.Displayable;

public class Volunteer extends Person implements Displayable {
    private String areaAssigned;

    public Volunteer(String name, int age, String areaAssigned) {
        super(name, age);
        this.areaAssigned = areaAssigned;
    }

    @Override
    public void display() {
        System.out.println("Volunteer -> Name: " + name + ", Age: " + age + ", Area: " + areaAssigned);
    }
}
