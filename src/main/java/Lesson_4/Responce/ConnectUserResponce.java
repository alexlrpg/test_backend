package Lesson_4.Responce;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "username",
        "spoonacularPassword",
        "hash"
})
@Generated("jsonschema2pojo")
@Data
public class ConnectUserResponce {

    @JsonProperty("status")
    public String status;
    @JsonProperty("username")
    public String username;
    @JsonProperty("spoonacularPassword")
    public String spoonacularPassword;
    @JsonProperty("hash")
    public String hash;

}
