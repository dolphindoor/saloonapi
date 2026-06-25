package com.saloon.entity;

import com.dolphindoors.resource.enums.Status;
import com.dolphindoors.resource.jpa.BaseModel;
import com.dolphindoors.resource.jpa.QueryBuilder;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author 
 */
@Entity
@Table(name = "employees")
public class Employee extends BaseModel implements Serializable{
    
    public static final String _fullName = "fullName";
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "email")
    private String email;
    
    public static final String _phoneNumber = "phoneNumber";
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "address")
    private String address;
    
    public static final String _userCode = "userCode";
    @Column(name = "user_code")
    private String userCode;
    
    public static final String _status = "status";
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public static QueryBuilder<Employee> query(EntityManager em){
        return new QueryBuilder(em, Employee.class);
    }
}
