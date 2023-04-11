/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.servermanager.enumeration;

/**
 *
 * @author USER
 */
public enum Status {
    SERVER_UP("SERVER_UP"),
    SERVER_DOWN("SERVER_DOWN");
     
    private final String status;
    
    Status(String status) {
        this.status = status;
        
    }
    
    public String getStatus() {
        return this.status;
    }
}
