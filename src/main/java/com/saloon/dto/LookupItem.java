/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saloon.dto;

/**
 *
 * @author richa
 */
public class LookupItem
{
    private String value;
    private String label;

    public LookupItem() {
    }

    public LookupItem(String value, String label) {
        this.value = value;
        this.label = label;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
