package org.geoserver.geoservices.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.measure.unit.Unit;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.config.GeoServer;
import org.geoserver.geoservices.exception.ServiceError;
import org.geoserver.geoservices.exception.ServiceException;
import org.geoserver.geoservices.geometry.Envelope;
import org.geoserver.geoservices.geometry.SpatialReferenceWKID;
import org.geoserver.geoservices.map.DocumentInfo;
import org.geoserver.geoservices.map.Layer;
import org.geoserver.geoservices.map.Table;
import org.geoserver.geoservices.map.TileInfo;
import org.geoserver.geoservices.map.TimeInfo;
import org.geoserver.geoservices.map.Units;
import org.geoserver.geoservices.rest.format.GeoRestReflectiveJSONFormat;
import org.geoserver.rest.ReflectiveResource;
import org.geoserver.rest.format.DataFormat;
import org.geoserver.rest.format.ReflectiveJSONFormat;
import org.geoserver.wms.GetMapOutputFormat;
import org.geoserver.wms.WMS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.util.logging.Logging;
import org.opengis.metadata.extent.Extent;
import org.opengis.metadata.extent.GeographicBoundingBox;
import org.opengis.metadata.extent.GeographicExtent;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.GeodeticCRS;
import org.opengis.referencing.crs.GeographicCRS;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.cs.CoordinateSystemAxis;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */
public class MapRootResource extends ReflectiveResource {

    protected Catalog catalog;

    private String formatValue;

    private String callback;

    private WMS wms;

    /**
     * logger
     */

    static Logger LOGGER = Logging.getLogger("org.geoserver.geoservices.map");

    public MapRootResource(Context context, Request request, Response response, GeoServer geoServer,
            WMS wms) {
        super(context, request, response);
        this.catalog = geoServer.getCatalog();
        this.formatValue = getRequest().getResourceRef().getQueryAsForm().getFirstValue("f");
        this.callback = getRequest().getResourceRef().getQueryAsForm().getFirstValue("callback");
        this.wms = wms;
    }

    @Override
    protected List<DataFormat> createSupportedFormats(Request request, Response response) {
        List<DataFormat> formats = new ArrayList<DataFormat>();
        // formats.add(createHTMLFormat(request,response));
        // formats.add(createXMLFormat(request,response) );
        formats.add(createJSONFormat(request, response));
        return formats;
    }

    @Override
    protected DataFormat getFormatGet() {
        DataFormat df = super.getFormatGet();
        if (df != null) {
            return df;
        } else {
            GeoRestReflectiveJSONFormat format = new GeoRestReflectiveJSONFormat();
            return format;
        }
    }

