package com.taf.restapi.clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class AbstractApiClient {

    static {
        RestAssured.defaultParser = Parser.JSON;
    }

    protected Response get(String url) {
        return execute(baseRequest().get(url));
    }

    protected Response get(String url, Map<String, Object> queryParams) {
        RequestSpecification request = baseRequest();

        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }

        return execute(request.get(url));
    }

    protected <V> Response post(String url, V body) {
        return execute(baseRequest().body(body).post(url));
    }

    protected <V> Response patch(String url, V body) {
        return execute(baseRequest().body(body).patch(url));
    }

    protected <V> Response delete(String url, V body) {
        return execute(baseRequest().body(body).delete(url));
    }

    protected <V> Response delete(String url) {
        return execute(baseRequest().delete(url));
    }

    private Response execute(Response response) {
        return response.then().log().all().extract().response();
    }

    private RequestSpecification baseRequest() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
    }
}


