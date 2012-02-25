package org.geoserver.geoservices.services;

import java.util.ArrayList;
import java.util.List;

import org.geoserver.config.GeoServer;
import org.geoserver.geoservices.exception.ServiceError;
import org.geoserver.geoservices.exception.ServiceException;
import org.geoserver.wms.WMS;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;

public class ExportMapJsonResource extends ExportMapResource {

    public ExportMapJsonResource(Context context, Request request, Response response,
            GeoServer geoServer, WMS wms) {
        super(context, request, response, geoServer, wms);
    }

    @Override
    protected Object handleObjectGet() {
        Object response = null;
        try {
            ExportMapService exportMap = prepareExportMap();
            if (this.formatValue.equals("json")) {
                String url = getRequest().getResourceRef().toString().replace("json", "image");
                exportMap.setHref(url);
                response = exportMap;
            } else {
                List<String> details = new ArrayList<String>();
                details.add("Format " + this.formatValue + " is not supported");
                response = new ServiceException(new ServiceError(
                        (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                        details));
            }
        } catch (Exception e) {
            List<String> details = new ArrayList<String>();
            details.add(e.getLocalizedMessage());
            response = new ServiceException(new ServiceError(
                    (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                    details));
        }
        return response;
    }

}
