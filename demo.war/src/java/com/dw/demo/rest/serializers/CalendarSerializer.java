package com.dw.demo.rest.serializers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.SerializerBase;

/**
 * Serialize Calendar objects in yyyy-MM-dd format
 * 
 * @author rgunderson
 */
public class CalendarSerializer extends SerializerBase<Calendar> {

	protected CalendarSerializer() {
		super(Calendar.class, true);
	}

	@Override
	public JsonNode getSchema(SerializerProvider arg0, Type arg1) throws JsonMappingException {
		return null;
	}

	@Override
	public void serialize(Calendar cal, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonGenerationException {
		jgen.writeString(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
	}
}
