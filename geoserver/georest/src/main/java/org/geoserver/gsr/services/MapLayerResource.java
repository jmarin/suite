package org.geoserver.geoservices.services;

import java.util.ArrayList;
import java.util.List;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.config.GeoServer;
import org.geoserver.geoservices.exception.ServiceError;
import org.geoserver.geoservices.exception.ServiceException;
import org.geoserver.geoservices.geometry.Envelope;
import org.geoserver.geoservices.geometry.SpatialReference;
import org.geoserver.geoservices.map.MapLayer;
import org.geoserver.rest.util.RESTUtils;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;

public class MapLayerResource extends GeoServicesResource {

    private GeoServer geoServer;

    private int layerId;

    public MapLayerResource(Context context, Request request, Response response,
            GeoServer geoServer, int layerId) {
        super(context, request, response);
        this.layerId = layerId;
        this.geoServer = geoServer;
    }

    @Override
    protected Object handleObjectGet() throws Exception {
        Object response = null;
        MapLayer layer = null;
        boolean layerExists = false;
        String serviceName = RESTUtils.getAttribute(getRequest(), "serviceName");
        Catalog catalog = geoServer.getCatalog();
        LayerGroupInfo layerGroupInfo = catalog.getLayerGroupByName(serviceName);
        List<LayerInfo> layerInfos = layerGroupInfo.getLayers();
        LayerInfo layerInfo = null;
        for (int i = 0; i < layerInfos.size(); i++) {
            if (layerId == i) {
                layerExists = true;
                layerInfo = layerInfos.get(i);
                layer = new MapLayer(layerId, layerInfo.getName());
                layer.setType("Feature Layer");
                double xmin = layerInfo.getResource().boundingBox().getMinX();
                double ymin = layerInfo.getResource().boundingBox().getMinY();
                double xmax = layerInfo.getResource().boundingBox().getMaxX();
                double ymax = layerInfo.getResource().boundingBox().getMaxY();
                SpatialReference spatialReference = null;
                
                
                
                layer.setExtent(new Envelope(xmin, ymin, xmax, ymax, spatialReference));
                
            }
        }
        if (!layerExists) {
            List<String> details = new ArrayList<String>();
            details.add("Layer {" + this.layerId + "} does not exist");
            response = new ServiceException(new ServiceError(
                    (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                    details));
        } else {
            response = layer;
        }
        return response;
    }
}
