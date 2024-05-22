package ru.masha;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final RequestSpecification BASE_REQUEST_SPECIFICATION = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("https://qa-scooter.praktikum-services.ru")
            .build();

    public Response createOrder(Order order) {
        return given()
                .spec(BASE_REQUEST_SPECIFICATION)
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    public Response getAllOrdersWithParameters(GetOrderRequestParameters parameters) {
        final Map<String, Object> queryParams = new HashMap<>();
        if (parameters.getCourierId() != null) {
            queryParams.put("courierId", parameters.getCourierId());
        }
        if (parameters.getNearestStation() != null) {
            final String nearestStation = parameters.getNearestStation()
                    .stream()
                    .map(s -> "\"" + s + "\"")
                    .collect(Collectors.joining(",", "[", "]"));
            queryParams.put("nearestStation", nearestStation);
        }
        if (parameters.getLimit() != null) {
            queryParams.put("limit", parameters.getLimit());
        }
        if (parameters.getPage() != null) {
            queryParams.put("page", parameters.getPage());
        }
        return given()
                .spec(BASE_REQUEST_SPECIFICATION)
                .queryParams(queryParams)
                .when()
                .log()
                .uri()
                .get("/api/v1/orders");
    }
}
