package otus.java.pro.serialization.dto.input;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIncludeProperties({"last"})
public class Member {
    @JsonProperty
    private String last;
}
