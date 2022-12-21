package Lesson_4.Request;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "item",
        "aisle",
        "parse"
})
@Generated("jsonschema2pojo")
public class AddShoppingListRequest {

    @JsonProperty("item")
    public String item;
    @JsonProperty("aisle")
    public String aisle;
    @JsonProperty("parse")
    public Boolean parse;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddShoppingListRequest() {
    }

    public AddShoppingListRequest(String item, String aisle, Boolean parse) {
        super();
        this.item = item;
        this.aisle = aisle;
        this.parse = parse;
    }

    public AddShoppingListRequest(String item, Boolean parse) {
        super();
        this.item = item;
        this.parse = parse;
    }
}
