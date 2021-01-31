package com.taskmanager.springboot.app.util;

public enum StatusEnum {

    COMPLETED("1"), UNCOMPLETED("0");

    private String codigo;

    StatusEnum (String codigo){
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
