package otus.java.pro.serialization.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import otus.java.pro.serialization.Converter;
import otus.java.pro.serialization.dto.input.DataInput;
import otus.java.pro.serialization.dto.output.DataOutput;
import otus.java.pro.serialization.dto.output.DataPlain;
import otus.java.pro.serialization.dto.output.Sender;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class WriterToTextFile {
    private static void writeToHFile(DataInput dataInput, String fileName, ObjectMapper objectMapper) {
        DataOutput<Sender> dataOutput = new DataOutput<>();

        Converter.executeHierarchy(dataInput, dataOutput);

        objectMapper.findAndRegisterModules();

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8)) {
            objectMapper.writeValue(writer, dataOutput);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static void writeToJson(DataInput dataInput, String fileName) {
        writeToHFile(dataInput, fileName, new ObjectMapper());
    }

    public static void writeToXml(DataInput dataInput, String fileName) {
        writeToHFile(dataInput, fileName, new XmlMapper());
    }

    public static void writeToYaml(DataInput dataInput, String fileName) {
        writeToHFile(dataInput, fileName, new YAMLMapper());
    }

    public static void writeToCsv(DataInput dataInput, String fileName) {
        DataOutput<DataPlain> dataOutput = new DataOutput<>();

        Converter.executePlain(dataInput, dataOutput);

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.findAndRegisterModules();

        CsvSchema csvSchema = csvMapper
                .typedSchemaFor(DataPlain.class)
                .withHeader()
                .withColumnSeparator(';')
                .withComments();

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8)) {
            csvMapper.writer(csvSchema).writeValue(writer, dataOutput.getRows());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
