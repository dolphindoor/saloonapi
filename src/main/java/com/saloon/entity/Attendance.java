package com.saloon.entity;

import com.dolphindoors.resource.jpa.BaseModel;
import com.dolphindoors.resource.jpa.QueryBuilder;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richardnarh
 */
@Entity
@Table(name = "attendance")
public class Attendance extends BaseModel implements Serializable{
        
    public static final String _employee = "employee";
    @JoinColumn(name = "employee", referencedColumnName = "id")
    @ManyToOne
    private Employee employee;
    
    @Column(name = "user_image")
    private String userImage;
    
    public static final String _attendanceTime = "attendanceTime";
    @Column(name = "attendance_time")
    private LocalDateTime attendanceTime;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }
   
    public static QueryBuilder<Attendance> query(EntityManager em){
        return new QueryBuilder(em, Attendance.class);
    }
}
