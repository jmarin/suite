package org.geoserver.geoservices.map;

import java.util.List;

public class LayerSubtype {

    private int id;

    public LayerSubtype(int id, String name, List<FieldDomain> domains) {
        this.id = id;
        this.name = name;
        this.domains = domains;
    }

    private String name;

    private List<FieldDomain> domains;

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

    public List<FieldDomain> getDomains() {
        return domains;
    }

    public void setDomains(List<FieldDomain> domains) {
        this.domains = domains;
    }

}
