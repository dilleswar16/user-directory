package com.example.userapi.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final Logger log = LoggerFactory.getLogger(LocalDateDeserializer.class);

    private static final DateTimeFormatter[] FORMATTERS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("yyyy-M-d")
    };

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String dateStr = jsonParser.getText().trim();
        log.debug("Attempting to parse date: {}", dateStr);

        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                LocalDate parsedDate = LocalDate.parse(dateStr, formatter);
               // log.info("Successfully parsed date '{}' using format '{}'", dateStr, formatter);
                return parsedDate;
            } catch (DateTimeParseException e) {
              //  log.warn("Failed to parse date '{}' using format '{}'", dateStr, formatter);
            }
        }

        log.error("All date formats failed for input: '{}'", dateStr);
        throw new IOException("Invalid date format: '" + dateStr + "'. Expected formats: yyyy-MM-dd or yyyy-M-d");
    }
}
