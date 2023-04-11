/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.servermanager.repo;

import com.servermanager.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 */

@Repository
public interface ServerRepo extends JpaRepository <Server, Long>{
    
    Server findByIpAddress(String ipAddress);
    
   
    
}

 
    // note: Long is the data type of the Id column in model
    //extending JPA repository helps manage your info or data. Basically CRUD operations
    //jpa helps you create your own methods that will be equivalent to certain sql queries 
    //For this case we choose ipAddress, which will be unique