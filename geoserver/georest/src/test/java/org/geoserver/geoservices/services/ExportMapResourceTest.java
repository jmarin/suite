package org.geoserver.geoservices.services;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.schema.JsonSchema;

public class ExportMapResourceTest extends MapResourceTest {

    /**
     * GeoServices REST Map Service DRAFT 4 Req 14: The JSON representation of a response to a request on an Export Map resource SHALL validate
     * against the JSON Schema http://schemas.opengis.net/gsr-ms/1.0/map.json or in case of an exception against JSON Schema
     * http://schemas.opengis.net/gsr/1.0/exception.json.
     * 
     * @throws Exception
     */

    public void testExportMapServiceSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(ExportMapService.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        assertEquals("string", properties.get("href").get("type").getTextValue());
        assertEquals("integer", properties.get("width").get("type").getTextValue());
        assertEquals("integer", properties.get("height").get("type").getTextValue());
        assertEquals("number", properties.get("scale").get("type").getTextValue());

    }

    /**
     * GeoServices REST Map Service DRAFT 4 Req 12: The Export Map resource SHALL accept requests that conform to the URI template in Table 5 and use
     * any HTTP method identified in the same table.
     * 
     * @throws Exception
     */
    public void testExportMap() throws Exception {
        JSON json = getAsJSON("/rest/services/SanFrancisco/MapServer/export?f=json&bbox=-122.75,36.8,-121.75,37.8&size=500,500&dpi=96");
        assertTrue(json instanceof JSONObject);
        JSONObject jsonObject = (JSONObject) json;
        assertEquals(
                "http://localhost/geoserver/rest/services/SanFrancisco/MapServer/export?f=image&bbox=-122.75,36.8,-121.75,37.8&size=500,500&dpi=96",
                jsonObject.get("href"));
        assertEquals(500, jsonObject.get("width"));
        assertEquals(500, jsonObject.get("height"));
        JSONObject extent = (JSONObject) jsonObject.get("extent");
        assertEquals(-122.75, extent.get("xmin"));
        assertEquals(36.8, extent.get("ymin"));
        assertEquals(-121.75, extent.get("xmax"));
        assertEquals(37.8, extent.get("ymax"));
        JSONObject spatialReference = (JSONObject) extent.get("spatialReference");
        assertEquals(4326, spatialReference.get("wkid"));
        // TODO : review scale calculation and test
        // assertEquals(840526.486922667, jsonObject.get("scale"));
    }

    /**
     * GeoServices REST Map Service DRAFT 4 Req 15: The image representation of a Map resource and the image file referenced from the the href
     * property of the JSON representation of a Map resource SHALL have the width, height and dpi as specified by the URI parameters.
     * 
     * @throws Exception
     */

    public void testImageOutput() throws Exception {
        JSON json = getAsJSON("/rest/services/SanFrancisco/MapServer/export?f=json&bbox=-122.75,36.8,-121.75,37.8&size=500,500&dpi=96");
        fail("not yet implemented");
    }

    /**
     * GeoServices REST Map Service DRAFT 4 Req 16: If aspect ratio is inconsistent between the parameters bbox and size, the extent of the returned
     * map image SHALL be adjusted to have the same aspect ratio as the image. If the Map resource is returned, the bbox SHALL be the adjusted
     * envelope.
     */
    public void testInconsistentBBOX() {
        fail("not yet implemented");
    }

    /**
     * GeoServices REST Map Service DRAFT 4 Req 17: The image SHALL be in the requested format, if the format is listed in the Map Service Root
     * resource as a supported image format type.
     * 
     * @throws Exception
     */
    public void testImageFormat() throws Exception {
        HttpServletResponse response = getAsServletResponse("/rest/services/SanFrancisco/MapServer/export?f=image&bbox=-122.75,36.8,-121.75,37.8&format=JPG");
        assertNotNull(response);
        
    }

}
