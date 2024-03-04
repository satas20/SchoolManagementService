package com.example.demo.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class OutputMessage {
    private String from;
    private String text;
    private String time;


    public OutputMessage(String from, String text, String time) {
        this.from = from;
        this.text = text;
        this.time = time;
    }
}
