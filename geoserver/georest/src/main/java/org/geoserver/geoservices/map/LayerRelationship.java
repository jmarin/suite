package org.geoserver.geoservices.map;

public class LayerRelationship {

    private int id;
    
    private String name;
    
    private int relatedTableId;

    
    public LayerRelationship(int id, String name, int relLayerId){
        this.id = id;
        this.name = name;
        this.relatedTableId = relLayerId;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRelatedTableId() {
        return relatedTableId;
    }

    public void setRelatedTableId(int relatedTableId) {
        this.relatedTableId = relatedTableId;
    }
    
}
