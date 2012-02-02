package org.geoserver.geoservices.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.WorkspaceInfo;
import org.geoserver.geoservices.core.AbstractService;
import org.geoserver.geoservices.geometry.GeometryService;
import org.geoserver.geoservices.rest.format.GeoRestReflectiveJSONFormat;
import org.geoserver.rest.ReflectiveResource;
import org.geoserver.rest.format.ReflectiveJSONFormat;
import org.geotools.util.logging.Logging;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class CatalogResource extends ReflectiveResource {

    /**
     * logger
     */

    static Logger LOGGER = Logging.getLogger("org.geoserver.geoservices.catalog");

    /**
     * GeoServer catalog
     */
    protected Catalog catalog;

    /**
     * The class of the resource
     */

    protected Class clazz;

    public CatalogResource(Context context, Request request, Response response, Catalog catalog) {
        super(context, request, response);
        this.catalog = catalog;
    }

    @Override
    protected Object handleObjectGet() throws Exception {
        List<WorkspaceInfo> workspaces = catalog.getWorkspaces();
        List<String> folders = new ArrayList<String>();
        for (WorkspaceInfo workspace : workspaces) {
            folders.add(workspace.getName());
        }

        List<AbstractService> services = new ArrayList<AbstractService>();
        GeometryService geometryService = new GeometryService("Geometry");
        services.add(geometryService);
        return new CatalogService("services", "1.0", "OpenGeo Suite Enterprise Edition", "2.4.4", folders, services);
    }

    @Override
    protected ReflectiveJSONFormat createJSONFormat(Request request,Response response) {
        GeoRestReflectiveJSONFormat format = new GeoRestReflectiveJSONFormat();
        configureXStream( format.getXStream() );
        return format;
    }
    
       
    
    /**
     * Method for subclasses to customize of modify the xstream instance being used to persist and depersist XML and JSON.
     */
    @Override
    protected void configureXStream(XStream xstream) {
        xstream.processAnnotations(CatalogService.class);
        //xstream.registerConverter(new CatalogServiceConverter());
    }

}
