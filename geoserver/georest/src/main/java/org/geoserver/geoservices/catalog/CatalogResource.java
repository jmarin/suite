package org.geoserver.geoservices.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.WorkspaceInfo;
import org.geoserver.geoservices.core.AbstractService;
import org.geoserver.geoservices.exception.ServiceError;
import org.geoserver.geoservices.exception.ServiceException;
import org.geoserver.geoservices.geometry.GeometryService;
import org.geoserver.geoservices.rest.format.GeoRestReflectiveJSONFormat;
import org.geoserver.rest.ReflectiveResource;
import org.geoserver.rest.format.DataFormat;
import org.geoserver.rest.format.ReflectiveJSONFormat;
import org.geoserver.rest.format.ReflectiveXMLFormat;
import org.geotools.util.logging.Logging;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;

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
    protected List<DataFormat> createSupportedFormats(Request request, Response response) {
        List<DataFormat> formats = new ArrayList<DataFormat>();
        // formats.add(createHTMLFormat(request,response));
        // formats.add(createXMLFormat(request,response) );
        formats.add(createJSONFormat(request, response));

        return formats;
    }

    @Override
    protected DataFormat getFormatGet() {
        DataFormat df = super.getFormatGet();
        if (df != null) {
            return df;
        } else {
            GeoRestReflectiveJSONFormat format = new GeoRestReflectiveJSONFormat();
            return format;
        }
    }

    @Override
    protected Object handleObjectGet() throws Exception {
        try {
            String format = getAttribute("format");
            if (!format.equals("json")) {
                List<String> details = new ArrayList<String>();
                details.add("Format " + format + " is not supported");

                return new ServiceException(new ServiceError(
                        (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                        details));

            } else {
                System.out.println(format);
                List<WorkspaceInfo> workspaces = catalog.getWorkspaces();
                List<String> folders = new ArrayList<String>();
                for (WorkspaceInfo workspace : workspaces) {
                    folders.add(workspace.getName());
                }

                List<AbstractService> services = new ArrayList<AbstractService>();
                GeometryService geometryService = new GeometryService("Geometry");
                services.add(geometryService);
                return new CatalogService("services", "1.0", "OpenGeo Suite Enterprise Edition",
                        "2.4.4", folders, services);
            }
        } catch (Exception e) {
            List<String> details = new ArrayList<String>();
            details.add(e.getMessage());
            return new ServiceException(new ServiceError(
                    (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                    details));

        }

    }

    @Override
    protected ReflectiveJSONFormat createJSONFormat(Request request, Response response) {
        GeoRestReflectiveJSONFormat format = new GeoRestReflectiveJSONFormat();
        configureXStream(format.getXStream());
        return format;
    }

    @Override
    protected void configureXStream(XStream xstream) {
        xstream.processAnnotations(CatalogService.class);
        xstream.processAnnotations(ServiceException.class);
        xstream.processAnnotations(ServiceError.class);
        // xstream.registerConverter(new CatalogServiceConverter());
    }

}
