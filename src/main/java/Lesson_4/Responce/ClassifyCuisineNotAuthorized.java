package Lesson_4.Responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.annotation.Generated;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "code",
        "message"
})
@Generated("jsonschema2pojo")
@Data
public class ClassifyCuisineNotAuthorized {

    @JsonProperty("status")
    public String status;
    @JsonProperty("code")
    public Integer code;
    @JsonProperty("message")
    public String message;
}
