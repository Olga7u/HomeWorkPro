package otus.java.pro.serialization.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@JsonPropertyOrder({"send_date", "text"})
public class MessageFromSender implements Comparable<MessageFromSender>, Serializable {
    @Serial
    private static final long serialVersionUID = 2630950274549895160L;

    @JsonIgnore
    private final String belongNumber;

    @JsonProperty("send_date")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM-dd-yyyy HH:mm:ss")
    private final LocalDateTime sendDate;

    @JsonProperty
    private final String text;

    @Override
    public int compareTo(MessageFromSender o) {
        return this.sendDate.compareTo(o.sendDate);
    }
}
