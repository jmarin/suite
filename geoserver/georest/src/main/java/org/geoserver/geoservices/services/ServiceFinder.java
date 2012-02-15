package org.geoserver.geoservices.services;

import java.util.Map;

import org.geoserver.catalog.rest.AbstractCatalogFinder;
import org.geoserver.config.GeoServer;
import org.geoserver.geoservices.core.ServiceType;
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
            switch (ServiceType.valueOf(serviceType)) {
            case MapServer:
                resource = new MapResource(null, request, response, geoServer, wms);
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
            // return new ServiceException(new ServiceError(
            // (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
            // details));
        }

    }

}
