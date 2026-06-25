package com.saloon.dto;

import java.io.InputStream;

/**
 *
 * @author richardnarh
 */
public class AttendanceDto {
    private String id;
    private String userCode;
    private String userImage;
    private InputStream file;
    private String imageName;
    private String timeRecorded;
    private String fullName;
    private boolean isLate;

    public AttendanceDto() {
    }

    public AttendanceDto(String userCode, String userImage) {
        this.userCode = userCode;
        this.userImage = userImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getTimeRecorded() {
        return timeRecorded;
    }

    public void setTimeRecorded(String timeRecorded) {
        this.timeRecorded = timeRecorded;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public boolean isIsLate() {
        return isLate;
    }

    public void setIsLate(boolean isLate) {
        this.isLate = isLate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }
}
