/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.servermanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

/**
 *
 * @author USER
 */

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Response {
    
    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map <?, ?> data;
    
    //represent the response that is sent to the user anytime a request is sent to the application
    //it's not necessary but the class is good to create a consistent API 
    
}
