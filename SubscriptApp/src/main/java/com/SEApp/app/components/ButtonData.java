package com.SEApp.app.components;

import java.util.function.Function;

public class ButtonData {
    private String name;

    private Function<Integer, Void> callback;


    public ButtonData(String name, Function<Integer, Void> callback){
        this.name = name;
        this.callback = callback;
    }

    public String getName(){
        return name;
    }

    public Function<Integer, Void> getCallback(){
        return callback;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setCallback(Function<Integer, Void> callback){
        this.callback = callback;
    }

}
