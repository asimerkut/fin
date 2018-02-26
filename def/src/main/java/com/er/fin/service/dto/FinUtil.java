package com.er.fin.service.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;

public class FinUtil {

    public static ComboSelDTO getComboSelDTO(String query){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ComboSelDTO obj = mapper.readValue(query, ComboSelDTO.class);
            return obj;
        } catch (IOException e) {
            return null;
        }
    }

    public static Pageable getPageParam(){
        return new PageRequest(0,10000, Sort.Direction.ASC, "id");
    }

}
