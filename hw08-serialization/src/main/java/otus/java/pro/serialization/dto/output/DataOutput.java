package otus.java.pro.serialization.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class DataOutput<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -2787444146057128913L;

    @JsonProperty
    private final List<T> rows = new ArrayList<>();
}
