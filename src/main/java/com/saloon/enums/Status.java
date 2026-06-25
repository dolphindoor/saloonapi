
package com.saloon.enums;

/**
 *
 * @author richardnarh
 */
public enum Status implements EnumResolver{
    ACTIVE("ACTIVE", "Active"),
    IN_ACTIVE("IN_ACTIVE", "In Active");
    
    private final String value;
    private final String label;
    
    private Status(String value, String label)
    {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return label;
    }
}

