package org.geoserver.geoservices.services;

import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.List;

import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.config.GeoServer;
import org.geoserver.geoservices.rest.format.GeoRestReflectiveImageFormat;
import org.geoserver.rest.format.DataFormat;
import org.geoserver.wms.GetMap;
import org.geoserver.wms.GetMapRequest;
import org.geoserver.wms.MapLayerInfo;
import org.geoserver.wms.WMS;
import org.geoserver.wms.map.RenderedImageMap;
import org.geotools.styling.Style;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;

public class ExportMapImageResource extends ExportMapResource {

    public ExportMapImageResource(Context context, Request request, Response response,
            GeoServer geoServer, WMS wms) {
        super(context, request, response, geoServer, wms);
        this.imageFormat = getImageFormat(getRequest().getResourceRef().getQueryAsForm()
                .getFirstValue("format"));
    }

    @Override
    protected List<DataFormat> createSupportedFormats(Request request, Response response) {
        List<DataFormat> formats = new ArrayList<DataFormat>();
        formats.add(new GeoRestReflectiveImageFormat(MediaType.IMAGE_PNG));
        // formats.add(createJSONFormat(request, response));
        return formats;
    }

    @Override
    protected DataFormat getFormatGet() {
        GeoRestReflectiveImageFormat format = new GeoRestReflectiveImageFormat(MediaType.IMAGE_PNG);
        return format;
    }

    @Override
    public Object handleObjectGet() {
        Object response = null;
        try {
            ExportMapService exportMap = prepareExportMap();
            if (this.formatValue.equals("image")) {
                GetMapRequest getMapRequest = new GetMapRequest();
                getMapRequest.setHeight(exportMap.getHeight());
                getMapRequest.setWidth(exportMap.getWidth());
                com.vividsolutions.jts.geom.Envelope env = new com.vividsolutions.jts.geom.Envelope(
                        exportMap.getExtent().getXmin(), exportMap.getExtent().getYmin(), exportMap
                                .getExtent().getXmax(), exportMap.getExtent().getYmax());
                getMapRequest.setBbox(env);
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
                getMapRequest.setFormat(this.imageFormat);
                GetMap getMap = new GetMap(wms);
                RenderedImageMap webMap = (RenderedImageMap) getMap.run(getMapRequest);
                RenderedImage image = webMap.getImage();
                response = image;

            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return response;
    }

    private String getImageFormat(String format) {
        String imageFormat = "";
        if (format == null) {
            imageFormat = "image/png";
        } else if (format.toLowerCase().equals("jpg")) {
            imageFormat = "image/jpeg";
        } else if (format.toLowerCase().equals("png")) {
            imageFormat = "image/png";
        }
        return imageFormat;
    }

}
