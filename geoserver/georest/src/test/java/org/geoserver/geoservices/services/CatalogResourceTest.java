package org.geoserver.geoservices.services;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.schema.JsonSchema;
import org.geoserver.geoservices.core.ServiceType;
import org.geoserver.test.GeoServerTestSupport;

/**
 * GeoServices REST DRAFT 1 Specification - 6.2.1 Catalog URI
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class CatalogResourceTest extends ResourceTest {

    /**
     * Req. 1: The Catalog resource SHALL accept requests that conform to the URI template in Table 3 and use any HTTP method identified in the same
     * table (GET) Req. 2: A Catalog resource SHALL support all parameters and values specified in Table 4 (f=json)
     * 
     */

    public void testGetCatalogRootURI() throws Exception {
        JSON json = getAsJSON("/rest/services/?f=json");
        assertTrue(json instanceof JSONObject);
        JSONObject jsonObject = (JSONObject) json;
        assertEquals("1.0", jsonObject.get("specVersion"));
        assertEquals("OpenGeo Suite Enterprise Edition", jsonObject.get("productName"));
        assertEquals("2.4.4", jsonObject.get("currentVersion"));
        assertEquals(ServiceType.CatalogServer.toString(), jsonObject.get("type"));
        JSONArray folders = (JSONArray) jsonObject.get("folders");
        assertEquals(0, folders.size());
        JSONArray services = (JSONArray) jsonObject.get("services");
        assertEquals(1, services.size());
    }

    /**
     * GeoServices REST DRAFT 1 Specification - 7.3 Exceptions Req. 3: A request to a resource of a Geoservice REST API implementation SHALL result in
     * an exception, if and only if the request violates a request requirement, unless an internal processing exception occurs
     * 
     * This tests for a request with the wrong format
     * 
     */
    @Override
    public void testServiceException() throws Exception {
        String baseURL = "/rest/services/";
        this.baseURL = baseURL;
        super.testServiceException();
    }

    /**
     * Req. 3: The JSON representation of the Catalog resource SHALL validate against the JSON Schema
     * http://schemas.opengis.net/gsr-cs/1.0/catalog.json or in case of an exception against JSON Schema
     * http://schemas.opengis.net/gsr/1.0/exception.json
     * 
     * @throws Exception
     * 
     *         NOTE: currentVersion and productVersion are described in the Spec as "number". This would not accomodate versions 1.1.0, 2.4.4, etc.
     *         Keeping them as String in this implementation
     */
    public void testCatalogServiceSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(CatalogService.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        JsonNode specVersion = properties.get("specVersion");
        assertEquals("string", specVersion.get("type").getTextValue());
        JsonNode productName = properties.get("productName");
        assertEquals("string", productName.get("type").getTextValue());
        JsonNode currentVersion = properties.get("currentVersion");
        assertEquals("string", currentVersion.get("type").getTextValue());
        JsonNode folders = properties.get("folders");
        assertEquals("array", folders.get("type").getTextValue());
        JsonNode folderItems = folders.get("items");
        assertEquals("string", folderItems.get("type").getTextValue());
        JsonNode services = properties.get("services");
        assertEquals("array", services.get("type").getTextValue());
        JsonNode servicesItems = services.get("items");
        assertEquals("object", servicesItems.get("type").getTextValue());
        JsonNode servicesItemsProperties = servicesItems.get("properties");
        JsonNode serviceName = servicesItemsProperties.get("name");
        assertEquals("string", serviceName.get("type").getTextValue());
        JsonNode serviceType = servicesItemsProperties.get("type");
        assertEquals("string", serviceType.get("type").getTextValue());
    }

}
