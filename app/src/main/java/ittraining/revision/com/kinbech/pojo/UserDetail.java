package ittraining.revision.com.kinbech.pojo;

import java.io.Serializable;

/**
 * Created by Akash on 28/01/2016.
 */
public class UserDetail implements Serializable {
    String id;
    String mobileNumber;
    String address;
    String firstName;
    String lastName;
    String email;


    public UserDetail(String address, String email, String firstName, String id, String lastName, String mobileNumber) {
        this.address = address;
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}