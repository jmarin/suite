package org.geoserver.geoservices.geometry;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * GeoServices REST DRAFT 1 Specification - 9.7 Envelope
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

public class EnvelopeTest {

    /**
     * Req. 15: xmin SHALL be smaller than or equal to xmax, ymin SHALL be smaller that or equal to ymax
     */

    @Test
    public void testIsValid() {
        double xmin1 = -105.89;
        double xmax1 = -77.09;
        double ymin1 = 25.8;
        double ymax1 = 68.56;

        double xmin2 = -105.89;
        double xmax2 = -107.09;
        double ymin2 = 25.8;
        double ymax2 = 68.56;

        SpatialReference spatialReference = new SpatialReferenceWKID(4326);
        Envelope validEnvelope = new Envelope(xmin1, ymin1, xmax1, ymax1, spatialReference);
        Envelope invalidEnvelope = new Envelope(xmin2, ymin2, xmax2, ymax2, spatialReference);

        assertEquals(true, validEnvelope.isValid());
        assertEquals(false, invalidEnvelope.isValid());

    }

}
