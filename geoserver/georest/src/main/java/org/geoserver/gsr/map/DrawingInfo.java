package org.geoserver.geoservices.map;

public class DrawingInfo {

    private Renderer renderer;
    
    private double transparency;
    
    private LabelingInfo labelingInfo;

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public double getTransparency() {
        return transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    public LabelingInfo getLabelingInfo() {
        return labelingInfo;
    }

    public void setLabelingInfo(LabelingInfo labelingInfo) {
        this.labelingInfo = labelingInfo;
    }
    
    
}
