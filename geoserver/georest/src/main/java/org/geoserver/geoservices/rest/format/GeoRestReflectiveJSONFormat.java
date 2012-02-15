package org.geoserver.geoservices.rest.format;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import org.geoserver.geoservices.geometry.SpatialReference;
import org.geoserver.geoservices.geometry.SpatialReferenceWKID;
import org.geoserver.geoservices.geometry.SpatialReferenceWKT;
import org.geoserver.rest.format.ReflectiveJSONFormat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class GeoRestReflectiveJSONFormat extends ReflectiveJSONFormat {

    XStream xstream;

    public XStream getXstream() {
        return xstream;
    }

    public void setXstream(XStream xstream) {
        this.xstream = xstream;
    }

    public GeoRestReflectiveJSONFormat() {
        super();
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        xstream.addDefaultImplementation(SpatialReferenceWKID.class, SpatialReference.class);
        xstream.addDefaultImplementation(SpatialReferenceWKT.class, SpatialReference.class);
        this.xstream = xstream;
    }

    @Override
    protected Object read(InputStream input) throws IOException {
        return xstream.fromXML(input);
    }

    @Override
    protected void write(Object data, OutputStream output) throws IOException {
        xstream.toXML(data, output);
    }

}
