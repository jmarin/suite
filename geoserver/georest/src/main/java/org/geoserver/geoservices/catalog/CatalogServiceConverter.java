package org.geoserver.geoservices.catalog;

import java.io.Writer;

import org.geoserver.geoservices.services.CatalogService;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonWriter;


// TODO: REMOVE THIS CLASS, NOT NECESSARY. LEAVING FOR REFERENCE FOR NOW

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public class CatalogServiceConverter implements Converter {

    public boolean canConvert(Class clazz) {
        return clazz.equals(CatalogService.class);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        CatalogService catalogService = (CatalogService) value;
        System.out.println(catalogService.toString());
        //JsonWriter jsonWriter = new JsonWriter((Writer) writer);
                      
        //JsonWriter jsonWriter = new JsonWriter((Writer) writer, JsonWriter.DROP_ROOT_MODE);
        //jsonWriter.addAttribute("specVersion", "1.0");
//        writer.addAttribute("specVersion", "1.0");
//        writer.addAttribute("currentVersion", "2.4.4");
//        writer.startNode("folders");
//        
//        writer.endNode();
//        

             
        
        

    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        CatalogService catalogService = new CatalogService(null, null, null, null, null, null);
        reader.moveDown();
        //catalogService.setSpecVersion(reader.getValue());
        reader.moveUp();
        return catalogService;
    }

}
