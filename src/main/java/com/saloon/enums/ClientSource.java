/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.saloon.enums;


/**
 *
 * @author richardnarh
 */
public enum ClientSource implements EnumResolver{
    WHATSAPP("WHATSAPP", "WhatsApp"),
    WALK_IN("WALK_IN", "Walk In"),
    TIK_TOK("TIK_TOK", "TikTok"),
    INSTAGRAM("INSTAGRAM", "Instagram"),
    SNAPCHAT("SNAPCHAT", "Snapchat");
    
    private final String code;
    private final String label;

    private ClientSource(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getValue() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }
    
}
