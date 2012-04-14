package org.geoserver.geoservices.map;

import java.util.List;

import org.geoserver.geoservices.geometry.Envelope;
import org.geoserver.geoservices.geometry.GeometryType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias(value = "")
public class MapLayer extends AbstractLayer {

    private String type;

    private String description;

    private String copyrightText;

    @XStreamImplicit(itemFieldName = "relationships")
    private List<LayerRelationship> relationships;

    private GeometryType geometryType;

    private double minScale;

    private double maxScale;

    private Envelope extent;

    private DrawingInfo drawingInfo;

    private LayerTimeInfo timeInfo;

    private boolean hasAttachements;

    private HtmlPopupType htmlPopupType;

    private String objectIdField;

    private String displayField;

    private String typeIdField;

    private List<Field> fields;

    private List<LayerSubtype> types;

    private List<String> capabilities;

    public MapLayer(int id, String name, String type, String description, String copyrightText,
            List<LayerRelationship> relationships, GeometryType geometryType, double minScale,
            double maxScale, Envelope extent, DrawingInfo drawingInfo, LayerTimeInfo timeInfo,
            boolean hasAttachements, HtmlPopupType htmlPopupType, String objectIdField,
            String displayField, String typeIdField, List<Field> fields, List<LayerSubtype> types,
            List<String> capabilities) {
        super(id, name);
        this.type = type;
        this.description = description;
        this.copyrightText = copyrightText;
        this.relationships = relationships;
        this.geometryType = geometryType;
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.extent = extent;
        this.drawingInfo = drawingInfo;
        this.timeInfo = timeInfo;
        this.hasAttachements = hasAttachements;
        this.htmlPopupType = htmlPopupType;
        this.objectIdField = objectIdField;
        this.displayField = displayField;
        this.typeIdField = typeIdField;
        this.fields = fields;
        this.types = types;
        this.capabilities = capabilities;
    }

    public MapLayer(int id, String name) {
        super(id, name);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<LayerRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<LayerRelationship> relationships) {
        this.relationships = relationships;
    }

    public GeometryType getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(GeometryType geometryType) {
        this.geometryType = geometryType;
    }

    public double getMinScale() {
        return minScale;
    }

    public void setMinScale(double minScale) {
        this.minScale = minScale;
    }

    public double getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(double maxScale) {
        this.maxScale = maxScale;
    }

    public Envelope getExtent() {
        return extent;
    }

    public void setExtent(Envelope extent) {
        this.extent = extent;
    }

    public DrawingInfo getDrawingInfo() {
        return drawingInfo;
    }

    public void setDrawingInfo(DrawingInfo drawingInfo) {
        this.drawingInfo = drawingInfo;
    }

    public LayerTimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(LayerTimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    public boolean isHasAttachements() {
        return hasAttachements;
    }

    public void setHasAttachements(boolean hasAttachements) {
        this.hasAttachements = hasAttachements;
    }

    public HtmlPopupType getHtmlPopupType() {
        return htmlPopupType;
    }

    public void setHtmlPopupType(HtmlPopupType htmlPopupType) {
        this.htmlPopupType = htmlPopupType;
    }

    public String getObjectIdField() {
        return objectIdField;
    }

    public void setObjectIdField(String objectIdField) {
        this.objectIdField = objectIdField;
    }

    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }

    public String getTypeIdField() {
        return typeIdField;
    }

    public void setTypeIdField(String typeIdField) {
        this.typeIdField = typeIdField;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<LayerSubtype> getTypes() {
        return types;
    }

    public void setTypes(List<LayerSubtype> types) {
        this.types = types;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

}
