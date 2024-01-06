package otus.java.pro.serialization.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Getter
@ToString
@RequiredArgsConstructor
@JsonPropertyOrder({"belong_number", "chat_identifier", "last", "messages"})
public class Sender implements Serializable {
    @Serial
    private static final long serialVersionUID = -3192590076478593342L;

    @JsonProperty("belong_number")
    private final String belongNumber;

    @JsonProperty("chat_identifier")
    private final String chatIdentifier;

    @JsonProperty
    private final Set<String> last = new HashSet<>();

    @JsonProperty
    private final Set<MessageFromSender> messages = new TreeSet<>();
}
