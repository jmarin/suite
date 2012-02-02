package org.geoserver.geoservices.geometry;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * GeoServices REST DRAFT 3 Specification 9.8 Array
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
        Coordinate coord1 = new Coordinate(-77.1, 40.07);
        Coordinate coord2 = new Coordinate(-79.1, 38.07);
        Coordinate coord3 = new Coordinate(-105.1, -29.08);
        SpatialReference spatialRef = new SpatialReferenceWKID(4326);
        Point point1 = new Point(coord1.getX(), coord1.getY(), spatialRef);
        Point point2 = new Point(coord2.getX(), coord2.getY(), spatialRef);
        Ring[] rings = new Ring[1];
        Coordinate[] coords = new Coordinate[4];
        coords[0] = coord1;
        coords[1] = coord2;
        coords[2] = coord3;
        coords[3] = coord1;
        Ring ring = new Ring(coords);
        rings[0] = ring;
        Polygon polygon = new Polygon(rings, spatialRef);
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
