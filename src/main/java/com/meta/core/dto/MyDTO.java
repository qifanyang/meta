package com.meta.core.dto;

import java.util.List;
import java.util.Map;

public class MyDTO {
    private String name;
    private List<String> list = List.of("a", "s");
    private Map<String, String> map=Map.of("key", "map");
    private List<MySubDTO> dtoList = List.of(new MySubDTO(), new MySubDTO());
    private Map<String, MySubDTO> dtoMap = Map.of("xx", new MySubDTO());

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<MySubDTO> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<MySubDTO> dtoList) {
        this.dtoList = dtoList;
    }

    public Map<String, MySubDTO> getDtoMap() {
        return dtoMap;
    }

    public void setDtoMap(Map<String, MySubDTO> dtoMap) {
        this.dtoMap = dtoMap;
    }
}
