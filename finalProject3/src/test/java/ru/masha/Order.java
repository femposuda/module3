package ru.masha;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final LocalDate deliveryDate;
    private final String comment;
    private List<Color> color;

    public Order(String firstName,
                 String lastName,
                 String address,
                 String metroStation,
                 String phone,
                 int rentTime,
                 LocalDate deliveryDate,
                 String comment,
                 List<Color> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    public static Order getRandomOrder() {
        String firstName = RandomStringUtils.random(10, true, false);
        String lastName = RandomStringUtils.random(10, true, false);
        String address = RandomStringUtils.random(20, true, true);
        String metroStation = RandomStringUtils.random(10, true, false);
        String phone = RandomStringUtils.random(15, false, true);
        int rentTime = RandomUtils.nextInt(1,10);
        LocalDate deliveryDate = LocalDate.of(LocalDate.now().getYear(), RandomUtils.nextInt(1, 13), RandomUtils.nextInt(1,30));
        String comment = RandomStringUtils.random(30, true, true);
        int colorNumber = RandomUtils.nextInt(0, Color.values().length);
        final List<Color> color = List.of(Color.values()[colorNumber]);
        return new Order(firstName,lastName,address, metroStation,phone,rentTime,deliveryDate,comment, color);
    }

    public enum Color {
        BLACK,
        GREEN,
        RED
    }
}
