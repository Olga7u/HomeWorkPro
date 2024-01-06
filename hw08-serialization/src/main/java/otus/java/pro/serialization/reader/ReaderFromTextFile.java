package otus.java.pro.serialization.reader;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import otus.java.pro.serialization.dto.output.DataPlain;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class ReaderFromTextFile {
    private static Object readFromHFile(String fileName, Class<?> dataType, ObjectMapper objectMapper) {
        objectMapper.findAndRegisterModules();

        try (Reader reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)) {
            return objectMapper.readValue(reader, dataType);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static Object readFromJson(String fileName, Class<?> dataType) {
        return readFromHFile(fileName, dataType, new ObjectMapper());
    }

    public static Object readFromXml(String fileName, Class<?> dataType) {
        return readFromHFile(fileName, dataType, new XmlMapper());
    }

    public static Object readFromYaml(String fileName, Class<?> dataType) {
        return readFromHFile(fileName, dataType, new YAMLMapper());
    }

    public static Collection<DataPlain> readFromCsv(String fileName) {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.findAndRegisterModules();

        CsvSchema csvSchema = csvMapper
                .typedSchemaFor(DataPlain.class)
                .withHeader()
                .withColumnSeparator(';')
                .withComments();

        try (Reader reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)) {
            MappingIterator<DataPlain> csvIterator = csvMapper
                    .readerWithTypedSchemaFor(DataPlain.class)
                    .with(csvSchema)
                    .readValues(reader);
            return csvIterator.readAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
