package com.example.demo.entity.events;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import java.io.IOException;
import java.util.Arrays;

public class GeometrySerializer extends JsonSerializer<Geometry> {
    public GeometrySerializer() {
    }

    public void serialize(Geometry value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        this.writeGeometry(jgen, value);
    }

    public void writeGeometry(JsonGenerator jgen, Geometry value) throws IOException {
        if (value instanceof Polygon) {
            this.writePolygon(jgen, (Polygon)value);
        } else if (value instanceof Point) {
            this.writePoint(jgen, (Point)value);
        } else if (value instanceof MultiPoint) {
            this.writeMultiPoint(jgen, (MultiPoint)value);
        } else if (value instanceof MultiPolygon) {
            this.writeMultiPolygon(jgen, (MultiPolygon)value);
        } else if (value instanceof LineString) {
            this.writeLineString(jgen, (LineString)value);
        } else if (value instanceof MultiLineString) {
            this.writeMultiLineString(jgen, (MultiLineString)value);
        } else {
            if (!(value instanceof GeometryCollection)) {
                throw new JsonMappingException("Geometry type " + value.getClass().getName() + " cannot be serialized as GeoJSON." + "Supported types are: " + Arrays.asList(Point.class.getName(), LineString.class.getName(), Polygon.class.getName(), MultiPoint.class.getName(), MultiLineString.class.getName(), MultiPolygon.class.getName(), GeometryCollection.class.getName()));
            }

            this.writeGeometryCollection(jgen, (GeometryCollection)value);
        }

    }

    private void writeGeometryCollection(JsonGenerator jgen, GeometryCollection value) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "GeometryCollection");
        jgen.writeArrayFieldStart("geometries");

        for(int i = 0; i != value.getNumGeometries(); ++i) {
            this.writeGeometry(jgen, value.getGeometryN(i));
        }

        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    private void writeMultiPoint(JsonGenerator jgen, MultiPoint value) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "MultiPoint");
        jgen.writeArrayFieldStart("coordinate");

        for(int i = 0; i != value.getNumGeometries(); ++i) {
            this.writePointCoords(jgen, (Point)value.getGeometryN(i));
        }

        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    private void writeMultiLineString(JsonGenerator jgen, MultiLineString value) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "MultiLineString");
        jgen.writeArrayFieldStart("coordinate");

        for(int i = 0; i != value.getNumGeometries(); ++i) {
            this.writeLineStringCoords(jgen, (LineString)value.getGeometryN(i));
        }

        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    public Class<Geometry> handledType() {
        return Geometry.class;
    }

    private void writeMultiPolygon(JsonGenerator jgen, MultiPolygon value) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "MultiPolygon");
        jgen.writeArrayFieldStart("coordinate");

        for(int i = 0; i != value.getNumGeometries(); ++i) {
            this.writePolygoncoordinate(jgen, (Polygon)value.getGeometryN(i));
        }

        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    private void writePolygon(JsonGenerator jgen, Polygon value) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "Polygon");
        jgen.writeFieldName("coordinate");
        this.writePolygoncoordinate(jgen, value);
        jgen.writeEndObject();
    }

    private void writePolygoncoordinate(JsonGenerator jgen, Polygon value) throws IOException {
        jgen.writeStartArray();
        this.writeLineStringCoords(jgen, value.getExteriorRing());

        for(int i = 0; i < value.getNumInteriorRing(); ++i) {
            this.writeLineStringCoords(jgen, value.getInteriorRingN(i));
        }

        jgen.writeEndArray();
    }

    private void writeLineStringCoords(JsonGenerator jgen, LineString ring) throws IOException {
        jgen.writeStartArray();

        for(int i = 0; i != ring.getNumPoints(); ++i) {
            Point p = ring.getPointN(i);
            this.writePointCoords(jgen, p);
        }

        jgen.writeEndArray();
    }

    private void writeLineString(JsonGenerator jgen, LineString lineString) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "LineString");
        jgen.writeFieldName("coordinate");
        this.writeLineStringCoords(jgen, lineString);
        jgen.writeEndObject();
    }

    private void writePoint(JsonGenerator jgen, Point p) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "Point");
        jgen.writeFieldName("coordinate");
        this.writePointCoords(jgen, p);
        jgen.writeEndObject();
    }

    private void writePointCoords(JsonGenerator jgen, Point p) throws IOException {
        jgen.writeStartArray();
        jgen.writeNumber(p.getX());
        jgen.writeNumber(p.getY());
        if (p.getCoordinate() == null) {
            throw new IllegalStateException("getZ called on empty Point");
        } else {
            jgen.writeNumber(p.getCoordinate().z);
        }
        jgen.writeEndArray();
    }
}
