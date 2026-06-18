package com.taf.restapi.models;

import lombok.Data;

@Data
public class ErrorResponse {
    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
