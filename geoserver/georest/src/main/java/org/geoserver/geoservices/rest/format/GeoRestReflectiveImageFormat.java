package org.geoserver.geoservices.rest.format;

import org.geoserver.rest.format.DataFormat;
import org.restlet.data.MediaType;

public class GeoRestReflectiveImageFormat extends DataFormat {

    private MediaType mediaType;

    public GeoRestReflectiveImageFormat(MediaType mediaType) {
        super(mediaType);
        this.mediaType = mediaType;
    }


}
