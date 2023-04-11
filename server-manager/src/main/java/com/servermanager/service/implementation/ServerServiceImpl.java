/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.servermanager.service.implementation;

import static com.servermanager.enumeration.Status.SERVER_DOWN;
import static com.servermanager.enumeration.Status.SERVER_UP;
import com.servermanager.model.Server;
import com.servermanager.repo.ServerRepo;
import com.servermanager.service.ServerService;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author USER
 */

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;
    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) {
        try {
            log.info("Pinging new server IP: {}", ipAddress);
            Server server = serverRepo.findByIpAddress(ipAddress);
            InetAddress address = InetAddress.getByName(ipAddress);
            server.setStatus(address.isReachable( 10000)? SERVER_UP: SERVER_DOWN);
            serverRepo.save(server);
            return server;
        } catch (IOException ex) {
            Logger.getLogger(ServerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all Servers");
         return serverRepo.findAll(PageRequest.of( 0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching all Servers by Id: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
         log.info("Updating server: {}", server.getName());
         return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
      log.info("Deleting server by Id : {}", id);
          serverRepo.deleteById(id);
          return true;
    }

    private String setServerImageUrl() {
        String [] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png", };
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames [new Random().nextInt()]).toUriString();
               // path("/server/image/" + imageNames [new Random().nextInt(bound: 4)]).toUriString();
        
    }
    
}
