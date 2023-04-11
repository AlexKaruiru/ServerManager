/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.servermanager.controller;

import com.servermanager.enumeration.Status;
import com.servermanager.model.Response;
import com.servermanager.model.Server;
import com.servermanager.service.implementation.ServerServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.time.LocalDateTime.now;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USER
 */

@RestController
@RequestMapping ("/server")
@RequiredArgsConstructor
public class ServerController {
    
    private final ServerServiceImpl serverService; 
    
    @GetMapping("/list")
    public ResponseEntity<Response> getServers() throws InterruptedException{
       // TimeUnit.SECONDS.sleep(3);
      // throw new InterruptedException("Something went wrong"); 
       return ResponseEntity.ok(
        Response.builder()
        .timeStamp(now())
        .data(Map.of("servers", serverService.list(30)))
        .message("Servers retrieved")
        .status(OK)
        .statusCode(OK.value())
        .build()
        );
                
    }
    
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException{
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
        Response.builder()
        .timeStamp(now())
        .data(Map.of("server", server))
        .message(server.getStatus() ==Status.SERVER_UP ? "Ping success" : "Ping failed")
        .status(OK)
        .statusCode(OK.value())
        .build()
        );
                
    }
    
    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
        Response.builder()
        .timeStamp(now())
        .data(Map.of("server", serverService.create(server)))
        .message("Servers Created")
        .status(CREATED)
        .statusCode(CREATED.value())
        .build()
        );
                
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
        Response.builder()
        .timeStamp(now())
        .data(Map.of("server", serverService.get(id)))
        .message("Server retrieved")
        .status(OK)
        .statusCode(OK.value())
        .build()
        );
                
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
        Response.builder()
        .timeStamp(now())
        .data(Map.of("deleted", serverService.delete(id)))
        .message("Server deleted")
        .status(OK)
        .statusCode(OK.value())
        .build()
        );
                
    }
  
    
     @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" +fileName));
        //C:\Users\USER\Downloads\images
    }
    
}
