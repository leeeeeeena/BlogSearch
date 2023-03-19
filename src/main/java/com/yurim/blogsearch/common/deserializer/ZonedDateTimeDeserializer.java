package com.yurim.blogsearch.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.yurim.blogsearch.common.Constants.ZONED_DATE_TIME_FORMAT;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        return ZonedDateTime.parse(jsonParser.getText(), DateTimeFormatter.ofPattern(ZONED_DATE_TIME_FORMAT));
    }
}
