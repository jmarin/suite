package org.geoserver.geoservices.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.geoservices.core.AbstractService;
import org.geoserver.geoservices.exception.ServiceError;
import org.geoserver.geoservices.exception.ServiceException;
import org.geotools.util.logging.Logging;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class CatalogResource extends GeoServicesResource {

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

    private String formatValue;

    private String callback;

    public CatalogResource(Context context, Request request, Response response, Catalog catalog) {
        super(context, request, response);
        this.catalog = catalog;
        this.formatValue = getRequest().getResourceRef().getQueryAsForm().getFirstValue("f");
        this.callback = getRequest().getResourceRef().getQueryAsForm().getFirstValue("callback");
    }

    @Override
    protected Object handleObjectGet() throws Exception {
        try {
            if (!formatValue.equals("json")) {
                List<String> details = new ArrayList<String>();
                details.add("Format " + formatValue + " is not supported");
                return new ServiceException(new ServiceError(
                        (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                        details));

            } else {
                List<AbstractService> services = new ArrayList<AbstractService>();
                GeometryService geometryService = new GeometryService("Geometry");
                services.add(geometryService);
                List<LayerGroupInfo> layerGroupsInfo = catalog.getLayerGroups();
                for (LayerGroupInfo layerGroupInfo : layerGroupsInfo) {
                    MapService mapService = new MapService(layerGroupInfo.getName());
                    services.add(mapService);
                }
                List<String> folders = new ArrayList<String>();
                CatalogService catalogService = new CatalogService("services", "1.0",
                        "OpenGeo Suite Enterprise Edition", "2.4.4", folders, services);
                if (callback == null) {
                    return catalogService;
                } else {
                    return catalogService;
                }
            }
        } catch (Exception e) {
            List<String> details = new ArrayList<String>();
            details.add(e.getMessage());
            return new ServiceException(new ServiceError(
                    (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                    details));

        }

    }

}
