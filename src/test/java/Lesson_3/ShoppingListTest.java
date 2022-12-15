package Lesson_3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ShoppingListTest extends AbstractTest {

    @Test
    void getShoppingList() {
        JsonPath connectUser = given()
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"username\": \"user013\",\n"
                        + " \"firstName\": \"firstName1\",\n"
                        + " \"lastName\": \"lastName1\",\n"
                        + " \"email\": \"email1234@gmail.com\",\n"
                        + "}")
                .when()
                .post(getBaseUrl() + "users/connect")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        String hash = connectUser.getString("hash");

        String username = connectUser.getString("username");

        given()
                .queryParam("apiKey", getApiKey())
                .pathParams("username", username)
                .queryParam("hash", hash)
                .pathParams("start-date", "2022-12-15")
                .pathParams("end-date", "2023-12-15")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/{start-date}/{end-date}")
                .then()
                .statusCode(200);

        JsonPath addShoppingList = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .pathParams("username", username)
                .body("{\n"
                        + " \"item\": \"1 chocolate\",\n"
                        + " \"parse\": true,\n"
                        + "}")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        String id = addShoppingList.getString("id");

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .pathParams("username", username)
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/shopping-list")
                .then()
                .statusCode(200);

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .pathParams("username", username)
                .pathParams("id", id)
                .delete(getBaseUrl() + "mealplanner/{username}/shopping-list/items/{id}")
                .then()
                .statusCode(200);
    }
}
