package org.geoserver.geoservices.geometry;

public class TestSpatialRef {

    private int wkid;

    public int getWkid() {
        return wkid;
    }

    public void setWkid(int wkid) {
        this.wkid = wkid;
    }
    
    public TestSpatialRef(int wkid){
        this.wkid = wkid;
    }
}