    @Override
    protected Object handleObjectGet() throws Exception {
        Object response = null;
        try {
            if (!formatValue.equals("json")) {
                List<String> details = new ArrayList<String>();
                details.add("Format " + formatValue + " is not supported");
                response = new ServiceException(new ServiceError(
                        (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())), "Bad Request",
                        details));

            } else {
                String serviceName = getAttribute("serviceName");
                List<LayerGroupInfo> layerGroupInfos = catalog.getLayerGroups();
                for (LayerGroupInfo layerGroupInfo : layerGroupInfos) {
                    if (layerGroupInfo.getName().equals(serviceName)) {
                        MapService mapService = new MapService(serviceName);
                        mapService.setServiceDescription("");
                        mapService.setDescription("");
                        mapService.setCopyrightText("");
                        mapService.setCapabilities("Map,Query,Data");
                        List<LayerInfo> layerInfos = layerGroupInfo.getLayers();
                        List<Layer> layers = new ArrayList<Layer>();
                        for (int i = 0; i < layerInfos.size(); i++) {
                            LayerInfo layerInfo = layerInfos.get(i);
                            Layer layer = new Layer(i, layerInfo.getName(), layerInfo.isEnabled(),
                                    -1, null);
                            layers.add(layer);
                        }
                        mapService.setLayers(layers);
                        List<Table> tables = new ArrayList<Table>();
                        mapService.setTables(tables);
                        ReferencedEnvelope mapBounds = layerGroupInfo.getBounds();
                        CoordinateReferenceSystem crf = mapBounds.getCoordinateReferenceSystem();
                        int wkid = Integer.parseInt(crf.getIdentifiers().iterator().next()
                                .getCode());
                        SpatialReferenceWKID spatialReference = new SpatialReferenceWKID(wkid);
                        mapService.setSpatialReference(spatialReference);
                        Envelope initialExtent = new Envelope(mapBounds.getMinX(),
                                mapBounds.getMinY(), mapBounds.getMaxX(), mapBounds.getMaxY(),
                                new SpatialReferenceWKID(wkid));
                        mapService.setInitialExtent(initialExtent);

                        Extent extent = crf.getDomainOfValidity();
                        Collection<? extends GeographicExtent> envelopes = extent
                                .getGeographicElements();
                        if (envelopes.iterator().hasNext()) {
                            GeographicBoundingBox boundingBox = (GeographicBoundingBox) envelopes
                                    .iterator().next();
                            Envelope fullExtent = new Envelope(boundingBox.getWestBoundLongitude(),
                                    boundingBox.getSouthBoundLatitude(),
                                    boundingBox.getEastBoundLongitude(),
                                    boundingBox.getNorthBoundLatitude(), new SpatialReferenceWKID(
                                            wkid));
                            mapService.setFullExtent(fullExtent);
                        }
                        mapService.setTileInfo(new TileInfo());
                        mapService.setTimeInfo(new TimeInfo());
                        mapService.setDocumentInfo(new DocumentInfo());
                        // TODO: get Map Units dynamically
                        mapService.setUnits(getDefaultMapUnits(layerGroupInfo));
                        mapService.setSupportedImageFormatTypes(getImageOutputFormats());
                        response = mapService;
                        break;
                    } else {
                        List<String> details = new ArrayList<String>();
                        details.add("Map Service does not exist");
                        response = new ServiceException(new ServiceError(
                                (String.valueOf(Status.CLIENT_ERROR_BAD_REQUEST.getCode())),
                                "Bad Request", details));
                    }
                }

            }
        } catch (Exception e) {
            List<String> details = new ArrayList<String>();
            details.add(e.getMessage());
            response = new ServiceException(new ServiceError(
                    (String.valueOf(Status.SERVER_ERROR_INTERNAL.getCode())),
                    "Internal Server Error", details));
        }
        return response;

    }

    private String getDefaultMapUnits(LayerGroupInfo layerGroupInfo) {
        if (layerGroupInfo.getLayers().size() > 0) {
            LayerInfo firstLayer = layerGroupInfo.getLayers().get(0);
            CoordinateReferenceSystem crs = firstLayer.getResource().getCRS();
            if (crs instanceof GeographicCRS || crs instanceof GeodeticCRS) {
                return "DecimalDegrees";
            } else if (crs instanceof ProjectedCRS){
                return crs.getCoordinateSystem()
                        .getAxis(crs.getCoordinateSystem().getDimension() - 1).getUnit().toString();
            } else {
                return "";
            }

        } else {
            return "";
        }
    }

    private String getImageOutputFormats() {
        Collection<GetMapOutputFormat> formats = wms.getAvailableMapFormats();
        ArrayList<String> supportedFormats = new ArrayList<String>();
        for (GetMapOutputFormat of : formats) {
            String format = of.getMimeType();
            if (format.equals("image/png")) {
                supportedFormats.add("PNG");
            } else if (format.equals("image/png; mode=8bit")) {
                supportedFormats.add("PNG8");
            } else if (format.equals("image/png; mode=24bit")) {
                supportedFormats.add("PNG24");
            } else if (format.equals("image/png; mode=32bit")) {
                supportedFormats.add("PNG32");
            } else if (format.equals("image/tiff")) {
                supportedFormats.add("TIFF");
            } else if (format.equals("image/tiff8")) {
                supportedFormats.add("TIFF8");
            } else if (format.equals("image/geotiff")) {
                supportedFormats.add("GEOTIFF");
            } else if (format.equals("image/geotiff8")) {
                supportedFormats.add("GEOTIFF8");
            } else if (format.equals("image/gif")) {
                supportedFormats.add("GIF");
            } else if (format.equals("image/jpeg")) {
                supportedFormats.add("JPG");
            } else if (format.equals("application/pdf")) {
                supportedFormats.add("PDF");
            } else if (format.equals("image/svg+xml")) {
                supportedFormats.add("SVG");
            }
        }
        String listOfSupportedFormats = supportedFormats.get(0);
        for (int i = 1; i < supportedFormats.size(); i++) {
            listOfSupportedFormats += "," + supportedFormats.get(i);
        }
        return listOfSupportedFormats;
    }

    @Override
    protected ReflectiveJSONFormat createJSONFormat(Request request, Response response) {
        GeoRestReflectiveJSONFormat format = new GeoRestReflectiveJSONFormat();
        configureXStream(format.getXStream());
        return format;
    }

}