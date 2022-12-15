package Lesson_3;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SearchAndClassifyTest extends AbstractTest {

    @Test
    @DisplayName("Поиск с параметрами minCaffeine, includeIngredients, titleMatch")
    void getSearchRecipes() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("minCaffeine", 50)
                .queryParam("includeIngredients", "chocolate")
                .queryParam("titleMatch", "Chocolate")
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("results[0].nutrition.nutrients[0].amount", greaterThan(50F))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");
    }

    @Test
    @DisplayName("Поиск с параметрами query и diet")
    void getSearchRecipes2() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .queryParam("diet", "vegetarian")
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");
    }

    @Test
    @DisplayName("Поиск с параметрами query, diet, maxReadyTime, minCalories")
    void getSearchRecipes3() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "meat")
                .queryParam("diet", "gluten free")
                .queryParam("maxReadyTime", 10)
                .queryParam("minCalories", 300)
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("results[0].nutrition.nutrients[0].amount", greaterThan(300F))
                .body("results[0].nutrition.nutrients[0].name", equalTo("Calories"))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");
    }

    @Test
    @DisplayName("Поиск с параметрами includeIngredients, instructionsRequired, sortDirection, number")
    void getSearchRecipes4() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeIngredients", "cucumber,meat")
                .queryParam("instructionsRequired", "true")
                .queryParam("sortDirection", "asc")
                .queryParam("number", 3)
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("results[0].title", equalTo("Loaded Macaroni Salad"))
                .body("number", equalTo(3))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");
    }

    @Test
    @DisplayName("Поиск с параметрами addRecipeInformation, addRecipeNutrition, number")
    void getSearchRecipes5() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeInformation", "true")
                .queryParam("addRecipeNutrition", "true")
                .queryParam("number", 1)
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("results[0].title", equalTo("Cauliflower, Brown Rice, and Vegetable Fried Rice"))
                .body("results[0].veryPopular", is(true))
                .body("results[0].glutenFree", is(true))
                .body("number", equalTo(1))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");
    }

    @Test
    @DisplayName("Проверка классификации рецепта с параметрами language=en, title=salsa")
    void getClassifyCuisine() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "en")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "salsa")
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("cuisine", equalTo("Mexican"))
                .when()
                .post(getBaseUrl() + "recipes/cuisine");
    }

    @Test
    @DisplayName("Проверка классификации рецепта с параметрами title=burger")
    void getClassifyCuisine2() {
        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "burger")
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("cuisine", equalTo("American"))
                .when()
                .post(getBaseUrl() + "recipes/cuisine");
    }

    @Test
    @DisplayName("Проверка классификации рецепта с параметрами language=de, title=Paleo Bruschetta, ingredientList=1 cup diced tomatoes")
    void getClassifyCuisine3() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "de")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Paleo Bruschetta")
                .formParam("ingredientList", "1 cup diced tomatoes")
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("cuisine", equalTo("Mediterranean"))
                .body("confidence", notNullValue())
                .when()
                .post(getBaseUrl() + "recipes/cuisine");
    }

    @Test
    @DisplayName("Проверка классификации рецепта с параметрами title=Spanish style salmon fillets, ingredientList=3 red bell peppers")
    void getClassifyCuisine4() {
        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Spanish style salmon fillets")
                .formParam("ingredientList", "3 red bell peppers")
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(200)
                .body("cuisine", equalTo("European"))
                .body("confidence", notNullValue())
                .when()
                .post(getBaseUrl() + "recipes/cuisine");
    }

    @Test
    @DisplayName("Проверка классификации рецепта без авторизации")
    void getClassifyCuisine5() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .response()
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(401)
                .body("status", equalTo("failure"))
                .body("message", equalTo("You are not authorized. Please read https://spoonacular.com/food-api/docs#Authentication"))
                .when()
                .post(getBaseUrl() + "recipes/cuisine");
    }
}
