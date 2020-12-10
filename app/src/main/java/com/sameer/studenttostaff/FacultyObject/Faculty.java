package com.sameer.studenttostaff.FacultyObject;

public class Faculty {

    String Name;
    String Contact_Number;
    String Department;
    String CabinNumber;
    String Post;
    String College;
    String Location;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }


    public Faculty(String name, String contact_Number, String department, String cabinNumber, String post, String college,String location) {
        Name = name;
        Contact_Number = contact_Number;
        Department = department;
        CabinNumber = cabinNumber;
        Post = post;
        College = college;
        Location = location;


        setName(name);
        setContact_Number(contact_Number);
        setDepartment(department);
        setCabinNumber(cabinNumber);
        setPost(post);
        setCollege(college);
    }

    public Faculty() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContact_Number() {
        return Contact_Number;
    }

    public void setContact_Number(String contact_Number) {
        Contact_Number = contact_Number;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getCabinNumber() {
        return CabinNumber;
    }

    public void setCabinNumber(String cabinNumber) {
        CabinNumber = cabinNumber;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String college) {
        College = college;
    }
}
