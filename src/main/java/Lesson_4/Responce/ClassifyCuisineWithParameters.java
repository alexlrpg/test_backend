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
        "cuisine",
        "cuisines",
        "confidence"
})
@Generated("jsonschema2pojo")
@Data
public class ClassifyCuisineWithParameters {

    @JsonProperty("cuisine")
    public String cuisine;
    @JsonProperty("cuisines")
    public List<String> cuisines = new ArrayList<String>();
    @JsonProperty("confidence")
    public Double confidence;
}
