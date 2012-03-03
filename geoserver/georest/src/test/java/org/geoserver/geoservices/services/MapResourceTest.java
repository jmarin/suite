package org.geoserver.geoservices.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.LayerGroupInfo;
import org.geotools.geometry.jts.ReferencedEnvelope;

import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockServletOutputStream;

public class MapResourceTest extends ResourceTest {

    protected Catalog catalog;

    protected LayerGroupInfo layerGroup;

    @Override
    protected void setUpInternal() throws Exception {
        super.setUpInternal();
        catalog = getCatalog();
        layerGroup = catalog.getFactory().createLayerGroup();
        layerGroup.setName("SanFrancisco");
        layerGroup.getLayers().add(catalog.getLayerByName("Bridges"));
        layerGroup.getLayers().add(catalog.getLayerByName("Buildings"));
        layerGroup.getLayers().add(catalog.getLayerByName("Forests"));
        layerGroup.getLayers().add(catalog.getLayerByName("Lakes"));
        layerGroup.getLayers().add(catalog.getLayerByName("Ponds"));
        layerGroup.getLayers().add(catalog.getLayerByName("Streams"));
        ReferencedEnvelope envelope = layerGroup.getLayers().get(0).getResource().boundingBox();
        layerGroup.setBounds(envelope);
        catalog.add(layerGroup);
    }

    @Override
    protected void tearDownInternal() throws Exception {
        super.tearDownInternal();
        catalog.remove(layerGroup);
    }

    protected BufferedImage getAsImage(String path, String mime) throws Exception {
        MockHttpServletResponse resp = getAsServletResponse(path);
        InputStream is = getBinaryInputStream(resp);
        return ImageIO.read(is);
    }

}
