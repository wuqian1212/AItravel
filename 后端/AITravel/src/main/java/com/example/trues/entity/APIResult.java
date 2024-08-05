package com.example.trues.entity;

public class APIResult<T> {
    private Integer statusCode;
    private String message;
    private T data;

    public APIResult(T data) {
        this.statusCode = 200;
        this.message = "结果获取成功";
        this.data = data;
    }

    public APIResult(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
