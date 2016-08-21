package ittraining.revision.com.kinbech.pojo;

import java.io.Serializable;

/**
 * Created by Akash on 2/02/2016.
 */
public class AdDetail implements Serializable {
    String id,name,address,email_id,mobile_number,price,type,detail,first_name;

    public AdDetail(String address, String detail, String email_id, String first_name, String id, String mobile_number, String name, String price, String type) {
        this.address = address;
        this.detail = detail;
        this.email_id = email_id;
        this.first_name = first_name;
        this.id = id;
        this.mobile_number = mobile_number;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


