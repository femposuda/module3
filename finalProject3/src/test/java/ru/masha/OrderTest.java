package ru.masha;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class OrderTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    public void orderCanBeCreated(){
        Order order = Order.getRandomOrder();
        Response response = orderClient.createOrder(order);
        response
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .assertThat()
                .body("track", Matchers.notNullValue());
    }

    @ParameterizedTest
    @MethodSource("argumentsStreamForOrderColor")
    public void canChooseMultipleColorOrNoColorForOrder(List<Order.Color> colors){
        Order order = Order.getRandomOrder();
        order.setColor(colors);
        Response response = orderClient.createOrder(order);
        response
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .assertThat()
                .body("track", Matchers.notNullValue());
    }

    @ParameterizedTest
    @MethodSource("argumentsStreamForOrdersParameters")
    public void canGetListOfOrdersWithDifferentParameters(GetOrderRequestParameters parameters) {
        Response response = orderClient.getAllOrdersWithParameters(parameters);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("orders", Matchers.notNullValue());
    }

    private static Stream<GetOrderRequestParameters> argumentsStreamForOrdersParameters(){
        return Stream.of(
                new GetOrderRequestParameters(null, List.of(2, 3), null, null),
                new GetOrderRequestParameters(null, List.of(1), 2, 1),
                new GetOrderRequestParameters(26712, null, 2, 1)
        );

    }
    private static Stream<List<Order.Color>> argumentsStreamForOrderColor(){
        return Stream.of(
                List.of(Order.Color.BLACK, Order.Color.RED),
                List.of(Order.Color.RED),
                List.of()
        );
    }
}
