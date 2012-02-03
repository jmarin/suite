package org.geoserver.geoservices.catalog;

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

public class ServiceFinder extends AbstractCatalogFinder {

    protected ServiceFinder(Catalog catalog) {
        super(catalog);
    }

    @Override
    public Resource findTarget(Request request, Response response) {
        try {
            return new CatalogResource(null, request, response, catalog);    
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        
    }

}
