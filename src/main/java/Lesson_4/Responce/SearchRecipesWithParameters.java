package Lesson_4.Responce;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "results",
        "offset",
        "number",
        "totalResults"
})
@Generated("jsonschema2pojo")
@Data
public class SearchRecipesWithParameters {

    @JsonProperty("results")
    public List<Result> results = new ArrayList<Result>();
    @JsonProperty("offset")
    public Integer offset;
    @JsonProperty("number")
    public Integer number;
    @JsonProperty("totalResults")
    public Integer totalResults;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "title",
            "image",
            "imageType",
            "nutrition"
    })
    @Generated("jsonschema2pojo")
    @Data
    public static class Result {

        @JsonProperty("id")
        public Integer id;
        @JsonProperty("title")
        public String title;
        @JsonProperty("image")
        public String image;
        @JsonProperty("imageType")
        public String imageType;
        @JsonProperty("nutrition")
        public Nutrition nutrition;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "nutrients"
    })
    @Generated("jsonschema2pojo")
    public static class Nutrition {

        @JsonProperty("nutrients")
        public List<Nutrient> nutrients = new ArrayList<Nutrient>();

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "name",
            "amount",
            "unit"
    })
    @Generated("jsonschema2pojo")
    public static class Nutrient {

        @JsonProperty("name")
        public String name;
        @JsonProperty("amount")
        public Double amount;
        @JsonProperty("unit")
        public String unit;

    }
}
