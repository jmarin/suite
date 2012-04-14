package org.geoserver.gsr.services;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.geoserver.test.GeoServerTestSupport;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public abstract class ResourceTest extends GeoServerTestSupport {

    /**
     * GeoServices REST DRAFT 1 Specification - 7.3 Exceptions Req. 3: A request to a resource of a Geoservice REST API implementation SHALL result in
     * an exception, if and only if the request violates a request requirement, unless an internal processing exception occurs
     * 
     * This tests for a request with the wrong format
     * 
     */

    protected String baseURL;

    public void testServiceException() throws Exception {
        if (baseURL != null) {
            JSON json = getAsJSON(baseURL + "?f=xxx");
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
}
