package org.geoserver.gsr.exception;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.schema.JsonSchema;
import org.geoserver.gsr.exception.ServiceException;
import org.geoserver.test.GeoServerTestSupport;

/**
 * GeoServices REST DRAFT 1 Specification - 7.3 Core Exceptions
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class ServiceExceptionTest extends GeoServerTestSupport {

    /**
     * Req. 5: The JSON representation of an exception SHALL validate against the JSON Schema http://schemas.opengis.net/gsr/1.0/exception.json
     * 
     * @throws Exception
     */

    public void testExceptionServiceSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(ServiceException.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        JsonNode error = properties.get("error");
        assertEquals("object", error.get("type").getTextValue());
        JsonNode errorProperties = error.get("properties");
        JsonNode code = errorProperties.get("code");
        assertEquals("string", code.get("type").getTextValue());
        JsonNode message = errorProperties.get("message");
        assertEquals("string", message.get("type").getTextValue());
        JsonNode details = errorProperties.get("details");
        assertEquals("array", details.get("type").getTextValue());
        JsonNode detailsItems = details.get("items");
        assertEquals("string", detailsItems.get("type").getTextValue());

    }

}
