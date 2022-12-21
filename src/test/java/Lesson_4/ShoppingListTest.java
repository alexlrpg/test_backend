package Lesson_4;

import Lesson_4.Request.AddShoppingListRequest;
import Lesson_4.Request.ConnectUser;
import Lesson_4.Responce.AddShoppingListResponce;
import Lesson_4.Responce.ConnectUserResponce;
import Lesson_4.Responce.GetShoppingListResponce;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class ShoppingListTest extends AbstractTest {

    ConnectUser connectUser = new ConnectUser("user0100", "Jane", "Doe", "email@ya.com");
    AddShoppingListRequest addShoppingListRequest = new AddShoppingListRequest("1 chocolate", true);

    @Test
    void shoppingList() {
        ConnectUserResponce connectUserResponce = given()
                .contentType(ContentType.JSON)
                .body(connectUser)
                .queryParam("apiKey", getApiKey())
                .post(getBaseUrl() + "users/connect")
                .then()
                .extract()
                .body()
                .as(ConnectUserResponce.class);
        assertThat(connectUserResponce.getStatus(), equalTo("success"));

        given()
                .queryParam("apiKey", getApiKey())
                .pathParams("username", connectUserResponce.getUsername())
                .queryParam("hash", connectUserResponce.getHash())
                .pathParams("start-date", "2022-12-22")
                .pathParams("end-date", "2023-12-15")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/{start-date}/{end-date}")
                .then()
                .statusCode(200);

        AddShoppingListResponce addShoppingListResponce = given()
                .contentType(ContentType.JSON)
                .body(addShoppingListRequest)
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", connectUserResponce.getHash())
                .pathParams("username", connectUserResponce.getUsername())
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/items")
                .then()
                .extract()
                .body()
                .as(AddShoppingListResponce.class);
        assertThat(addShoppingListResponce.getName(), equalTo("chocolate"));
        assertThat(addShoppingListResponce.getMeasures().original.amount, equalTo(1.0));

        GetShoppingListResponce getShoppingListResponce = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", connectUserResponce.getHash())
                .pathParams("username", connectUserResponce.getUsername())
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/shopping-list")
                .then()
                .extract()
                .body()
                .as(GetShoppingListResponce.class);
        assertThat(getShoppingListResponce.getAisles().get(0).aisle, containsString("Sweet Snacks"));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", connectUserResponce.getHash())
                .pathParams("username", connectUserResponce.getUsername())
                .pathParams("id", addShoppingListResponce.getId())
                .delete(getBaseUrl() + "mealplanner/{username}/shopping-list/items/{id}")
                .then()
                .statusCode(200);
    }
}

