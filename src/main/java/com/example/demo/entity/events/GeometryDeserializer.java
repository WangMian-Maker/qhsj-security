package com.example.demo.entity.events;

import com.example.demo.config.log.Log;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeometryDeserializer<T extends Geometry> extends JsonDeserializer<Geometry> {


    @Override
    public Geometry deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode root = (JsonNode)oc.readTree(jsonParser);
        //System.out.println("root: "+root);
        Coordinate coordinate=null;
        JsonNode node=root.get("coordinates");
        //System.out.println("node: "+node.size());
        if(node.isArray()){
            List<Coordinate> coordinates=new ArrayList<>();
            try{
                for(JsonNode jsonNode :node){

                    coordinates.add(new Coordinate(Double.parseDouble(jsonNode.get("x").toString()),
                            Double.parseDouble(jsonNode.get("y").toString()),
                            Double.parseDouble(jsonNode.get("z").toString())));
                }
            }
            catch (Exception e){

            }
            //System.out.println(coordinates.size()+":size");
            Coordinate[] coordinates1=new Coordinate[coordinates.size()];
            LineString lineString=new LineString(coordinates.toArray(coordinates1),new PrecisionModel(),4214);
            System.out.println("node: "+lineString.getCoordinates().length);
            return lineString;
        }
        else {
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

//    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//        ObjectCodec oc = jsonParser.getCodec();
//        JsonNode root = (JsonNode)oc.readTree(jsonParser);
//        return this.geometryParser.geometryFromJson(root);
//    }
}
