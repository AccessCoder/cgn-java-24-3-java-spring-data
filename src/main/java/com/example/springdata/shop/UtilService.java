package com.example.springdata.shop;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UtilService {

    public String generateRandomId(){
        return UUID.randomUUID().toString();
    }

    public Instant generateTimestamp(){
        return Instant.now();
    }
}
