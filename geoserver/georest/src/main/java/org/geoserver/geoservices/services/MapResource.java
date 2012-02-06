package org.geoserver.geoservices.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.geoservices.exception.ServiceError;
import org.geoserver.geoservices.exception.ServiceException;
import org.geoserver.geoservices.rest.format.GeoRestReflectiveJSONFormat;
import org.geoserver.rest.ReflectiveResource;
import org.geoserver.rest.format.DataFormat;
import org.geoserver.rest.format.ReflectiveJSONFormat;
import org.geotools.util.logging.Logging;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */
public class MapResource extends ReflectiveResource {

    protected Catalog catalog;

    private String formatValue;

    private String callback;

    /**
     * logger
     */

    static Logger LOGGER = Logging.getLogger("org.geoserver.geoservices.catalog");

    public MapResource(Context context, Request request, Response response, Catalog catalog) {
        super(context, request, response);
        this.catalog = catalog;
        this.formatValue = getRequest().getResourceRef().getQueryAsForm().getFirstValue("f");
        this.callback = getRequest().getResourceRef().getQueryAsForm().getFirstValue("callback");
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
            Object resp = null;
            if (!formatValue.equals("json")) {
                List<String> details = new ArrayList<String>();
                details.add("Format " + formatValue + " is not supported");
                return new ServiceException(new ServiceError(
                        (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                        details));

            } else {
                String serviceName = getAttribute("serviceName");
                List<LayerGroupInfo> layerGroupInfos = catalog.getLayerGroups();
                for (LayerGroupInfo layerGroupInfo : layerGroupInfos) {
                    if (layerGroupInfo.getName().equals(serviceName)) {
                        MapService mapService = new MapService(serviceName);
                        resp = mapService;
                        break;
                    } else {
                        List<String> details = new ArrayList<String>();
                        details.add("Map Service does not exist");
                        resp = new ServiceException(new ServiceError(
                                (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())),
                                "Bad Request", details));
                    }
                }
                return resp;
            }
        } catch (Exception e) {
            List<String> details = new ArrayList<String>();
            details.add(e.getMessage());
            return new ServiceException(new ServiceError(
                    (String.valueOf(Status.SERVER_ERROR_INTERNAL.getCode())),
                    "Internal Server Error", details));
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
        xstream.processAnnotations(MapService.class);
        xstream.processAnnotations(ServiceException.class);
        xstream.processAnnotations(ServiceError.class);
        // xstream.registerConverter(new CatalogServiceConverter());

    }

}
