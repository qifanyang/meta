package com.meta.core.dto;

import java.util.List;
import java.util.Map;

public class MySubDTO {

    private String name;
    private List<String> list = List.of("a", "s");
    private Map<String, String> map=Map.of("key", "map");

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
}
