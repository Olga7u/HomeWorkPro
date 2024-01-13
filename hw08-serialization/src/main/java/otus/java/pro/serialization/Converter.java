package otus.java.pro.serialization;

import otus.java.pro.serialization.dto.input.ChatSession;
import otus.java.pro.serialization.dto.input.DataInput;
import otus.java.pro.serialization.dto.input.Member;
import otus.java.pro.serialization.dto.input.Message;
import otus.java.pro.serialization.dto.output.DataOutput;
import otus.java.pro.serialization.dto.output.DataPlain;
import otus.java.pro.serialization.dto.output.MessageFromSender;
import otus.java.pro.serialization.dto.output.Sender;
import otus.java.pro.serialization.protobuf.ProtoDataOuterClass;

import java.util.*;

public class Converter {

    public static void executeHierarchy(DataInput dataInput, DataOutput<Sender> dataOutput) {
        Map<String, Sender> data = new HashMap<>();
        for (ChatSession chatSession : dataInput.getChatSessions()) {
            for (Message message : chatSession.getMessages()) {
                String belongNumber = message.getBelongNumber();
                if (!data.containsKey(belongNumber)) {
                    data.put(belongNumber, new Sender(belongNumber, chatSession.getChatIdentifier()));
                }
                data.get(belongNumber).getMessages().add(new MessageFromSender(belongNumber, message.getSendDate(), message.getText()));
                for (Member member : chatSession.getMembers()) {
                    data.get(belongNumber).getLast().add(member.getLast());
                }
            }
        }
        dataOutput.getRows().clear();
        dataOutput.getRows().addAll(data.values());
    }

    public static void executePlain(DataInput dataInput, DataOutput<DataPlain> dataOutput) {
        Set<DataPlain> rows = new TreeSet<>();
        for (ChatSession chatSession : dataInput.getChatSessions()) {
            Set<String> last = new HashSet<>();
            for (Member member : chatSession.getMembers()) {
                last.add(member.getLast());
            }
            for (Message message : chatSession.getMessages()) {
                rows.add(DataPlain.builder()
                        .belongNumber(message.getBelongNumber())
                        .chatIdentifier(chatSession.getChatIdentifier())
                        .last(last.toString())
                        .sendDate(message.getSendDate())
                        .text(message.getText())
                        .build());
            }
        }
        dataOutput.getRows().clear();
        dataOutput.getRows().addAll(rows);
    }

    public static ProtoDataOuterClass.ProtoData executeProtobuf(DataInput dataInput) {
        DataOutput<Sender> dataOutput = new DataOutput<>();
        List<ProtoDataOuterClass.ProtoSender> senders = new LinkedList<>();

        Converter.executeHierarchy(dataInput, dataOutput);

        for (Sender sender : dataOutput.getRows()) {
            List<ProtoDataOuterClass.ProtoMessage> msgList = new LinkedList<>();
            for (MessageFromSender msg : sender.getMessages()) {
                msgList.add(ProtoDataOuterClass.ProtoMessage.newBuilder()
                        .setSendDate(msg.getSendDate().toString())
                        .setText(msg.getText())
                        .build());
            }
            ProtoDataOuterClass.ProtoSender protoSender = ProtoDataOuterClass.ProtoSender.newBuilder()
                    .setBelongNumber(sender.getBelongNumber())
                    .setChatIdentifier(sender.getChatIdentifier())
                    .addAllLast(sender.getLast())
                    .addAllMessages(msgList)
                    .build();
            senders.add(protoSender);
        }

        return ProtoDataOuterClass.ProtoData.newBuilder()
                .addAllSenders(senders)
                .build();
    }

}
