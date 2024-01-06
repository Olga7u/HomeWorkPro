package otus.java.pro.serialization.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@JsonIncludeProperties({"belong_number", "send_date", "text"})
public class Message {
    @JsonProperty("belong_number")
    private String belongNumber;

    @JsonProperty("send_date")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime sendDate;

    @JsonProperty
    private String text;
}
