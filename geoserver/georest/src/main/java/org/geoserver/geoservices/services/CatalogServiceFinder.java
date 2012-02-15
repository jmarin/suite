package org.geoserver.geoservices.services;

import java.util.ArrayList;
import java.util.List;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.rest.AbstractCatalogFinder;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Resource;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class CatalogServiceFinder extends AbstractCatalogFinder {

    protected CatalogServiceFinder(Catalog catalog) {
        super(catalog);
    }

    @Override
    public Resource findTarget(Request request, Response response) {
        try {
            return new CatalogResource(null, request, response, catalog);
        } catch (Exception e) {
            List<String> details = new ArrayList<String>();
            details.add(e.getMessage());
            return null;
//            return new ServiceException(new ServiceError(
//                    (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
//                    details));
        }

    }

}
