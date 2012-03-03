package org.geoserver.geoservices.services;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.geoserver.catalog.Catalog;
import org.geoserver.config.GeoServer;
import org.geoserver.ows.util.CaseInsensitiveMap;
import org.geoserver.ows.util.KvpUtils;
import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.platform.Operation;
import org.geoserver.platform.Service;
import org.geoserver.rest.util.RESTUtils;
import org.geoserver.wms.GetMapRequest;
import org.geoserver.wms.WMS;
import org.geoserver.wms.WebMap;
import org.geoserver.wms.WebMapService;
import org.geoserver.wms.map.GetMapKvpRequestReader;
import org.geoserver.wms.map.PNGMapResponse;
import org.geoserver.wms.map.RenderedImageMap;
import org.geoserver.wms.map.RenderedImageMapResponse;
import org.restlet.Context;
import org.restlet.data.Reference;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Resource;

import com.noelios.restlet.http.HttpResponse;

public class ExportMapImageResource extends Resource {

    private Map<String, String> paramsMap;

    private GeoServer geoServer;

    private WMS wms;

    private WebMapService webMapService;

    public ExportMapImageResource(Context context, Request request, Response response,
            Map<String, String> paramsMap, GeoServer geoServer, WMS wms, WebMapService webMapService) {
        this.setContext(context);
        this.setRequest(request);
        this.setResponse(response);
        this.paramsMap = paramsMap;
        this.geoServer = geoServer;
        this.wms = wms;
        this.webMapService = webMapService;
    }

    @Override
    public void handleGet() {
        String wmsUrl = getWMSUrl();
        Map<String, String> rawMap = KvpUtils.parseQueryString(wmsUrl);
        GetMapKvpRequestReader mapKvpReader = new GetMapKvpRequestReader(wms);
        try {
            GetMapRequest mapRequest = mapKvpReader.createRequest();
            mapRequest = mapKvpReader
                    .read(mapRequest, parseMap(rawMap), caseInsensitiveKvp(rawMap));
            WebMap webMap = webMapService.getMap(mapRequest);
            RenderedImageMap imageMap = (RenderedImageMap) webMap;
            RenderedImageMapResponse mapResponse = getMapResponse(webMap.getMimeType());
            Service service = (Service) GeoServerExtensions.bean("wms-1_3_0-ServiceDescriptor");
            Operation operation = new Operation("GetMap", service, null, null);
            HttpResponse httpResponse = (HttpResponse) getResponse();
            OutputStream out = httpResponse.getHttpCall().getResponseStream();
            mapResponse.write(webMap, out, operation);
        } catch (Exception e) {
            // TODO: Log exceptions
            System.out.println(e.getLocalizedMessage());
        }

    }

    private RenderedImageMapResponse getMapResponse(String mimeType) {
        RenderedImageMapResponse mapResponse = null;
        if (mimeType.equals("image/png")) {
            return new PNGMapResponse(wms);
        }
        // TODO complete rest of Mime types
        return mapResponse;
    }

    // The following 2 private methods are taken from KvpRequestReaderTestSupport

    private Map parseMap(Map<String, String> raw) throws Exception {
        // parse like the dispatcher but make sure we don't change the original map
        HashMap input = new HashMap(raw);
        List<Throwable> errors = KvpUtils.parse(input);
        if (errors != null && errors.size() > 0)
            throw (Exception) errors.get(0);

        return caseInsensitiveKvp(input);
    }

    private Map caseInsensitiveKvp(Map input) {
        Map result = new HashMap();
        for (Iterator it = input.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            result.put(key.toUpperCase(), input.get(key));
        }
        return new CaseInsensitiveMap(result);
    }

    private String getWMSUrl() {
        Request request = getRequest();
        Reference host = request.getHostRef(); // http://localhost:8080
        Catalog catalog = geoServer.getCatalog();
        String service = "wms";
        String version = "1.3.0";
        String operation = "getMap";
        String bbox = paramsMap.get("bbox");
        String size = paramsMap.get("size");
        String imageFormat = paramsMap.get("f");
        String serviceName = "";
        Map<String, Object> attributes = request.getAttributes();
        if (attributes.get("serviceType").equals("MapServer")) {
            if (attributes.get("serviceName") != null) {
                serviceName = RESTUtils.getAttribute(getRequest(), "serviceName");
            }
        }
        if (size == null) {
            size = "500,500";
        }
        StringTokenizer sizeTokenizer = new StringTokenizer(size, ",");
        String width = "";
        String height = "";
        int numOfSizeTokens = sizeTokenizer.countTokens();
        if (numOfSizeTokens == 2) {
            width = sizeTokenizer.nextToken();
            height = sizeTokenizer.nextToken();
        } else {
            try {
                throw new Exception("Size parameter is not correct");
            } catch (Exception e) {
                // TODO: Log exceptions
                // LOGGER.log(Level.FINER, e.getMessage(), e);
            }
        }
        String layers = paramsMap.get("layers");
        if (layers == null) {
            layers = serviceName;
        } else {
            // TODO: include logic for show | hide | include | exclude
        }
        if (imageFormat == null) {
            imageFormat = "PNG";
        }
        String format = getMimeTypeFromFormat(imageFormat);
        String wmsUrl = host.toString() + "/geoserver/wms?request=" + operation + "&service="
                + service + "&version=" + version + "&bbox=" + bbox + "&width=" + width
                + "&height=" + height + "&layers=" + layers + "&format=" + format;
        return wmsUrl;
    }

    private String getMimeTypeFromFormat(String format) {
        if (format.toUpperCase().equals("PNG")) {
            return "image/png";
        } else {
            return "image/png";
        }
    }

}
