package com.dw.demo.rest.serializers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.core.Link;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.SerializerBase;

/**
 * Serialize lists of HATEOAS Link objects
 * 
 * @author rgunderson
 */
public class LinksSerializer extends SerializerBase<List<Link>> {

	protected LinksSerializer() {
		super(Link.class, true);
	}

	@Override
	public JsonNode getSchema(SerializerProvider arg0, Type arg1) throws JsonMappingException {
		return null;
	}

	@Override
	public void serialize(List<Link> links, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonGenerationException {
		jgen.writeStartArray();
		for (Link link : links) {
			jgen.writeStartObject();
			jgen.writeStringField("rel", link.getRel());
			jgen.writeStringField("href", link.getUri().toString());
			jgen.writeStringField("type", link.getType());
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
	}

}
