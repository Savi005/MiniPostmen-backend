package com.minipostman.backend.model;

import jakarta.persistence.*;

@Entity
public class SavedRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String url;
    private String method;
    @Column(columnDefinition = "TEXT")
    private String headers;

    @Column(columnDefinition = "TEXT")
    private String body;

    public Long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getUrl(){
        return url;
    } 
    public void setUrl(String url){
        this.url = url;
    }
    public String getMethod(){
        return method;
    }
    public void setMethod(String method){
        this.method = method;
    }
    public String getHeaders(){
        return headers;
    }
    public void setHeaders(String headers){
        this.headers = headers;
    }
    public String getBody(){
        return body;
    }
    public void setBody(String body){
        this.body = body;
    }
}
