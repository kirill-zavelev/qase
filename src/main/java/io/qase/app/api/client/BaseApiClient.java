package io.qase.app.api.client;

import io.qase.app.ui.util.PropertiesLoader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseApiClient {

    private static final String BASE_URL = "https://api.qase.io";
    private static final String TOKEN = "Token";
    private final Properties properties;

    public BaseApiClient() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;
        this.properties = PropertiesLoader.loadProperties();
    }

    public <R> R post(String path, Object body, Class<R> responseClass) {
        return getRequestSpecification()
                .header(TOKEN, properties.getProperty("standard.token"))
                .body(body)
                .when()
                .post(path)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(responseClass);
    }

    public Response get(String path, Map<String, ?> parameterNameValuePairs) {
        return getRequestSpecification()
                .header(TOKEN, properties.getProperty("standard.token"))
                .pathParams(parameterNameValuePairs)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response get(String path, int limit) {
        return getRequestSpecification()
                .header(TOKEN, properties.getProperty("standard.token"))
                .queryParam("limit", limit)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response delete(String path, Map<String, ?> parameterNameValuePairs) {
        return getRequestSpecification()
                .header(TOKEN, properties.getProperty("standard.token"))
                .pathParams(parameterNameValuePairs)
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
    }

    protected static RequestSpecification getRequestSpecification() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}
