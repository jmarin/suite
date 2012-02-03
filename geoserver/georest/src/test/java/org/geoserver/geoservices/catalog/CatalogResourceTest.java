package org.geoserver.geoservices.catalog;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.geoserver.geoservices.core.ServiceType;
import org.geoserver.test.GeoServerTestSupport;

/**
 * GeoServices REST DRAFT 1 Specification - 6.2.1 Catalog URI
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class CatalogResourceTest extends GeoServerTestSupport {

    /**
     * Req. 1: The Catalog resource SHALL accept requests that conform to the URI template in Table 3 and use any HTTP method identified in the same
     * table (GET) Req. 2: A Catalog resource SHALL support all parameters and values specified in Table 4 (f=json)
     */

    public void testGetCatalogRootURI() throws Exception {
        JSON json = getAsJSON("/rest/services/?f=json");
        assertTrue(json instanceof JSONObject);
        JSONObject jsonObject = (JSONObject) json;
        assertEquals("1.0", jsonObject.get("specVersion"));
        assertEquals("OpenGeo Suite Enterprise Edition", jsonObject.get("productName"));
        assertEquals(ServiceType.CatalogServer.toString(), jsonObject.get("type"));
        JSONArray folders = (JSONArray) jsonObject.get("folders");
        assertEquals(5, folders.size());
        JSONArray services = (JSONArray) jsonObject.get("services");
        assertEquals(1, services.size());
    }

    /**
     * GeoServices REST DRAFT 1 Specification - 7.3 Exceptions Req. 3: A request to a resource of a Geoservice REST API implementation SHALL result in
     * an exception, if and only if the request violates a request requirement, unless an internal processing exception occurs
     * 
     * This tests for a request with the wrong format
     */
    public void testServiceException() throws Exception {
        JSON json = getAsJSON("/rest/services/?f=xxx");
        assertTrue(json instanceof JSONObject);
        JSONObject jsonObject = (JSONObject) json;
        JSONObject error = (JSONObject) jsonObject.get("error");
        assertTrue(error instanceof JSONObject);
        String code = (String) error.get("code");
        assertEquals("400", code);
        String message = (String) error.get("message");
        assertEquals("Bad Request", message);
        JSONArray details = (JSONArray) error.get("details");
        assertTrue(details instanceof JSONArray);
        assertEquals("Format xxx is not supported", details.getString(0));
    }

}
