package ru.masha;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CourierTest {

    private final CourierClient courierClient = new CourierClient();


    @Test
    public void courierCanBeCreated(){
        Courier courier = Courier.getRandom();
        Response response = courierClient.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .path("ok");
    }

    @Test
    public void courierCanBeDeleted(){
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        Response responseLogin = courierClient.loginCourier(new AuthCourier(courier.getLogin(), courier.getPassword()));
        int courierId = responseLogin
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
        Response responseDelete = courierClient.deleteCourier(courierId);
        responseDelete
                .then()
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void cantCreateTwoEqualLoginCourier(){
        Courier courier = Courier.getRandom();
        Response response = courierClient.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .path("ok");
        Response responseSame = courierClient.createCourier(courier);
        responseSame.then()
                .assertThat()
                .statusCode(409)
                .and()
                .extract()
                .path("message", "Этот логин уже используется. Попробуйте другой.");
    }

    @ParameterizedTest
    @ArgumentsSource(CourierTestArgumentProvider.class)
    public void cantCreateEmptyFieldCourier(Courier courier){
        Response response = courierClient.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .extract()
                .path("message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    public void courierCanLogin() {
        Courier courier = Courier.getRandom();
        Response response = courierClient.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .path("ok");
        AuthCourier authCourier = new AuthCourier(courier.getLogin(), courier.getPassword());
        Response responseAuth = courierClient.loginCourier(authCourier);
        responseAuth.then()
                .assertThat()
                .statusCode(200)
                .assertThat().body("id", Matchers.notNullValue());
        int id = responseAuth.then()
                .extract()
                .path("id");
        courierClient.deleteCourier(id);
    }

    @ParameterizedTest
    @MethodSource("argumentsStreamForLoginCourier")
    public void cantLoginCourierWithEmptyField(String login, String password){
        Response response = courierClient.loginCourier(new AuthCourier(login, password));
        response
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .extract()
                .path("message", "Недостаточно данных для входа");
    }

    @Test
    public void cantLoginWithWrongPairCredentials(){
        String login = "0";
        String password = "1";
        Response response = courierClient.loginCourier(new AuthCourier(login, password));
        response
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .extract()
                .path("message", "Учетная запись не найдена");
    }


    private static Stream<Arguments> argumentsStreamForLoginCourier() {
        return Stream.of(
                Arguments.of("", "333"),
                Arguments.of("333", ""),
                Arguments.of("", "")
        );
    }
}
