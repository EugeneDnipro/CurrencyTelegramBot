package com.goit.org.dto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class BankService implements Serializable {
    protected <T> List<T> convertResponseToList(String response, Class<T> bankClass) {
        Type type = TypeToken.getParameterized(List.class, bankClass).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}
