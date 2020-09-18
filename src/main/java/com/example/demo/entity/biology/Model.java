package com.example.demo.entity.biology;

import lombok.Data;

import java.util.List;

@Data
public class Model {
    public String name;
    public Integer value;
    public Color itemStyle;
    public List<Model> children;
}
