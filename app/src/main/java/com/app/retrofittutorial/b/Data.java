
package com.app.retrofittutorial.b;

import java.io.Serializable;
import javax.annotation.Generated;

import com.app.retrofittutorial.b.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data implements Serializable
{

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("token")
    @Expose
    private String token;
    private final static long serialVersionUID = 580699950684806741L;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
