package otus.java.pro.serialization.reader;

import otus.java.pro.serialization.protobuf.ProtoDataOuterClass;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ReaderFromBinFile {
    public static Object readFromJavaBin(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return inputStream.readObject();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static ProtoDataOuterClass.ProtoData readFromProtobuf(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return ProtoDataOuterClass.ProtoData.newBuilder().mergeFrom(inputStream).build();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
