package com.pomodoro.backend;


import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class timerlogic {
    private Instant sessionStartTime; 

    private Duration seshLen = Duration.ofMinutes(1);

    private int shesCounter = 1; 

    private boolean breakTime = false;

    //sending informatio to the react frontend 
    @PostMapping("/session/start")
    public Map<String,String> startSesh(){
        sessionStartTime = Instant.now();

        //for the json request
        Map<String, String> rep = new HashMap<>();
        rep.put("message", "started");

        return rep;
    }


    @PostMapping("/session/resume")
    public Map<String ,Object> resumeSesh(){
        Map<String , Object> rep = new HashMap<>();
        long secsElapsed = Duration.between(sessionStartTime, Instant.now()).getSeconds();
        long secdsRemaining = seshLen.getSeconds() - secsElapsed;
        
        rep.put("status" , "working");

        rep.put("timeRemaining", Math.max(0, secdsRemaining));

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

            // this is where the time should be checked for 0 and if it is break time as well as increment the sesh

            if(secdsRemaining == 0){
                if(!breakTime){
                    breakTime = true;
                    if(shesCounter == 4){
                        seshLen = Duration.ofMinutes(20);

                    }
                    else{
                        seshLen = Duration.ofMinutes(5);
                    }
                    shesCounter++;

                    if(shesCounter == 5){
                        shesCounter = 0;
                    }

                    
                }
                else{
                    breakTime = false;

                    seshLen = Duration.ofMinutes(25);

                }
            }

            
        }

        return rep;
    }
    
    @PostMapping("/session/stop")
    public Map<String , Object> stopSession(){
        sessionStartTime = null;
        return Map.of("message" , "Pomo stopped");
        
    }


}
