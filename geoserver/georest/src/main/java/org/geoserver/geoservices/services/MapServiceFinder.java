package org.geoserver.geoservices.services;

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

public class MapServiceFinder extends AbstractCatalogFinder {

    protected MapServiceFinder(Catalog catalog) {
        super(catalog);
    }
    
    @Override
    public Resource findTarget(Request request, Response response) {
        return null;
    }

}
