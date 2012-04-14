package org.geoserver.gsr.geometry;

import static org.junit.Assert.*;

import org.geoserver.gsr.geometry.Ring;
import org.junit.Test;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */
public class RingTest {

    @Test
    public void testIsValid(){
        double[] c1 = {0,0};
        double[] c2 = {0,1};
        double[] c3 = {1,1};
        double[] c4 = {1,0};
        double[] c5 = {1,2};
        
        double[][] coords1 = new double[5][2];
        coords1[0] = c1;
        coords1[1] = c2;
        coords1[2] = c3;
        coords1[3] = c4;
        coords1[4] = c1; 
        
        double[][] coords2 = new double[5][2];
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
