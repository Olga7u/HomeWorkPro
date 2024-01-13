package otus.java.pro.serialization.dto.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@EqualsAndHashCode
@JsonPropertyOrder({"belong_number", "chat_identifier", "last", "send_date", "text"})
public class DataPlain implements Comparable<DataPlain> {
    @JsonProperty("belong_number")
    private String belongNumber;

    @JsonProperty("chat_identifier")
    private String chatIdentifier;

    @JsonProperty("last")
    private String last;

    @JsonProperty("send_date")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime sendDate;

    @JsonProperty("text")
    private String text;

    @JsonCreator
    public DataPlain(
            @JsonProperty("belong_number") String belongNumber
            , @JsonProperty("chat_identifier") String chatIdentifier
            , @JsonProperty("last") String last
            , @JsonProperty("send_date") LocalDateTime sendDate
            , @JsonProperty("text") String text) {
        this.belongNumber = belongNumber;
        this.chatIdentifier = chatIdentifier;
        this.last = last;
        this.sendDate = sendDate;
        this.text = text;
    }

    @Override
    public int compareTo(DataPlain o) {
        return this.belongNumber.compareTo(o.belongNumber) | this.sendDate.compareTo(o.sendDate);
    }
}