package org.geoserver.geoservices.geometry;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public class Path {

    private Coordinate[] coordinates;
    
    public Path(Coordinate[] coords){
        this.coordinates = coords;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }
    
    
}
