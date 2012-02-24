package org.geoserver.geoservices.services;

import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.config.GeoServer;
import org.geoserver.geoservices.exception.ServiceError;
import org.geoserver.geoservices.exception.ServiceException;
import org.geoserver.geoservices.geometry.Envelope;
import org.geoserver.geoservices.geometry.SpatialReference;
import org.geoserver.geoservices.geometry.SpatialReferenceWKID;
import org.geoserver.wms.GetMap;
import org.geoserver.wms.GetMapRequest;
import org.geoserver.wms.MapLayerInfo;
import org.geoserver.wms.WMS;
import org.geoserver.wms.map.RenderedImageMap;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.styling.Style;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;

public class ExportMapResource extends GeoServicesResource {

    private GeoServer geoServer;

    private WMS wms;

    private String formatValue;

    private String bboxValue;

    private String sizeValue;

    private String dpiValue;

    private String layersValue;

    private String transparentValue;

    private String callback;

    private String imageFormat;

    public ExportMapResource(Context context, Request request, Response response) {
        super(context, request, response);
    }

    public ExportMapResource(Context context, Request request, Response response,
            GeoServer geoServer, WMS wms) {
        super(context, request, response);
        this.geoServer = geoServer;
        this.wms = wms;
        this.formatValue = getRequest().getResourceRef().getQueryAsForm().getFirstValue("f");
        this.bboxValue = getRequest().getResourceRef().getQueryAsForm().getFirstValue("bbox");
        this.sizeValue = getRequest().getResourceRef().getQueryAsForm().getFirstValue("size");
        this.dpiValue = getRequest().getResourceRef().getQueryAsForm().getFirstValue("dpi");
        this.layersValue = getRequest().getResourceRef().getQueryAsForm().getFirstValue("layers");
        this.transparentValue = getRequest().getResourceRef().getQueryAsForm()
                .getFirstValue("transparent");
        this.callback = getRequest().getResourceRef().getQueryAsForm().getFirstValue("callback");
        this.imageFormat = getImageFormat(getRequest().getResourceRef().getQueryAsForm()
                .getFirstValue("format"));
    }
   
    @Override
    protected Object handleObjectGet() {
        Object response = null;
        try {
            String serviceName = getAttribute("serviceName");
            ExportMapService exportMap = new ExportMapService(serviceName);
            if (this.sizeValue == null) {
                exportMap.setWidth(400);
                exportMap.setHeight(400);
            } else {
                StringTokenizer tokenizer = new StringTokenizer(this.sizeValue, ",");
                exportMap.setWidth(Integer.parseInt(tokenizer.nextToken()));
                exportMap.setHeight(Integer.parseInt(tokenizer.nextToken()));

            }
            if (this.bboxValue == null) {
                List<String> details = new ArrayList<String>();
                details.add("No BBOX parameter provided");
                response = new ServiceException(new ServiceError(
                        (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                        details));
            } else {
                StringTokenizer tokenizer = new StringTokenizer(this.bboxValue, ",");
                List<LayerGroupInfo> layerGroupInfos = geoServer.getCatalog().getLayerGroups();
                SpatialReference spatialReference = null;
                for (LayerGroupInfo layerGroupInfo : layerGroupInfos) {
                    ReferencedEnvelope mapBounds = layerGroupInfo.getBounds();
                    CoordinateReferenceSystem crf = mapBounds.getCoordinateReferenceSystem();
                    int wkid = Integer.parseInt(crf.getIdentifiers().iterator().next().getCode());
                    spatialReference = new SpatialReferenceWKID(wkid);
                }
                Double xmin = Double.parseDouble(tokenizer.nextToken());
                Double ymin = Double.parseDouble(tokenizer.nextToken());
                Double xmax = Double.parseDouble(tokenizer.nextToken());
                Double ymax = Double.parseDouble(tokenizer.nextToken());
                Envelope envelope = new Envelope(xmin, ymin, xmax, ymax, spatialReference);
                exportMap.setExtent(envelope);

                int dpi = 96;
                if (this.dpiValue != null) {
                    dpi = Integer.parseInt(this.dpiValue);
                }
                exportMap.setScale(calculateScale(exportMap.getExtent().getXmin(), exportMap
                        .getExtent().getYmin(), exportMap.getExtent().getXmax(), exportMap
                        .getExtent().getYmax(), exportMap.getHeight(), exportMap.getWidth(), dpi));
            }
            if (this.formatValue.equals("json")) {
                String url = getRequest().getResourceRef().toString().replace("json", "image");
                exportMap.setHref(url);
                response = exportMap;
            } else if (this.formatValue.equals("image")) {
                GetMapRequest getMapRequest = new GetMapRequest();
                getMapRequest.setHeight(exportMap.getHeight());
                getMapRequest.setWidth(exportMap.getWidth());
                com.vividsolutions.jts.geom.Envelope envelope = new com.vividsolutions.jts.geom.Envelope(
                        exportMap.getExtent().getXmin(), exportMap.getExtent().getYmin(), exportMap
                                .getExtent().getXmax(), exportMap.getExtent().getYmax());
                getMapRequest.setBbox(envelope);
                LayerGroupInfo layerGroupInfo = geoServer.getCatalog().getLayerGroups().get(0);
                List<LayerInfo> layerInfos = layerGroupInfo.getLayers();
                List<MapLayerInfo> mapLayerInfos = new ArrayList<MapLayerInfo>();
                List<Style> styles = new ArrayList<Style>();
                for (LayerInfo layerInfo : layerInfos) {
                    MapLayerInfo mapLayerInfo = new MapLayerInfo(layerInfo);
                    styles.add(layerInfo.getDefaultStyle().getStyle());
                    mapLayerInfo.setStyle(layerInfo.getDefaultStyle().getStyle());
                    mapLayerInfos.add(mapLayerInfo);
                }
                getMapRequest.setLayers(mapLayerInfos);
                getMapRequest.setStyles(styles);
                getMapRequest.setFormat("image/png");
                GetMap getMap = new GetMap(wms);
                RenderedImageMap webMap = (RenderedImageMap) getMap.run(getMapRequest);
                RenderedImage image = webMap.getImage();
                
                response = image.getData();

            } else {
                List<String> details = new ArrayList<String>();
                details.add("Format " + this.formatValue + " is not supported");
                response = new ServiceException(new ServiceError(
                        (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                        details));
            }
        } catch (Exception e) {
            List<String> details = new ArrayList<String>();
            details.add(e.getLocalizedMessage());
            response = new ServiceException(new ServiceError(
                    (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                    details));
        }
        return response;
    }

    /**
     * 
     * @param xmin
     * @param ymin
     * @param xmax
     * @param ymax
     * @param height
     * @param width
     * @param dpi
     * @return
     */

    private double calculateScale(double xmin, double ymin, double xmax, double ymax, int height,
            int width, int dpi) {
        double scale = 0.0d;
        double p = (dpi * 3779.527555) / 96; // number of pixels in 1 meter, for 96 dpi --> 3779.527555
        scale = ((((scale = xmax - xmin) * p) / width) + (((ymax - ymin) * p) / height)) / 2;
        return scale;
    }

    private String getImageFormat(String format) {
        String imageFormat = "";
        if (format == null) {
            imageFormat = "image/png";
        } else if (format.toLowerCase().equals("jpg")) {
            imageFormat = "image/jpg";
        } else if (format.toLowerCase().equals("png")) {
            imageFormat = "image/png";
        }
        return imageFormat;
    }

}
