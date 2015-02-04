package com.sequenia.fazzer.responses;

/**
 * Created by chybakut2004 on 04.02.15.
 */
public class Response<T> {
    private boolean success;
    private String info;
    private T data;

    public boolean getSuccess() {
        return success;
    }

    public String getInfo() {
        return info;
    }

    public T getData() {
        return data;
    }
}
