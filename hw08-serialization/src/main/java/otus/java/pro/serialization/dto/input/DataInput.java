package otus.java.pro.serialization.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class DataInput {

    @JsonProperty("chat_sessions")
    private List<ChatSession> chatSessions;
}
