package org.geoserver.gsr.services;

import org.geoserver.gsr.core.AbstractService;
import org.geoserver.gsr.core.ServiceType;
import org.geoserver.gsr.geometry.Envelope;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

@XStreamAlias("")
public class ExportMapService extends AbstractService {

    public ExportMapService(String name) {
        super(name, ServiceType.MapServer);
    }

    private String href;

    private int width;

    private int height;

    private Envelope extent;

    private double scale;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Envelope getExtent() {
        return extent;
    }

    public void setExtent(Envelope extent) {
        this.extent = extent;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

}
