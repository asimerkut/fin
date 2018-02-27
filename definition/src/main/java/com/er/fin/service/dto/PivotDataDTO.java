package com.er.fin.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PivotDataDTO implements Serializable {

    private List<Map<String, Object>> dataList;
    private List<String> fieldList;

    public PivotDataDTO(List<Map<String, Object>> dataList, List<String> fieldList){
        this.dataList = dataList;
        this.fieldList = fieldList;
    }

    public List<Map<String, Object>> getDataList() {
        return this.dataList;
    }

    public List<String> getFieldList() {
        return this.fieldList;
    }

}
