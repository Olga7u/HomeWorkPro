package otus.java.pro.serialization;

import lombok.extern.slf4j.Slf4j;
import otus.java.pro.serialization.dto.input.DataInput;
import otus.java.pro.serialization.dto.output.DataOutput;
import otus.java.pro.serialization.dto.output.DataPlain;
import otus.java.pro.serialization.protobuf.ProtoDataOuterClass;
import otus.java.pro.serialization.reader.ReaderFromBinFile;
import otus.java.pro.serialization.reader.ReaderFromTextFile;
import otus.java.pro.serialization.writer.WriterToBinFile;
import otus.java.pro.serialization.writer.WriterToTextFile;

@Slf4j
public class Main {
    private static final String FILENAME = "hw08-serialization/src/main/resources/out/output.";

    public static void main(String[] args) {
        DataInput dataInput = new DataInput();

        try {
            dataInput = (DataInput) ReaderFromTextFile.readFromJson(
                    "hw08-serialization/src/main/resources/sms.json", DataInput.class);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }

        writeToTextFiles(dataInput);
        printFromTextFiles();

        writeToBinFile(dataInput);
        printFromBinFile();

        writeToProtoFile(dataInput);
        printFromProtoFile();
    }

    private static void writeToTextFiles(DataInput dataInput) {
        try {
            WriterToTextFile.writeToJson(dataInput, FILENAME + "json");
            WriterToTextFile.writeToXml(dataInput, FILENAME + "xml");
            WriterToTextFile.writeToYaml(dataInput, FILENAME + "yml");
            WriterToTextFile.writeToCsv(dataInput, FILENAME + "csv");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    private static void printFromTextFiles() {
        try {
            Object dataOutputJson = ReaderFromTextFile.readFromJson(FILENAME + "json", DataOutput.class);
            Object dataOutputXml = ReaderFromTextFile.readFromXml(FILENAME + "xml", DataOutput.class);
            Object dataOutputYaml = ReaderFromTextFile.readFromYaml(FILENAME + "yml", DataOutput.class);

            DataOutput<DataPlain> dataOutputCsv = new DataOutput<>();
            dataOutputCsv.getRows().addAll(ReaderFromTextFile.readFromCsv(FILENAME + "csv"));

            System.out.println("\ndataOutputJson=" + dataOutputJson);
            System.out.println("\ndataOutputXml=" + dataOutputXml);
            System.out.println("\ndataOutputYaml=" + dataOutputYaml);
            System.out.println("\ndataOutputCsv=" + dataOutputCsv);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    private static void writeToBinFile(DataInput dataInput) {
        try {
            WriterToBinFile.writeToJavaBin(dataInput, FILENAME + "bin");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    private static void printFromBinFile() {
        try {
            Object dataOutputBin = ReaderFromBinFile.readFromJavaBin(FILENAME + "bin");
            System.out.println("\ndataOutputBin=" + dataOutputBin);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    private static void writeToProtoFile(DataInput dataInput) {
        try {
            WriterToBinFile.writeToProtobuf(dataInput, FILENAME + "pbin");
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    private static void printFromProtoFile() {
        try {
            ProtoDataOuterClass.ProtoData protoData = ReaderFromBinFile.readFromProtobuf(FILENAME + "pbin");
            System.out.println("\ndataOutputProtobuf=" + protoData);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }
}