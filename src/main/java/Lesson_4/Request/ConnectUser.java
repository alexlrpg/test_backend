package Lesson_4.Request;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "username",
        "firstName",
        "lastName",
        "email"
})
@Generated("jsonschema2pojo")
@Data
public class ConnectUser {

    @JsonProperty("username")
    public String username;
    @JsonProperty("firstName")
    public String firstName;
    @JsonProperty("lastName")
    public String lastName;
    @JsonProperty("email")
    public String email;

    public ConnectUser() {
    }

    public ConnectUser(String username, String firstName, String lastName, String email) {
        super();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
