package com.saloon.entity;

import com.dolphindoors.resource.jpa.BaseModel;
import com.dolphindoors.resource.jpa.QueryBuilder;
import com.dolphindoors.resource.utilities.JUtils;
import com.saloon.enums.ClientSource;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author richardnarh
 */
@Entity
@Table(name = "customers")
public class Customer extends BaseModel implements Serializable{
    @Column(name = "code")
    private String code = JUtils.generate(5);
    
    public static final String _name = "name";
    @Column(name = "name")
    private String name;
    
    public static final String _phoneNumber = "phoneNumber";
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    @Lob
    private String description;
    
    public static final String _clientSource = "clientSource";
    @Column(name = "client_source")
    @Enumerated(EnumType.STRING)
    private ClientSource clientSource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ClientSource getClientSource() {
        return clientSource;
    }

    public void setClientSource(ClientSource clientSource) {
        this.clientSource = clientSource;
    }
    
    public static QueryBuilder<Customer> query(EntityManager em){
        return new QueryBuilder<>(em,Customer.class);
    }
}
