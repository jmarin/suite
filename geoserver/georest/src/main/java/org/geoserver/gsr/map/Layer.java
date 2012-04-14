package org.geoserver.geoservices.map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

@XStreamAlias(value = "")
public class Layer extends AbstractLayer {

    private boolean defaultVisibility;

    private int parentLayerId;

    private int[] subLayerIds;

    public boolean isDefaultVisibility() {
        return defaultVisibility;
    }

    public void setDefaultVisibility(boolean defaultVisibility) {
        this.defaultVisibility = defaultVisibility;
    }

    public int getParentLayerId() {
        return parentLayerId;
    }

    public void setParentLayerId(int parentLayerId) {
        this.parentLayerId = parentLayerId;
    }

    public int[] getSubLayerIds() {
        return subLayerIds;
    }

    public void setSubLayerIds(int[] subLayerIds) {
        this.subLayerIds = subLayerIds;
    }
    
    public Layer(int id, String name, boolean isDefaultVisibility, int parentId, int[] sublayerIds){
        super(id, name);
        this.defaultVisibility = isDefaultVisibility;
        this.parentLayerId = parentId;
        this.subLayerIds = sublayerIds;
    }

}
