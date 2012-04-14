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
    private List<LevelOfDetail> lods;

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

    public List<LevelOfDetail> getLods() {
        return lods;
    }

    public void setLods(List<LevelOfDetail> lods) {
        this.lods = lods;
    }

    public TileInfo(int rows, int cols, int dpi, String format, double compressionQuality,
            SpatialReference origin, List<LevelOfDetail> lods) {
        this.rows = rows;
        this.cols = cols;
        this.dpi = dpi;
        this.format = format;
        this.compressionQuality = compressionQuality;
        this.origin = origin;
        this.lods = lods;
    }

    public TileInfo() {
        // TODO Auto-generated constructor stub
    }

}
