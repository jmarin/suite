package org.geoserver.geoservices.map;

import java.util.List;

import org.geoserver.geoservices.geometry.SpatialReference;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

@XStreamAlias(value = "")
public class TileInfo {

    private int rows;
    
    private int cols;
    
    private int dpi;
    
    private String format;
    
    private double compressionQuality;
    
    private SpatialReference origin;
    
    @XStreamImplicit(itemFieldName = "lods")
    private List<LevelsOfDetail> lods;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public double getCompressionQuality() {
        return compressionQuality;
    }

    public void setCompressionQuality(double compressionQuality) {
        this.compressionQuality = compressionQuality;
    }

    public SpatialReference getOrigin() {
        return origin;
    }

    public void setOrigin(SpatialReference origin) {
        this.origin = origin;
    }

    public List<LevelsOfDetail> getLods() {
        return lods;
    }

    public void setLods(List<LevelsOfDetail> lods) {
        this.lods = lods;
    }
    
}
