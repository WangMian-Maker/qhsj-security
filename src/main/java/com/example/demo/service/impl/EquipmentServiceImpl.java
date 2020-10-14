package com.example.demo.service.impl;

import com.example.demo.entity.Equipment;
import com.example.demo.entity.params.Page;
import com.example.demo.repository.EquipmentRepository;
import com.example.demo.service.EquipmentService;
import com.sun.istack.NotNull;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Override
    public void save(Equipment equipment) {
        //JSONObject jsonObject=JSONObject.fromObject(equipment);
        //System.out.println(jsonObject.toString()+" :equipment");
//        Point point=null;
//        if(jsonObject.getJSONObject("point")!=null){
//            JSONObject coordinateObject=jsonObject.getJSONObject("point").getJSONObject("coordinate");
//            Coordinate coordinate=new Coordinate(coordinateObject.getDouble("x"),coordinateObject.getDouble("y"),coordinateObject.getDouble("z"));
//            PrecisionModel precisionModel=new PrecisionModel();
//            //com.example.demo.entity.events.Point point=new Point(coordinate,precisionModel,4214);
//            point=new Point(coordinate,precisionModel,4214);
//        }
//
//        jsonObject.set("point",null);
//        Equipment equipment=(Equipment) JSONObject.toBean(jsonObject,Equipment.class);
//        equipment.setPoint(point);
        Long id=1L;
        if(equipmentRepository.findMaxId()!=null){
            id=equipmentRepository.findMaxId()+1;
        }
        equipment.setId(id);
        try{
            equipment.getPoint().setSRID(4214);
        }catch (Exception e){

        }
        equipmentRepository.save(equipment.getId(),equipment.getName()
                ,equipment.getPoint()
                ,equipment.getStatus()
                ,equipment.getIndex()
                ,equipment.getChargePerson().getStaffId(),
                equipment.getPhoneNumber(),equipment.getType());
    }

    @Override
    public void deleteById(Long id) {
        equipmentRepository.deleteById(id);
    }

    @Override
    public void update(Equipment equipment) {

//        JSONObject jsonObject=new JSONObject(equipmentJson);
//        Point point=null;
//        if(jsonObject.getJSONObject("point")!=null){
//            JSONObject coordinateObject=jsonObject.getJSONObject("point").getJSONObject("coordinate");
//            Coordinate coordinate=new Coordinate(coordinateObject.getDouble("x"),coordinateObject.getDouble("y"),coordinateObject.getDouble("z"));
//            PrecisionModel precisionModel=new PrecisionModel();
//            //com.example.demo.entity.events.Point point=new Point(coordinate,precisionModel,4214);
//            point= new Point(coordinate,precisionModel,4214);
//        }
//
//        jsonObject.set("point",null);
//        Equipment equipment=(Equipment) JSONObject.toBean(jsonObject,Equipment.class);
        //equipment.setPoint(point);
        equipmentRepository.save(equipment);
    }

    @Override
    public Page<Equipment> findPage(@NotNull String status, @NotNull String type, int pageNum, int pageSize) {

        status=status==null?"%%":"%"+status+"%";
        type=type==null?"%%":"%"+type+"%";
        int startPoint=pageNum*pageSize-pageSize;
        Page<Equipment> equipmentPage=new Page<>();
        equipmentPage.setPageNum(pageNum);
        equipmentPage.setPageSize(pageSize);
        //System.out.println("sql: select * from equipment where status like " + status+"and type like "+type+" order by id desc limit "+pageSize+" offset "+startPoint);
        equipmentPage.setContent(equipmentRepository.findPage(status,type,pageSize,startPoint));
        equipmentPage.setTotalElements(equipmentRepository.findAll()==null ?0:equipmentRepository.findAll().size());
        equipmentPage.setTotalPages((int)Math.ceil((float)equipmentPage.getTotalElements()/(float)equipmentPage.getPageSize()));
        return equipmentPage;
    }
}
