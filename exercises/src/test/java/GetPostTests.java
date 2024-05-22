import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class GetPostTests {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    public void getMyInfoStatusCode() {
        given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDUzZDhjNzA4M2YzODAwNDIxMzg4MTQiLCJpYXQiOjE3MDA1MjA5MDEsImV4cCI6MTcwMTEyNTcwMX0.5yFId-TcFq-CrewxWruMwIIY-wtZ0M2ToGKQdwJB49k")
                .get("/api/users/me")
                .then().statusCode(200);
    }

    @Test
    public void getMyProfileInfo() {
        given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDUzZDhjNzA4M2YzODAwNDIxMzg4MTQiLCJpYXQiOjE3MDQ5ODA1OTQsImV4cCI6MTcwNTU4NTM5NH0.hta8Wb4NjMaiq7AOoW3e_Uw1f8KSRiegLiNf9oYEw8g")
                .get("/api/users/me")
                .then().assertThat().body("data.name", Matchers.equalTo("Аристарх Сократович"));
    }

    @Test
    public void getCards() {
        given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDUzZDhjNzA4M2YzODAwNDIxMzg4MTQiLCJpYXQiOjE3MDQ5ODA1OTQsImV4cCI6MTcwNTU4NTM5NH0.hta8Wb4NjMaiq7AOoW3e_Uw1f8KSRiegLiNf9oYEw8g")
                .get("/api/cards")
                .then().statusCode(200);
    }

    @Test
    public void checkUserActivitiesAndPrintResponse(){
        final Response response = given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDUzZDhjNzA4M2YzODAwNDIxMzg4MTQiLCJpYXQiOjE3MDQ5ODA1OTQsImV4cCI6MTcwNTU4NTM5NH0.hta8Wb4NjMaiq7AOoW3e_Uw1f8KSRiegLiNf9oYEw8g")
                .get("/api/users/me");
        response.then().assertThat().body("data.about", Matchers.equalTo("Автор автотестов"));
        response.prettyPrint();
    }

    @Test
    public void postNewCard(){
        File json = new File("src/test/resources/newCard.json");
        final Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDUzZDhjNzA4M2YzODAwNDIxMzg4MTQiLCJpYXQiOjE3MDQ5ODA1OTQsImV4cCI6MTcwNTU4NTM5NH0.hta8Wb4NjMaiq7AOoW3e_Uw1f8KSRiegLiNf9oYEw8g")
                .and()
                .body(json)
                .when()
                .post("/api/cards");
        response.then().statusCode(201)
                .and()
                .assertThat().body("data._id", Matchers.notNullValue());

    }

    @Test
    public void patchMyProfile(){
        final File jsonFile = new File("src/test/resources/newProfile.json");
        final Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDUzZDhjNzA4M2YzODAwNDIxMzg4MTQiLCJpYXQiOjE3MDQ5ODA1OTQsImV4cCI6MTcwNTU4NTM5NH0.hta8Wb4NjMaiq7AOoW3e_Uw1f8KSRiegLiNf9oYEw8g")
                .and()
                .body(jsonFile)
                .when()
                .patch("/api/users/me");
        response.then().assertThat().statusCode(200)
                .and()
                .assertThat().body("data.name", Matchers.equalTo("Василий Иванович"));

    }

    @Test
    public void postNewCardWithClass() {
        final Card superPlace = new Card("super place", "https://code.s3.yandex.net/qa-automation-engineer/java/files/paid-track/sprint1/photoSelenide.jpg");
        final Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDUzZDhjNzA4M2YzODAwNDIxMzg4MTQiLCJpYXQiOjE3MDQ5ODA1OTQsImV4cCI6MTcwNTU4NTM5NH0.hta8Wb4NjMaiq7AOoW3e_Uw1f8KSRiegLiNf9oYEw8g")
                .and()
                .body(superPlace)
                .when()
                .patch("/api/cards");
    }

    @Test
    public void patchNewProfileWithClass(){
        final Profile profile = new Profile("Cat Ivanovich", "cat");
        final Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDUzZDhjNzA4M2YzODAwNDIxMzg4MTQiLCJpYXQiOjE3MDQ5ODA1OTQsImV4cCI6MTcwNTU4NTM5NH0.hta8Wb4NjMaiq7AOoW3e_Uw1f8KSRiegLiNf9oYEw8g")
                .body(profile)
                .when()
                .patch("/api/users/me");
        response.then().assertThat().body("data.name", Matchers.equalTo("Cat Ivanovich"))
                .assertThat().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void getUserWithDeserialisation() {
        final User user = given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDUzZDhjNzA4M2YzODAwNDIxMzg4MTQiLCJpYXQiOjE3MDQ5ODA1OTQsImV4cCI6MTcwNTU4NTM5NH0.hta8Wb4NjMaiq7AOoW3e_Uw1f8KSRiegLiNf9oYEw8g")
                .get("/api/users/me")
                .body().as(User.class);
        MatcherAssert.assertThat(user, Matchers.notNullValue());
        System.out.println(user);
    }

    @Test
    public void fromJsonAndToJson() throws JsonProcessingException {
        final Profile profile = new Profile("test jackson chel", "doeba");
        String jsonChel = OBJECT_MAPPER.writeValueAsString(profile);
        System.out.println(jsonChel);

        String jsonAntichel = "{\"name\" : \"antichel\", \"about\" : \"doeba too\"}";
        final Profile profileAntichel = OBJECT_MAPPER.readValue(jsonAntichel, Profile.class);
        System.out.println(profileAntichel.getName() + profileAntichel.getAbout());

    }
}

