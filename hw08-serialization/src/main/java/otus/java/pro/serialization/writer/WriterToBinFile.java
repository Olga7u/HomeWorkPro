package otus.java.pro.serialization.writer;

import otus.java.pro.serialization.Converter;
import otus.java.pro.serialization.dto.input.DataInput;
import otus.java.pro.serialization.dto.output.DataOutput;
import otus.java.pro.serialization.dto.output.Sender;
import otus.java.pro.serialization.protobuf.ProtoDataOuterClass;

import java.io.*;

public class WriterToBinFile {

    public static void writeToJavaBin(DataInput dataInput, String fileName) {
        DataOutput<Sender> dataOutput = new DataOutput<>();

        Converter.executeHierarchy(dataInput, dataOutput);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(dataOutput);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static void writeToProtobuf(DataInput dataInput, String fileName) {
        ProtoDataOuterClass.ProtoData protoData = Converter.executeProtobuf(dataInput);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            protoData.writeTo(outputStream);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
