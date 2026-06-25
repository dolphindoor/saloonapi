package com.saloon.entity;

import com.dolphindoors.resource.enums.Role;
import com.dolphindoors.resource.jpa.BaseModel;
import com.dolphindoors.resource.jpa.QueryBuilder;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richardnarh
 */
@Entity
@Table(name = "users")
public class User extends BaseModel implements Serializable{
    
    public static final String _employee = "employee";
    @JoinColumn(name = "employee", referencedColumnName = "id")
    @ManyToOne
    private Employee employee;
    
    public static final String _role = "role";
    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Role role;
    
    public static final String _username = "username";
    @Column(name = "username")
    private String username;
    
    public static final String _password="password";
    @Column(name = "password")
    private String password;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static QueryBuilder<User> query(EntityManager em){
        return new QueryBuilder<>(em, User.class);
    }
}
