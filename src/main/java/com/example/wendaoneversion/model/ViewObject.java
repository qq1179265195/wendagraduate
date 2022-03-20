package com.example.wendaoneversion.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
    Map<String,Object> obj = new HashMap<>();
    public void setObj(String Key,Object value){
        obj.put(Key,value);
    }
    public Object getObj(String Key){
        return obj.get(Key);
    }
}
