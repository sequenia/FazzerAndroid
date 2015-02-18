package com.sequenia.fazzer.requests_data;

/**
 * Created by chybakut2004 on 18.02.15.
 */
public class Device {
    private String token;
    private String platform;

    public Device() {
        platform = "android";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
