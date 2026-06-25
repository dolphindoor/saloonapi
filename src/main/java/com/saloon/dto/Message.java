package com.saloon.dto;

import java.util.List;

/**
 *
 * @author richardnarh
 */
public class Message {
    private List<String> numbers;
    private String textMessage;

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
    
}
