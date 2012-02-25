package org.geoserver.geoservices.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.geoserver.catalog.rest.AbstractCatalogFinder;
import org.geoserver.config.GeoServer;
import org.geoserver.geoservices.core.ServiceType;
import org.geoserver.geoservices.exception.ServiceError;
import org.geoserver.geoservices.exception.ServiceException;
import org.geoserver.wms.WMS;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Resource;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class ServiceFinder extends AbstractCatalogFinder {

    private GeoServer geoServer;

    private WMS wms;

    protected ServiceFinder(GeoServer geoServer, WMS wms) {
        super(geoServer.getCatalog());
        this.geoServer = geoServer;
        this.wms = wms;
    }

    @Override
    public Resource findTarget(Request request, Response response) {
        try {
            Resource resource = null;
            Map<String, Object> attributes = request.getAttributes();
            String serviceType = attributes.get("serviceType").toString();
            String operation = "";
            String params = attributes.get("params").toString();
            Map<String, String> paramsMap = getParamsMap(params);
            String format = paramsMap.get("f");
            if (attributes.get("operation") != null) {
                operation = attributes.get("operation").toString();
            }
            switch (ServiceType.valueOf(serviceType)) {
            case MapServer:
                if (operation.equals("")) {
                    resource = new MapRootResource(null, request, response, geoServer, wms);
                } else if (operation.equals("export")) {
                    if (format.equals("json")) {
                        resource = new ExportMapJsonResource(null, request, response, geoServer,
                                wms);
                    } else if (format.equals("image")) {
                        resource = new ExportMapImageResource(null, request, response, geoServer,
                                wms);
                    }
                }
                break;
            case FeatureServer:
                break;
            case GeocodeServer:
                break;
            case GeometryServer:
                break;
            case GPServer:
                break;
            case ImageServer:
                break;
            }

            // for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            // String key = entry.getKey();
            // Object value = entry.getValue();
            // System.out.println(key + ":" + value.toString());
            // }

            return resource;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getParamsMap(String params) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        StringTokenizer tokenizer = new StringTokenizer(params, "&");
        while (tokenizer.hasMoreTokens()) {
            String str = tokenizer.nextToken();
            StringTokenizer tokenizer2 = new StringTokenizer(str, "=");
            while (tokenizer2.hasMoreTokens()) {
                paramsMap.put(tokenizer2.nextToken(), tokenizer2.nextToken());
            }
        }
        return paramsMap;
    }
}
