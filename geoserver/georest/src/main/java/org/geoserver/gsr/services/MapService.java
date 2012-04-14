package org.geoserver.geoservices.services;

import java.util.List;

import org.geoserver.geoservices.core.AbstractService;
import org.geoserver.geoservices.core.ServiceType;
import org.geoserver.geoservices.geometry.Envelope;
import org.geoserver.geoservices.geometry.SpatialReference;
import org.geoserver.geoservices.map.DocumentInfo;
import org.geoserver.geoservices.map.Layer;
import org.geoserver.geoservices.map.Table;
import org.geoserver.geoservices.map.TileInfo;
import org.geoserver.geoservices.map.TimeInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

@XStreamAlias(value = "")
public class MapService extends AbstractService {

    public MapService(String name) {
        super(name, ServiceType.MapServer);
        this.mapName = name;
    }

    private String serviceDescription;

    private String mapName;

    private String description;

    private String copyrightText;

    @XStreamImplicit(itemFieldName = "layers")
    private List<Layer> layers;

    @XStreamImplicit(itemFieldName = "tables")
    private List<Table> tables;

    private SpatialReference spatialReference;

    private boolean singleFusedMapCache;

    private TileInfo tileInfo;

    private Envelope initialExtent;

    private Envelope fullExtent;

    private TimeInfo timeInfo;

    private String units;

    private String supportedImageFormatTypes;

    private DocumentInfo documentInfo;

    private String capabilities;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public boolean isSingleFusedMapCache() {
        return singleFusedMapCache;
    }

    public void setSingleFusedMapCache(boolean singleFusedMapCache) {
        this.singleFusedMapCache = singleFusedMapCache;
    }

    public TileInfo getTileInfo() {
        return tileInfo;
    }

    public void setTileInfo(TileInfo tileInfo) {
        this.tileInfo = tileInfo;
    }

    public Envelope getInitialExtent() {
        return initialExtent;
    }

    public void setInitialExtent(Envelope initialExtent) {
        this.initialExtent = initialExtent;
    }

    public Envelope getFullExtent() {
        return fullExtent;
    }

    public void setFullExtent(Envelope fullExtent) {
        this.fullExtent = fullExtent;
    }

    public TimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(TimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getSupportedImageFormatTypes() {
        return supportedImageFormatTypes;
    }

    public void setSupportedImageFormatTypes(String supportedImageFormatTypes) {
        this.supportedImageFormatTypes = supportedImageFormatTypes;
    }

    public DocumentInfo getDocumentInfo() {
        return documentInfo;
    }

    public void setDocumentInfo(DocumentInfo documentInfo) {
        this.documentInfo = documentInfo;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

}
