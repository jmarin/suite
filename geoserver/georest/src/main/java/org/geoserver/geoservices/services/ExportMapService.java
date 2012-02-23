package org.geoserver.geoservices.services;

import org.geoserver.geoservices.core.AbstractService;
import org.geoserver.geoservices.core.ServiceType;
import org.geoserver.geoservices.geometry.Envelope;

import com.thoughtworks.xstream.annotations.XStreamAlias;

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
