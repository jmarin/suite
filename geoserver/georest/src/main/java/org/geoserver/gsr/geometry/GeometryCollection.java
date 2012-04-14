package org.geoserver.gsr.geometry;

/**
 * 
 * From the spec: An array of geometries contains a homogeneous collection of geometries. The first property specifies the geometry type, the second
 * the array of geometry objects of that type
 * 
 * @author Juan Marin, OpenGeo
 */

public class GeometryCollection {

    private GeometryType geometryType;

    private Geometry[] geometries;

    private SpatialReference spatialReference;

    public GeometryType getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(GeometryType geometryType) {
        this.geometryType = geometryType;
    }

    public Geometry[] getGeometries() {
        return geometries;
    }

    public void setGeometries(Geometry[] geometries) {
        this.geometries = geometries;
    }

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public GeometryCollection(GeometryType geometryType, Geometry[] geometries,
            SpatialReference spatialReference) {
        this.geometryType = geometryType;
        this.geometries = geometries;
        this.spatialReference = spatialReference;
    }

    public boolean isValidGeometryTypes() {
        if (this.geometries.length > 0) {
            GeometryType geomType = this.geometries[0].getGeometryType();
            for (int i = 1; i < this.geometries.length; i++) {
                if (!this.geometries[i].getGeometryType().equals(geomType)) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }

    }

}
