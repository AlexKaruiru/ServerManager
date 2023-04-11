/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.servermanager.service;

import com.servermanager.model.Server;
import java.util.Collection;

/**
 *
 * @author USER
 */

public interface ServerService {
    
    Server create(Server server);
    Server ping (String ipAddress);
    Collection<Server> list (int limit);
    Server get(Long id);
    Server update (Server server);
    Boolean delete(Long id);
    
}
