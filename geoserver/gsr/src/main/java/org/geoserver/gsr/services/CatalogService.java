package org.geoserver.gsr.services;

import java.util.List;

import org.geoserver.gsr.core.AbstractService;
import org.geoserver.gsr.core.ServiceType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

@XStreamAlias(value = "")
public class CatalogService extends AbstractService {

    public CatalogService(String name, String specVersion, String productName,
            String currentVersion, List<String> folders, List<AbstractService> services) {
        super(name, ServiceType.CatalogServer);
        this.specVersion = specVersion;
        this.productName = productName;
        this.currentVersion = currentVersion;
        this.folders = folders;
        this.services = services;
    }
    
    private String specVersion;

    private String productName;
    
    private String currentVersion;

    @XStreamImplicit(itemFieldName = "folders")
    private List<String> folders;

    @XStreamImplicit(itemFieldName = "services")
    private List<AbstractService> services;

    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public List<String> getFolders() {
        return folders;
    }

    public void setFolders(List<String> folders) {
        this.folders = folders;
    }

    public List<AbstractService> getServices() {
        return services;
    }

    public void setServices(List<AbstractService> services) {
        this.services = services;
    }

}
