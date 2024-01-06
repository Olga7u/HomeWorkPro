package otus.java.pro.serialization.dto.input;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@JsonIncludeProperties({"chat_identifier", "members", "messages"})
public class ChatSession {
    @JsonProperty("chat_identifier")
    private String chatIdentifier;

    @JsonProperty
    private List<Member> members;

    @JsonProperty
    private List<Message> messages;
}
