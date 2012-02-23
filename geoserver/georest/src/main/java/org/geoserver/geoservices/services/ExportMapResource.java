package org.geoserver.geoservices.services;

import org.geoserver.config.GeoServer;
import org.geoserver.wms.WMS;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;

public class ExportMapResource extends GeoServicesResource {

    private GeoServer geoServer;

    private WMS wms;

    public ExportMapResource(Context context, Request request, Response response) {
        super(context, request, response);
    }

    public ExportMapResource(Context context, Request request, Response response,
            GeoServer geoServer, WMS wms) {
        super(context, request, response);
        this.geoServer = geoServer;
        this.wms = wms;
    }

    @Override
    protected Object handleObjectGet() throws Exception {
        return null;
    }

}
