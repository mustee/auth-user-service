package com.algorithms.auth.userservice.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Result {
    private List<String> errors = new ArrayList<>();
    private boolean success;

    protected Result(boolean success){
        this.success = success;
    }

    protected Result(String... errors){
        this.errors.addAll(Arrays.asList(errors));
    }

    public boolean getSuccess(){
        return success;
    }

    public boolean hasError()
    {
        return !errors.isEmpty();
    }

    public List<String> getErrors(){
        return errors;
    }

    public static Result succeeded() {
        return new Result(true);
    }

    public static Result failed(String... errors)
    {
        return new Result(errors);
    }

    @Override
    public String toString()
    {
        return success ?
                "Succeeded" :
                "Failed : " + String.join(", ", errors);
    }
}
