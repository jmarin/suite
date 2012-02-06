package org.geoserver.geoservices.services;

import java.util.Map;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.rest.AbstractCatalogFinder;
import org.geoserver.geoservices.core.ServiceType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Resource;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class ServiceFinder extends AbstractCatalogFinder {

    protected ServiceFinder(Catalog catalog) {
        super(catalog);
    }

    @Override
    public Resource findTarget(Request request, Response response) {
        try {
            Resource resource = null;
            Map<String, Object> attributes = request.getAttributes();
            String serviceType = attributes.get("serviceType").toString();
            switch (ServiceType.valueOf(serviceType)) {
            case MapServer:
                resource = new MapResource(null, request, response, catalog);
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
