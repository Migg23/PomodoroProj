package com.pomodoro.backend;


import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class timerlogic {
    private Instant sessionStartTime; 

    private Duration seshLen = Duration.ofMinutes(25);

    //sending informatio to the react frontend 
    @PostMapping("/session/start")
    public Map<String,String> startSesh(){
        sessionStartTime = Instant.now();

        //for the json request
        Map<String, String> rep = new HashMap<>();
        rep.put("message", "started");

        return rep;
    }

    @GetMapping("/session/status")
    public Map<String , Object> getSessionStatus(){
        Map<String , Object> rep = new HashMap<>();

        if(sessionStartTime == null){
            rep.put("status", "not working");

        }
        else{
            long secsElapsed = Duration.between(sessionStartTime, Instant.now()).getSeconds();
            long secdsRemaining = seshLen.getSeconds() - secsElapsed;

            rep.put("status", "working");
            rep.put("timeRemaining" , Math.max(0, secdsRemaining));
        }

        return rep;
    }
    
    @PostMapping("/session/stop")
    public Map<String , Object> stopSession(){
        sessionStartTime = null;
        return Map.of("message" , "Pomo stopped");
        
    }


}
