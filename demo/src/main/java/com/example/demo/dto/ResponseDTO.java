package com.example.demo.dto;

public class ResponseDTO {
    private String message;
    private String status;
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseDTO(String message, String status,Object obj) {
        this.message = message;
        this.status = status;
        this.data=obj;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
