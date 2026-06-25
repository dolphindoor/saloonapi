package com.saloon.dto;

import com.saloon.enums.ClientSource;

/**
 *
 * @author richardnarh
 */
public class CustomerDto{
    private String id;
    private String code;
    private String name;
    private ClientSource clientSource;
    private String phoneNumber;
    private String email;
    private String address;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClientSource getClientSource() {
        return clientSource;
    }

    public void setClientSource(ClientSource clientSource) {
        this.clientSource = clientSource;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
