package org.geoserver.geoservices.services;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
public class MapResourceTest extends ResourceTest {

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

}
