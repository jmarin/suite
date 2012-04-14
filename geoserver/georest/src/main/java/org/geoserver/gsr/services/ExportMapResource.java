package org.geoserver.geoservices.services;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.config.GeoServer;
import org.geoserver.geoservices.geometry.Envelope;
import org.geoserver.geoservices.geometry.SpatialReference;
import org.geoserver.geoservices.geometry.SpatialReferenceWKID;
import org.geoserver.rest.util.RESTUtils;
import org.geoserver.wms.WMS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public abstract class ExportMapResource extends GeoServicesResource {

    protected GeoServer geoServer;

    protected WMS wms;

    protected String formatValue;

    protected String bboxValue;

    protected String sizeValue;

    protected String dpiValue;

    protected String layersValue;

    protected String transparentValue;

    protected String callback;

    protected String imageFormat;

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
        // this.imageFormat = getImageFormat(getRequest().getResourceRef().getQueryAsForm()
        // .getFirstValue("format"));
    }

    protected ExportMapService prepareExportMap() {
        String serviceName = RESTUtils.getAttribute(getRequest(), "serviceName");
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
            // response.new ServiceException(new ServiceError(
            // (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
            // details));
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
                    .getExtent().getYmin(), exportMap.getExtent().getXmax(), exportMap.getExtent()
                    .getYmax(), exportMap.getHeight(), exportMap.getWidth(), dpi));
        }
        return exportMap;
    }

    protected double calculateScale(double xmin, double ymin, double xmax, double ymax, int height,
            int width, int dpi) {
        double scale = 0.0d;
        double p = (dpi * 3779.527555) / 96; // number of pixels in 1 meter, for 96 dpi --> 3779.527555
        scale = ((((scale = xmax - xmin) * p) / width) + (((ymax - ymin) * p) / height)) / 2;
        return scale;
    }

}
