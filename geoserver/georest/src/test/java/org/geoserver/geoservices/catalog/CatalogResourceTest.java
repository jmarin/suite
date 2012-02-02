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
     * table
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

}
