/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saloon.dto;

import com.dolphindoors.resource.enums.Status;

/**
 *
 * @author Pascal
 */
public class AppConfigDto{
    private String id;
    private String configName;
    private String configValue;
    private Status configStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public Status getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Status configStatus) {
        this.configStatus = configStatus;
    }
    
}
