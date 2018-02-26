package com.er.fin.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PivotDataDTO implements Serializable {

	private List<Map<String, Object>> dataList;
	private Set<String> fieldSet;

	public PivotDataDTO(List<Map<String, Object>> dataList, Set<String> fieldSet){
		this.dataList = dataList;
		this.fieldSet = fieldSet;
	}

    public List<Map<String, Object>> getDataList() {
        return this.dataList;
    }

    public Set<String> getFieldSet() {
        return this.fieldSet;
    }

}
