package org.geoserver.geoservices.services;

import java.util.ArrayList;
import java.util.List;

import org.geoserver.geoservices.rest.format.GeoRestReflectiveJSONFormat;
import org.geoserver.rest.ReflectiveResource;
import org.geoserver.rest.format.DataFormat;
import org.geoserver.rest.format.ReflectiveJSONFormat;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public class GeoServicesResource extends ReflectiveResource {

    public GeoServicesResource(Context context, Request request, Response response) {
        super(context, request, response);
    }

    @Override
    protected Object handleObjectGet() throws Exception {
        return null;
    }

    @Override
    protected List<DataFormat> createSupportedFormats(Request request, Response response) {
        List<DataFormat> formats = new ArrayList<DataFormat>();
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
    protected ReflectiveJSONFormat createJSONFormat(Request request, Response response) {
        GeoRestReflectiveJSONFormat format = new GeoRestReflectiveJSONFormat();
        configureXStream(format.getXStream());
        return format;
    }

}
