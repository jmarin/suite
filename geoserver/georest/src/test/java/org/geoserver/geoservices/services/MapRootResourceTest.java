package org.geoserver.geoservices.services;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.schema.JsonSchema;
import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.test.GeoServerTestSupport;
import org.geotools.geometry.jts.ReferencedEnvelope;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */
public class MapRootResourceTest extends ResourceTest {

    protected Catalog catalog;

    private LayerGroupInfo layerGroup;

    @Override
    protected void setUpInternal() throws Exception {
        super.setUpInternal();
        catalog = getCatalog();
        layerGroup = catalog.getFactory().createLayerGroup();
        layerGroup.setName("SanFrancisco");
        layerGroup.getLayers().add(catalog.getLayerByName("Bridges"));
        layerGroup.getLayers().add(catalog.getLayerByName("Buildings"));
        layerGroup.getLayers().add(catalog.getLayerByName("Forests"));
        layerGroup.getLayers().add(catalog.getLayerByName("Lakes"));
        layerGroup.getLayers().add(catalog.getLayerByName("Ponds"));
        layerGroup.getLayers().add(catalog.getLayerByName("Streams"));
        ReferencedEnvelope envelope = layerGroup.getLayers().get(0).getResource().boundingBox();
        layerGroup.setBounds(envelope);
        catalog.add(layerGroup);
    }

    @Override
    protected void tearDownInternal() throws Exception {
        super.tearDownInternal();
        catalog.remove(layerGroup);
    }

    /**
     * Req. 1: The Map Service Root resource SHALL accept requests that conform to the URI template in Table 3 and use any HTTP method identified in
     * the same table (GET) Req. 2: The Map Service Root resource SHALL support all parameters and values specified in Table 4 (f=json)
     * 
     * @throws Exception
     * 
     */

    public void testMapServerRootURI() throws Exception {
        JSON json = getAsJSON("/rest/services/SanFrancisco/MapServer?f=json");
        assertTrue(json instanceof JSONObject);
        JSONObject jsonObject = (JSONObject) json;
        assertEquals("SanFrancisco", jsonObject.get("name"));
        assertEquals("MapServer", jsonObject.get("type"));
        JSONArray layers = (JSONArray) jsonObject.get("layers");
        assertEquals(6, layers.size());
        JSONObject bridgesLayer = (JSONObject) layers.get(0);
        assertEquals(0, bridgesLayer.getInt("id"));
        assertEquals("Bridges", bridgesLayer.get("name"));
        assertEquals(-1, bridgesLayer.get("parentLayerId"));
        assertTrue(bridgesLayer.getBoolean("defaultVisibility"));
        JSONObject spatialReference = (JSONObject) jsonObject.get("spatialReference");
        assertEquals(4326, spatialReference.getInt("wkid"));
        assertEquals(false, jsonObject.get("singleFusedMapCache"));
        JSONObject initialExtent = (JSONObject) jsonObject.get("initialExtent");
        assertEquals("ENVELOPE", initialExtent.get("geometryType"));
        assertEquals(-180.0, initialExtent.get("xmin"));
        assertEquals(-90.0, initialExtent.get("ymin"));
        assertEquals(180.0, initialExtent.get("xmax"));
        assertEquals(90.0, initialExtent.get("ymax"));
        assertEquals("Map,Query,Data", jsonObject.get("capabilities"));

    }

    /**
     * GeoServices REST DRAFT 1 Specification - 7.3 Exceptions Req. 3: A request to a resource of a Geoservice REST API implementation SHALL result in
     * an exception, if and only if the request violates a request requirement, unless an internal processing exception occurs
     * 
     * This tests for a request with the wrong format
     * 
     */
    public void testServiceException() throws Exception {
        String baseURL = "/rest/services/SanFrancisco/MapServer";
        this.baseURL = baseURL;
        super.testServiceException();
    }

    /**
     * GeoServices REST Map Service DRAFT 4 Req. 3: The JSON representation of a Map Service Root resource SHALL validate against the JSON Schema
     * http://schemas.opengis.net/gsr-ms/1.0/mapservice.json or in case of an exception against JSON Schema
     * http://schemas.opengis.net/gsr/1.0/exception.json.
     */

