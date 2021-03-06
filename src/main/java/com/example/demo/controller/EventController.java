package com.example.demo.controller;

import com.example.demo.NonStaticResourceHttpRequestHandler;
import com.example.demo.entity.Task;
import com.example.demo.entity.events.Event;
import com.example.demo.entity.params.Page;
import com.example.demo.entity.params.TaskParam1;
import com.example.demo.service.impl.EventServiceImpl;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("data/event")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;

    @PostMapping("/uploadEvent")
    public Long upload(@RequestPart("event") String event, @RequestPart("photoFile") List<MultipartFile> photoFiles, @RequestPart("videoFile") List<MultipartFile> videoFiles){
        return eventService.uploadEvent(event,photoFiles,videoFiles);
    }

    @PostMapping("/findPhotoPathsById/{id}")
    public List<String> findPhotoPathsById(@PathVariable("id") Long id){
        return eventService.findPhotoFilePathById(id);
    }
    @PostMapping("/findVideoPathsById/{id}")
    public List<String> findVideoPathsById(@PathVariable("id") Long id){
        return eventService.findVideoFilePathById(id);
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Page<Event> findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return eventService.findPage(pageNum,pageSize);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id) throws IOException {
        eventService.delete(id);
        return "success";
    }

    @PostMapping("/deleteFiles")
    public String deleteFiles(@RequestBody String url) throws IOException {
        System.out.println(url+"url");
        JSONObject object=JSONObject.fromObject(url);
        eventService.myDeleteFile(object.getString("url"));
        return "success";
    }

    @PostMapping("/addPhotoToEvent")
    public String addPhotoToEvent(@RequestPart("id") String id,@RequestPart("photoFile") List<MultipartFile> photoFiles){
        Long tmpId=Long.valueOf(id);
        System.out.println(photoFiles.size()+"啥毛病");
        eventService.addPhotoForEvent(tmpId,photoFiles);
        return "success";
    }
    @PostMapping("/addVideoToEvent")
    public String addVideoToEvent(@RequestPart("id") String id,@RequestPart("videoFile") List<MultipartFile> videoFiles){
        Long tmpId=Long.valueOf(id);
        eventService.addVideoForEvent(tmpId,videoFiles);
        return "success";
    }

    @PutMapping("/update")
    public String update(@RequestBody Event event){
        //System.out.println( eventStr);
        if(eventService.update(event)){
            return "success";
        }
        return "fail";
    }
    @PostMapping("/play")
    public String play(@RequestBody String imgPath){
        JSONObject object=JSONObject.fromObject(imgPath);
        String [] tmp=object.getString("imgPath").split("/");
        String name=tmp[tmp.length-1];
        name=name.split("\\.")[0]+".mp4";
        String root="";
        for(int i=1;i<tmp.length-2;i++){
            root=root+"/"+tmp[i];
        }
        return root+"/"+name;
    }

    @PostMapping("/uploadEventToTask/{tid}")
    public String uploadEventToTask(@PathVariable("tid") Long tid, @RequestPart("event") String event, @RequestPart("photoFile") List<MultipartFile> photoFiles, @RequestPart("videoFile") List<MultipartFile> videoFiles){
        return eventService.uploadEventToTask(tid,event,photoFiles,videoFiles);
    }


    @PostMapping("/findByTaskAndStatus/{tid}")
    public List<Event> findByTaskAndStatus(@PathVariable("tid") Long tid,@RequestBody String status){
        return eventService.findByTaskAndStatus(tid,status);
    }
    @PostMapping("/findByFindPersonAndStatus")
    public List<Event> findByFindPersonAndStatus(@RequestBody String status){
        return eventService.findByFindPersonAndStatus(status);
    }
    @PostMapping("/findCountInTask/{tid}")
    public List<Integer> findCountInTask(@PathVariable("tid") Long tid){
        return eventService.findCountInTask(tid);
    }
    @PostMapping("/findCountInFindPersonByToken")
    public List<Integer> findCountInFindPersonByToken(){
        return eventService.findCountInFindPersonByToken();
    }

    @PostMapping("/setDepartment/{did}/{eid}")
    public String setDepartment(@PathVariable("did")Long did,@PathVariable("eid")Long eid){
        eventService.setDepartment(did,eid);
        return "success";
    }

    @PostMapping("/setChargePerson/{staffId}/{eid}")
    public String setChargePerson(@PathVariable("staffId")Long staffId,@PathVariable("eid")Long eid){
        eventService.setChargePerson(staffId,eid);
        return "success";
    }

    @PostMapping("/setFindPerson/{staffId}/{eid}")
    public String setFindPerson(@PathVariable("staffId")Long staffId,@PathVariable("eid")Long eid){
        eventService.setFindPerson(staffId,eid);
        return "success";
    }

    @PostMapping("/setDealPerson/{staffId}/{eid}")
    public String setDealPerson(@PathVariable("staffId")Long staffId,@PathVariable("eid")Long eid){
        eventService.setDealPerson(staffId,eid);
        return "success";
    }

    @PostMapping("/setOperationPerson/{staffId}/{eid}")
    public String setOperationPerson(@PathVariable("staffId")Long staffId,@PathVariable("eid")Long eid){
        eventService.setOperationPerson(staffId,eid);
        return "success";
    }

    @PostMapping("/setTask/{tid}/{eid}")
    public String setTask(@PathVariable("tid")Long tid,@PathVariable("eid")Long eid){
        eventService.setTask(tid,eid);
        return "success";
    }

    @PostMapping("/findById/{id}")
    public Event findById(@PathVariable("id") Long id){
        return eventService.findById(id);
    }

    @PostMapping("/findAllEventNoTask")
    public List<Event> findAllEventNoTask(){
        return eventService.findAllEventNoTask();
    }

    @PostMapping("/findFirstPageNoTask")
    public Page<Event> findFirstPageNoTask(Long tid, String status){
        return eventService.findFirstPageNoTask(status);
    }

    @PostMapping("/findNewPageNoTask")
    public List<Event> findNewPageNoTask(Long tid,Long currentTime,String status){
        return eventService.findNewPageNoTask(currentTime,status);
    }

    @PostMapping("/findOldPageNoTask")
    public List<Event> findOldPageNoTask(Long tid,Long lastTime,String status,int size){
        return eventService.findOldPageNoTask(lastTime,status,size);
    }

    @PutMapping("/updateWithMedia")
    public String updateWithMedia(@RequestPart("event") String eventStr,@RequestPart("photoFile")List<MultipartFile> photoFiles,
                                  @RequestPart("videoFile")List<MultipartFile> videoFiles){
        JSONObject jsonObject=new JSONObject(eventStr);
        Point point=null;
        try{
            JSONObject coordinateObject=jsonObject.getJSONObject("point").getJSONObject("coordinate");
            Coordinate coordinate=new Coordinate(coordinateObject.getDouble("x"),coordinateObject.getDouble("y"),coordinateObject.getDouble("z"));
            PrecisionModel precisionModel=new PrecisionModel();
            //com.example.demo.entity.events.Point point=new Point(coordinate,precisionModel,4214);
            point=new Point(coordinate,precisionModel,4214);
        }
        catch (Exception e){

        }
        //jsonObject.set("point",JSONObject.fromBean(point));
        jsonObject.remove("point");
        Event event= (Event) JSONObject.toBean(jsonObject,Event.class);
        event.setPoint(point);
        eventService.update(event);
        eventService.addPhotoForEvent(event.getEid(),photoFiles);
        eventService.addVideoForEvent(event.getEid(),videoFiles);
        return "success";
    }
}
