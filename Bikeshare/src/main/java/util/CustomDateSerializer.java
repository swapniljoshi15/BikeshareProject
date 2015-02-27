package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Created by gaurav on 12/6/14.
 */
public class CustomDateSerializer extends JsonSerializer<DateTime>{

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy 'at' HH:mm:ss z");

    @Override
    public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
       jgen.writeString(formatter.print(value));
    }

}
