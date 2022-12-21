package Lesson_4;

import Lesson_4.Responce.ClassifyCuisineNotAuthorized;
import Lesson_4.Responce.ClassifyCuisineWithParameters;
import Lesson_4.Responce.SearchRecipesWithParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SearchAndClassifyTest extends AbstractTest {

    @Test
    @DisplayName("Поиск с параметрами minCaffeine, includeIngredients, titleMatch")
    void searchRecipesWithThreeParameters() {
        SearchRecipesWithParameters resp = given().spec(getRequestSpecification())
                .when()
                .queryParam("minCaffeine", 50)
                .queryParam("includeIngredients", "chocolate")
                .queryParam("titleMatch", "Chocolate")
                .get(getBaseUrl() + "recipes/complexSearch")
                .then().spec(getResponseSpecification())
                .extract()
                .response()
                .body()
                .as(SearchRecipesWithParameters.class);
        assertThat(resp.getResults().get(0).getNutrition().nutrients.get(0).amount, greaterThan(50.00));
    }

    @Test
    @DisplayName("Поиск с параметрами query и diet")
    void searchRecipesWithTwoParameters() {
        SearchRecipesWithParameters resp = given().spec(getRequestSpecification())
                .when()
                .queryParam("query", "pasta")
                .queryParam("diet", "vegetarian")
                .get(getBaseUrl() + "recipes/complexSearch")
                .then().spec(getResponseSpecification())
                .extract()
                .response()
                .body()
                .as(SearchRecipesWithParameters.class);
        assertThat(resp.getTotalResults(), equalTo(34));
    }

    @Test
    @DisplayName("Поиск с параметрами query, diet, maxReadyTime, minCalories")
    void searchRecipesWithParameters() {
        SearchRecipesWithParameters resp = given().spec(getRequestSpecification())
                .when()
                .queryParam("query", "meat")
                .queryParam("diet", "gluten free")
                .queryParam("maxReadyTime", 10)
                .queryParam("minCalories", 300)
                .get(getBaseUrl() + "recipes/complexSearch")
                .then().spec(getResponseSpecification())
                .extract()
                .response()
                .body()
                .as(SearchRecipesWithParameters.class);
        assertThat(resp.getResults().get(0).getNutrition().nutrients.get(0).amount, greaterThan(300.00));
        assertThat(resp.getResults().get(0).getNutrition().nutrients.get(0).name, equalTo("Calories"));
    }

    @Test
    @DisplayName("Поиск с параметрами includeIngredients, instructionsRequired, sortDirection, number")
    void searchRecipesWithForParameters() {
        SearchRecipesWithParameters resp = given().spec(getRequestSpecification())
                .when()
                .queryParam("includeIngredients", "cucumber,meat")
                .queryParam("instructionsRequired", "true")
                .queryParam("sortDirection", "asc")
                .queryParam("number", 3)
                .get(getBaseUrl() + "recipes/complexSearch")
                .then().spec(getResponseSpecification())
                .extract()
                .response()
                .body()
                .as(SearchRecipesWithParameters.class);
        assertThat(resp.getResults().get(0).title, equalTo("Loaded Macaroni Salad"));
        assertThat(resp.getNumber(), equalTo(3));
    }

    @Test
    @DisplayName("Поиск с параметрами minZinc")
    void searchRecipesWithOneParameters() {
        SearchRecipesWithParameters resp = given().spec(getRequestSpecification())
                .when()
                .queryParam("minZinc", 30)
                .get(getBaseUrl() + "recipes/complexSearch")
                .then().spec(getResponseSpecification())
                .extract()
                .response()
                .body()
                .as(SearchRecipesWithParameters.class);
        assertThat(resp.getResults().get(0).getNutrition().nutrients.get(0).name, containsString("Zinc"));
        assertThat(resp.getResults().get(0).getNutrition().nutrients.get(0).amount, greaterThan(30.00));
    }

    @Test
    @DisplayName("Проверка классификации рецепта с параметрами language=en, title=salsa")
    void classifyCuisineWithLanguageAndTitle() {
        ClassifyCuisineWithParameters resp = given().spec(getRequestSpecification())
                .when()
                .queryParam("language", "en")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "salsa")
                .post(getBaseUrl() + "recipes/cuisine")
                .then().spec(getResponseSpecification())
                .extract()
                .response()
                .body()
                .as(ClassifyCuisineWithParameters.class);
        assertThat(resp.getCuisine(), equalTo("Mexican"));
        assertThat(resp.getConfidence(), notNullValue());
    }

    @Test
    @DisplayName("Проверка классификации рецепта с параметрами title=burger")
    void classifyCuisineWithTitle() {
        ClassifyCuisineWithParameters resp = given().spec(getRequestSpecification())
                .when()
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "burger")
                .post(getBaseUrl() + "recipes/cuisine")
                .then().spec(getResponseSpecification())
                .extract()
                .response()
                .body()
                .as(ClassifyCuisineWithParameters.class);
        assertThat(resp.getCuisine(), equalTo("American"));
        assertThat(resp.getConfidence(), notNullValue());
    }

    @Test
    @DisplayName("Проверка классификации рецепта с параметрами language=de, title=Paleo Bruschetta, ingredientList=1 cup diced tomatoes")
    void classifyCuisineWithLanguageAndTitleAndIngredientList() {
        ClassifyCuisineWithParameters resp = given().spec(getRequestSpecification())
                .when()
                .queryParam("language", "de")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Paleo Bruschetta")
                .formParam("ingredientList", "1 cup diced tomatoes")
                .post(getBaseUrl() + "recipes/cuisine")
                .then().spec(getResponseSpecification())
                .extract()
                .response()
                .body()
                .as(ClassifyCuisineWithParameters.class);
        assertThat(resp.getCuisine(), equalTo("Mediterranean"));
        assertThat(resp.getConfidence(), notNullValue());
    }

    @Test
    @DisplayName("Проверка классификации рецепта с параметрами title=Spanish style salmon fillets, ingredientList=3 red bell peppers")
    void classifyCuisineWithTitleAndIngredientList() {
        ClassifyCuisineWithParameters resp = given().spec(getRequestSpecification())
                .when()
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Spanish style salmon fillets")
                .formParam("ingredientList", "3 red bell peppers")
                .post(getBaseUrl() + "recipes/cuisine")
                .then().spec(getResponseSpecification())
                .extract()
                .response()
                .body()
                .as(ClassifyCuisineWithParameters.class);
        assertThat(resp.getCuisine(), equalTo("European"));
        assertThat(resp.getConfidence(), notNullValue());
    }

    @Test
    @DisplayName("Проверка классификации рецепта без авторизации")
    void classifyCuisineNotAuthorized() {
        ClassifyCuisineNotAuthorized resp = given()
                .when()
                .contentType("application/x-www-form-urlencoded")
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .extract()
                .response()
                .body()
                .as(ClassifyCuisineNotAuthorized.class);
        assertThat(resp.getStatus(), equalTo("failure"));
        assertThat(resp.getCode(), equalTo(401));
        assertThat(resp.getMessage(), equalTo("You are not authorized. Please read https://spoonacular.com/food-api/docs#Authentication"));
    }
}
