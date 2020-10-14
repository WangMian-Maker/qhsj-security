package com.example.demo.entity.events;

import com.bedatadriven.jackson.datatype.jts.parsers.GeometryParser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.security.core.parameters.P;

import java.io.IOException;

public class GeometryDeserializer<T extends Geometry> extends JsonDeserializer<Point> {


    @Override
    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode root = (JsonNode)oc.readTree(jsonParser);
        System.out.println("root: "+root);
        Coordinate coordinate=null;
        try{
            coordinate=new Coordinate(Double.parseDouble(root.get("coordinate").get("x").toString()),
                    Double.parseDouble(root.get("coordinate").get("y").toString()),
                    Double.parseDouble(root.get("coordinate").get("z").toString()));
        }
        catch (Exception e){

        }
        Point point = new Point(coordinate, new PrecisionModel(),4214);
        return point;
    }
}
