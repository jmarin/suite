package org.geoserver.geoservices.geometry;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public class Ring {

    private Coordinate[] coordinates;

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }
    
    public Ring(Coordinate[] coords) {
        this.coordinates = coords;
    }
    

    public boolean isValid() {
        int size = coordinates.length;
        if (size > 0) {
            Coordinate firstCoordinate = coordinates[0];
            Coordinate lastCoordinate = coordinates[coordinates.length - 1];
            if (firstCoordinate.equals(lastCoordinate)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
