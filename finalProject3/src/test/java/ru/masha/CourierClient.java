package ru.masha;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final RequestSpecification BASE_REQUEST_SPECIFICATION = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("https://qa-scooter.praktikum-services.ru")
            .build();

    public Response createCourier(Courier courier) {
        return given()
                .spec(BASE_REQUEST_SPECIFICATION)
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    public Response loginCourier(AuthCourier courier) {
        return given()
                .spec(BASE_REQUEST_SPECIFICATION)
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    public Response deleteCourier(int idCourier) {
        return  given()
                .spec(BASE_REQUEST_SPECIFICATION)
                .pathParam("id", idCourier)
                .when()
                .delete("/api/v1/courier/{id}");
    }
}
