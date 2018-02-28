package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.service.dto.HelloMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/public")
public class HelloResource {

    @PostMapping("/sayHello")
    @Timed
    public ResponseEntity<Void> createDefRelation(@Valid @RequestBody HelloMessage helloMessage) throws URISyntaxException {
        System.out.println(helloMessage);
        return  ResponseEntity.ok().build();
    }

}
