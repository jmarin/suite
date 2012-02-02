package org.geoserver.geoservices.geometry;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */
public class RingTest {

    @Test
    public void testIsValid(){
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(0,1);
        Coordinate c3 = new Coordinate(1,1);
        Coordinate c4 = new Coordinate(1,0);
        Coordinate c5 = new Coordinate(1,2);
        
        Coordinate[] coords1 = new Coordinate[5];
        coords1[0] = c1;
        coords1[1] = c2;
        coords1[2] = c3;
        coords1[3] = c4;
        coords1[4] = c1; 
        
        Coordinate[] coords2 = new Coordinate[5];
        coords2[0] = c1;
        coords2[1] = c2;
        coords2[2] = c3;
        coords2[3] = c4;
        coords2[4] = c5;
        
        Ring validRing = new Ring(coords1);
        Ring invalidRing = new Ring(coords2);
        
        assertEquals(true,validRing.isValid());
        assertEquals(false, invalidRing.isValid());
        
    }
    
}
