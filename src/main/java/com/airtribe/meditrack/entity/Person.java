package com.airtribe.meditrack.entity;

public abstract class Person extends MedicalEntity {
    protected String name;
    protected int age;
    protected String contact;

    public Person(String id, String name, int age, String contact) {
        super(id);
        this.name = name;
        this.age = age;
        this.contact = contact;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getContact() { return contact; }

    // Setters
    public void setName(String name) { 
        this.name = name;
        markAsUpdated();
    }
    
    public void setAge(int age) { 
        this.age = age;
        markAsUpdated();
    }
    
    public void setContact(String contact) { 
        this.contact = contact;
        markAsUpdated();
    }
}
