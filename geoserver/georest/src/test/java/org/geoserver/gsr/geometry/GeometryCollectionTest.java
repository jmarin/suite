package org.geoserver.gsr.geometry;

import static org.junit.Assert.*;

import org.geoserver.gsr.geometry.Geometry;
import org.geoserver.gsr.geometry.GeometryCollection;
import org.geoserver.gsr.geometry.GeometryType;
import org.geoserver.gsr.geometry.Point;
import org.geoserver.gsr.geometry.Polygon;
import org.geoserver.gsr.geometry.SpatialReference;
import org.geoserver.gsr.geometry.SpatialReferenceWKID;
import org.junit.Test;

/**
 * 
 * GeoServices REST DRAFT 1 Specification - 9.8 Array
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class GeometryCollectionTest {

    /**
     * Req. 18: Each item in the "geometries" array SHALL validate against the schema of the geometry object stated in the property "geometryType", if
     * the property is provided
     */

    @Test
    public void isValidGeometryTypesTest() {
        double[] coord1 = {-77.1, 40.07};
        double[] coord2 = {-79.1, 38.07};
        double[] coord3 = {-105.1, -29.08};
        SpatialReference spatialRef = new SpatialReferenceWKID(4326);
        Point point1 = new Point(coord1[0], coord1[1], spatialRef);
        Point point2 = new Point(coord2[0], coord2[1], spatialRef);
        double[][] coords = new double[4][2];
        coords[0] = coord1;
        coords[1] = coord2;
        coords[2] = coord3;
        coords[3] = coord1;
        Polygon polygon = new Polygon(coords, spatialRef);
        Geometry[] geometries1 = new Geometry[2];
        geometries1[0] = point1;
        geometries1[1] = point2;
        Geometry[] geometries2 = new Geometry[3];
        geometries2[0] = point1;
        geometries2[1] = point2;
        geometries2[2] = polygon;
        GeometryCollection geometryArray1 = new GeometryCollection(GeometryType.POINT, geometries1,
                spatialRef);
        GeometryCollection geometryArray2 = new GeometryCollection(GeometryType.POINT, geometries2,
                spatialRef);

        assertEquals(true, geometryArray1.isValidGeometryTypes());
        assertEquals(false, geometryArray2.isValidGeometryTypes());

    }

}
