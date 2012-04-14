package org.geoserver.gsr.geometry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.schema.JsonSchema;
import org.geoserver.gsr.geometry.Envelope;
import org.geoserver.gsr.geometry.GeometryCollection;
import org.geoserver.gsr.geometry.MultiPoint;
import org.geoserver.gsr.geometry.Point;
import org.geoserver.gsr.geometry.Polygon;
import org.geoserver.gsr.geometry.Polyline;
import org.geoserver.gsr.geometry.SpatialReferenceWKID;
import org.geoserver.test.GeoServerTestSupport;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public class GeometryJSONSchemaTests extends GeoServerTestSupport {

    public void testSpatialReferenceSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(SpatialReferenceWKID.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        JsonNode wkid = properties.get("wkid");
        assertEquals("integer", wkid.get("type").getTextValue());

    }

    public void testPointSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(Point.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        JsonNode x = properties.get("x");
        assertEquals("number", x.get("type").getTextValue());
        JsonNode y = properties.get("y");
        assertEquals("number", y.get("type").getTextValue());
    }

    public void testMultipointSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(MultiPoint.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        JsonNode points = properties.get("points");
        assertEquals("array", points.get("type").getTextValue());
        JsonNode coordinates = points.get("items");
        assertEquals("number", coordinates.get("type").getTextValue());

    }

    // TODO: Complete this test and Polyline implementation
    public void testPolylineSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(Polyline.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        JsonNode paths = properties.get("paths");
        assertEquals("array", paths.get("type").getTextValue());
        JsonNode coordinates = paths.get("items");
        assertEquals("array", coordinates.get("type").getTextValue());
        JsonNode coords = coordinates.get("items");
        // assertEquals("array", coords.get("type").getTextValue());

    }

    // TODO: Complete this test and Polygon implementation
    public void testPolygonSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(Polygon.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        JsonNode rings = properties.get("rings");
        assertEquals("array", rings.get("type").getTextValue());
        JsonNode coordinates = rings.get("items");
        assertEquals("array", coordinates.get("type").getTextValue());
        JsonNode coords = coordinates.get("items");
    }

    public void testEnvelopeSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(Envelope.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        assertEquals("number", properties.get("xmin").get("type").getTextValue());
        assertEquals("number", properties.get("ymin").get("type").getTextValue());
        assertEquals("number", properties.get("xmax").get("type").getTextValue());
        assertEquals("number", properties.get("ymax").get("type").getTextValue());
    }

    public void testGeometryCollectionSchema() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchema jsonSchema = mapper.generateJsonSchema(GeometryCollection.class);
        JsonNode schemaNode = jsonSchema.getSchemaNode();
        JsonNode type = schemaNode.get("type");
        assertEquals("object", type.getTextValue());
        JsonNode properties = schemaNode.get("properties");
        assertEquals("string", properties.get("geometryType").get("type").getTextValue());
        assertEquals("array", properties.get("geometries").get("type").getTextValue());
        assertEquals("object", properties.get("geometries").get("items").get("type").getTextValue());

    }

}
