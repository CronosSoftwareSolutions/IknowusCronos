package com.iknowus.entity;

import java.io.Serializable;

/*******
 * Created by Jorge on 07/10/2015.
 */
public class User implements Serializable{

    private String id;
    private String name;
    private String image;
    private String gender;
    private String email;
    private String birthdate;

    public User(String id, String name, String image, String gender, String email, String birthdate) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.gender = gender;
        this.email = email;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