    public void testMapServiceRootSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(MapService.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        JsonNode serviceDescription = properties.get("serviceDescription");
        assertEquals("string", serviceDescription.get("type").getTextValue());
        JsonNode mapName = properties.get("mapName");
        assertEquals("string", mapName.get("type").getTextValue());
        JsonNode description = properties.get("description");
        assertEquals("string", description.get("type").getTextValue());
        JsonNode copyrightText = properties.get("copyrightText");
        assertEquals("string", copyrightText.get("type").getTextValue());
        JsonNode layers = properties.get("layers");
        assertEquals("array", layers.get("type").getTextValue());
        JsonNode layersItems = layers.get("items");
        assertEquals("object", layersItems.get("type").getTextValue());
        // Layer schema
        JsonNode layersItemsProperties = layersItems.get("properties");
        assertEquals("integer", layersItemsProperties.get("id").get("type").getTextValue());
        assertEquals("string", layersItemsProperties.get("name").get("type").getTextValue());
        assertEquals("boolean", layersItemsProperties.get("defaultVisibility").get("type")
                .getTextValue());
        assertEquals("integer", layersItemsProperties.get("parentLayerId").get("type")
                .getTextValue());
        assertEquals("array", layersItemsProperties.get("subLayerIds").get("type").getTextValue());
        assertEquals("integer", layersItemsProperties.get("subLayerIds").get("items").get("type")
                .getTextValue());
        JsonNode tables = properties.get("tables");
        assertEquals("array", tables.get("type").getTextValue());
        JsonNode tablesItems = tables.get("items");
        assertEquals("object", tablesItems.get("type").getTextValue());
        // Table schema
        JsonNode tablesItemsProperties = tablesItems.get("properties");
        assertEquals("integer", tablesItemsProperties.get("id").get("type").getTextValue());
        assertEquals("string", tablesItemsProperties.get("name").get("type").getTextValue());
        assertEquals("boolean", properties.get("singleFusedMapCache").get("type").getTextValue());
        //TileInfo schema
        JsonNode tileInfo = properties.get("tileInfo");
        assertEquals("object", tileInfo.get("type").getTextValue());
        assertEquals("integer", tileInfo.get("properties").get("rows").get("type").getTextValue());
        assertEquals("integer", tileInfo.get("properties").get("cols").get("type").getTextValue());
        assertEquals("integer", tileInfo.get("properties").get("dpi").get("type").getTextValue());
        assertEquals("string", tileInfo.get("properties").get("format").get("type").getTextValue());
        assertEquals("number", tileInfo.get("properties").get("compressionQuality").get("type").getTextValue());
        assertEquals("array", tileInfo.get("properties").get("lods").get("type").getTextValue());
        assertEquals("object", tileInfo.get("properties").get("lods").get("items").get("type").getTextValue());
        assertEquals("integer", tileInfo.get("properties").get("lods").get("items").get("properties").get("level").get("type").getTextValue());
        assertEquals("number", tileInfo.get("properties").get("lods").get("items").get("properties").get("resolution").get("type").getTextValue());
        assertEquals("number", tileInfo.get("properties").get("lods").get("items").get("properties").get("scale").get("type").getTextValue());
        //TimeInfo schema
        JsonNode timeInfo = properties.get("timeInfo");
        assertEquals("object", timeInfo.get("type").getTextValue());
        assertEquals("array", timeInfo.get("properties").get("timeExtent").get("type").getTextValue());
        assertEquals("object", timeInfo.get("properties").get("timeReference").get("type").getTextValue());
        assertEquals("string", timeInfo.get("properties").get("timeReference").get("properties").get("timeZone").get("type").getTextValue());
        assertEquals("boolean", timeInfo.get("properties").get("timeReference").get("properties").get("respectDaylightSaving").get("type").getTextValue());
        assertEquals("string",properties.get("units").get("type").getTextValue());
        assertEquals("string",properties.get("supportedImageFormatTypes").get("type").getTextValue());
        assertEquals("object",properties.get("documentInfo").get("type").getTextValue());
        assertEquals("string",properties.get("capabilities").get("type").getTextValue());

    }

}
