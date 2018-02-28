package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.service.dto.Ders;
import com.er.fin.service.dto.PersonInfo;
import com.er.fin.service.dto.Plan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PersonInfoResource {



    @PostMapping("/add-new-person")
    @Timed
    @ResponseBody
    public ResponseEntity<Void> newPersonInfo(@Valid @RequestBody PersonInfo personInfo) {
        //TODO kayit servisi yazılır
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-new-plan")
    @Timed
    @ResponseBody
    public ResponseEntity<Void> newPlan(@Valid @RequestBody Plan plan) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dersList")
    @Timed
    @ResponseBody
    public List<Ders> dersList() {
        return new ArrayList<>();
    }


}
