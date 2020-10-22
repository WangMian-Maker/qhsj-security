package com.example.demo.service.impl;

import com.example.demo.NonStaticResourceHttpRequestHandler;
import com.example.demo.config.utils.JwtTokenUtils;
import com.example.demo.entity.Department;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.Task;
import com.example.demo.entity.events.Event;
import com.example.demo.entity.params.Page;
import com.example.demo.entity.user.SysUser;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.StaffInforRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.user.SysUserRepository;
import com.example.demo.service.EventService;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.Even;
import org.apache.poi.util.IOUtils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


//private Long eid;
//private String eventIndex;
//private String eventGrade;
//private String department;
//private String position;
//private String chargePerson;
//private String photoPath;
//private String videoPath;
//private String status;
//private String eventSource;
//private String findTime;
//private String information;
//private String findPerson;
//private String dealPerson;
//private String dealTime;
//private String dealResult;
//private String operationPerson;
//private String blackList;
//private String influence;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private StaffInforRepository staffInforRepository;
    public EventServiceImpl(NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler) {
        this.nonStaticResourceHttpRequestHandler = nonStaticResourceHttpRequestHandler;
    }

    boolean contain(Iterator<String> keys, String key){
        while (keys.hasNext()){
            if(keys.next().equals(key)) return true;

        }
        return false;
    }

    @Override
    public Long uploadEvent(String eventStr, List<MultipartFile> photoFiles,List<MultipartFile> videoFiles) {
        System.out.println(eventStr);
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
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/"+event.getEid()+"/";
        if(photoFiles!=null&&photoFiles.size()>0){
            String photoPathRoot=rootPath+"photo/";
            event.setPhotoPath(photoPathRoot);
            dealStream(photoFiles,photoPathRoot,".jpg",0);
        }
        if(videoFiles!=null&&videoFiles.size()>0){
            String videoPathRoot=rootPath+"video/";
            event.setVideoPath(videoPathRoot);
            dealStream(videoFiles,videoPathRoot,".mp4",0);
        }
        Long eid=-1l;
        if(eventRepository.maxId()==null){
            eid=1l;
        }else {
            eid=eventRepository.maxId()+1;
        }
        event.setEid(eid);
        if(point!=null){
            event.setPoint(point);
        }
        event.setTime(System.currentTimeMillis());
        eventRepository.save(event.getEid(),event.getTime(),event.getEventIndex(),event.getEventType(),event.getEventGrade(),
                event.getPhotoPath(),event.getVideoPath(),event.getStatus(),event.getEventSource(),event.getFindTime(),event.getInformation()
                ,event.getDealTime(),event.getDealResult(),event.getBlackList(),event.getInfluence());
        //eventRepository.save(event);
        if(event.getDepartment()!=null&&event.getDepartment().getDid()!=null){
            eventRepository.updateDepartment(event.getDepartment().getDid(),event.getEid());
        }
        if(event.getChargePerson()!=null&&event.getChargePerson().getStaffId()!=null){
            eventRepository.updateChargePerson(event.getChargePerson().getStaffId(),event.getEid());
        }
        if(event.getTask()!=null&&event.getTask().getTid()!=null){
            eventRepository.updateTask(event.getTask().getTid(),event.getEid());
        }
        if(event.getDealPerson()!=null&&event.getDealPerson().getStaffId()!=null){
            eventRepository.updateDealPerson(event.getDealPerson().getStaffId(),event.getEid());
        }
        if(event.getFindPerson()!=null&&event.getFindPerson().getStaffId()!=null){
            eventRepository.updateFindPerson(event.getFindPerson().getStaffId(),event.getEid());
        }
        if(event.getOperationPerson()!=null&&event.getOperationPerson().getStaffId()!=null){
            eventRepository.updateOperationPerson(event.getOperationPerson().getStaffId(),event.getEid());
        }
        if(event.getPoint()!=null){
            eventRepository.updatePoint(event.getPoint(),event.getEid());
        }
        //eventRepository.save(event);
        return eid;
    }

    @Override
    public List<String> findPhotoFilePathById(Long id) {
        List<String> photoPaths=new ArrayList<>();
        Event event=eventRepository.findByeid(id);
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/"+event.getEid()+"/";
        String photoPathRoot=rootPath+"photo/";
        //String videoPathRoot=rootPath+"video/";
        File file=new File(photoPathRoot);
        File[] files= file.listFiles();
        if(files==null){
            //photoPaths.add("no photo files");
            return new ArrayList<String>();
        }
        for (int i=0;i< files.length;i++){
            photoPaths.add("/dataImage/"+event.getEid()+"/photo/"+files[i].getName());
        }
        return  photoPaths;
    }

    @Override
    public List<String> findVideoFilePathById(Long id) {
        List<String> videoPaths=new ArrayList<>();
        Event event=eventRepository.findByeid(id);
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/"+event.getEid()+"/";
        String videoPathRoot=rootPath+"video/";
        File file=new File(videoPathRoot);
        File[] files=file.listFiles();
        if(files==null){
            videoPaths.add("no video files");
            return videoPaths;
        }
        for(int i=0;i< files.length;i++){
            //System.out.println("path"+files[i].getName());
            if(!files[i].isDirectory()){
                String [] tmp=files[i].getName().split("\\.");
                //System.out.println("path"+tmp.length);
                String name=tmp[0];
                videoPaths.add("/dataImage/"+event.getEid()+"/video/image/"+name+".jpg");
            }


        }
        return videoPaths;
    }

    @Override
    public Page<Event> findPage(int pageNum, int pageSize) {
        int startPoint=pageNum*pageSize-pageSize;
        List<Event> events=eventRepository.findPage(pageSize,startPoint);
        com.example.demo.entity.params.Page<Event> eventPage=new com.example.demo.entity.params.Page<>();
        eventPage.setTotalElements(eventRepository.findAll().size());
        eventPage.setPageNum(pageNum);
        eventPage.setPageSize(pageSize);
        eventPage.setContent(events);
        eventPage.setTotalPages((int)Math.ceil((float)eventPage.getTotalElements()/(float) eventPage.getPageSize()));
        //PageRequest request=PageRequest.of(pageNum-1,pageSize);
        //System.out.println(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/7/photo/").listFiles().length+"lengthlength");
        for(Event event:events){
            String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/"+event.getEid()+"/";
            String realPath=rootPath+"photo/";

            File photo =new File(realPath);
            if(photo.listFiles()==null||photo.listFiles().length==0){
                event.setPhotoPath(null);
                eventRepository.save(event);
            }
            realPath=rootPath+"video/";
            photo =new File(realPath);
            if(photo.listFiles()==null||photo.listFiles().length==0){
                event.setVideoPath(null);
                eventRepository.save(event);
            }
        }
        return eventPage;
    }

    @Override
    public void delete(Long id) throws IOException {
        Event event=eventRepository.findByeid(id);
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/"+event.getEid();
        eventRepository.deleteById(id);
        File file=new File(rootPath);
        deleteDir(file);
    }

    @Override
    public void myDeleteFile(String fileName) throws IOException {
        //System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static"+fileName);
        File file=new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static"+fileName);
        InputStream inputStream=new FileInputStream(file);
        if(inputStream!=null){
            inputStream.close();
        }
        file.delete();
    }

    @Override
    public void videoPlay(Long id, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Event event=eventRepository.findByeid(id);
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/"+event.getEid()+"/";
        String realPath=rootPath+"video/"+fileName;
        //System.out.println(realPath);
        try {
            FileInputStream inputStream = new FileInputStream(realPath);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            // diskFilename = "final.mp4";
            response.setContentType("video/mp4");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            //System.out.println("data.length " + data.length);
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            OutputStream os = response.getOutputStream();
            os.write(data);
            //先声明的流后关掉！
            os.flush();
            os.close();
            inputStream.close();
        } catch (Exception e) {

        }
//        Path filePath = Paths.get(realPath );
//        if (Files.exists(filePath)) {
//            String mimeType = Files.probeContentType(filePath);
//            if (!StringUtils.isEmpty(mimeType)) {
//                response.setContentType(mimeType);
//            }
//            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
//            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
//        } else {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//        }
    }

    @Override
    public void addPhotoForEvent(Long id, List<MultipartFile> photoFiles) {
        Event event=eventRepository.findByeid(id);
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/"+event.getEid()+"/";
        String realPath=rootPath+"photo/";
        File photo =new File(realPath);
        int origin=-1;
        if(photo.listFiles()==null||photo.listFiles().length==0){
            origin=0;
        }
        else {
            origin=photo.listFiles().length;
        }
        event.setPhotoPath(realPath);
        eventRepository.save(event);
        dealStream(photoFiles,realPath,".jpg",origin);
    }

    @Override
    public void addVideoForEvent(Long id, List<MultipartFile> photoFiles) {
        Event event=eventRepository.findByeid(id);
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/"+event.getEid()+"/";
        String realPath=rootPath+"video/";

        File photo =new File(realPath);
        int origin=-1;
        if(photo.listFiles()==null||photo.listFiles().length==0){
            origin=0;
        }
        else {
            origin=photo.listFiles().length;
        }
        event.setVideoPath(realPath);
        eventRepository.save(event);
        dealStream(photoFiles,realPath,".mp4",origin);
    }

    @Override
    public Boolean update(Event event) {
//        JSONObject jsonObject=new JSONObject(eventStr);
//        Point point=null;
//        if(jsonObject.get("point")!=null){
//            if(contain(jsonObject.getJSONObject("point").keys(),"coordinate")){
//                JSONObject coordinateObject=jsonObject.getJSONObject("point").getJSONObject("coordinate");
//                Coordinate coordinate=new Coordinate(coordinateObject.getDouble("x"),coordinateObject.getDouble("y"),coordinateObject.getDouble("z"));
//                PrecisionModel precisionModel=new PrecisionModel();
//                //com.example.demo.entity.events.Point point=new Point(coordinate,precisionModel,4214);
//                point=new Point(coordinate,precisionModel,4214);
//            }
//        }
//        jsonObject.set("point",null);
//        Event event=(Event)JSONObject.toBean(jsonObject,Event.class);
//        event.setPoint(point);
        if(eventRepository.findByeid(event.getEid())==null){
            return false;
        }
        eventRepository.save(event);
        return true;
    }

    @Override
    public String uploadEventToTask(Long taskId, String eventStr, List<MultipartFile> photoFiles, List<MultipartFile> videoFiles) {
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
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/dataImage/"+event.getEid()+"/";
        String photoPathRoot=rootPath+"photo/";
        String videoPathRoot=rootPath+"video/";
        event.setPhotoPath(photoPathRoot);
        event.setVideoPath(videoPathRoot);
        dealStream(photoFiles,photoPathRoot,".jpg",0);
        dealStream(videoFiles,videoPathRoot,".mp4",0);
        event.setEid(eventRepository.maxId());
        Long eid=-1l;
        if(eventRepository.maxId()==null){
            eid=1l;
        }else {
            eid=eventRepository.maxId()+1;
        }
        if(taskRepository.findBytid(taskId)==null){
            return "fail";
        }
//        eventRepository.save(eid,event.getEventIndex(),event.getEventType(),event.getEventGrade(),event.getDepartment().getDid(),event.getPosition(),event.getChargePerson().getStaffId(),
//                event.getPhotoPath(),event.getVideoPath(),event.getStatus(),event.getEventSource(),event.getFindTime(),event.getInformation(),event.getFindPerson().getStaffId(),
//                event.getDealPerson().getStaffId(),event.getDealTime(),event.getDealResult(),event.getOperationPerson().getStaffId(),event.getBlackList(),event.getInfluence(),taskId);
        return "success";
    }

    @Override
    public List<Event> findByTaskAndStatus(Long tid, String status) {
        status="%"+status+"%";
        return eventRepository.findByTaskAndStatus(tid,status);
    }

    @Override
    public List<Event> findByFindPersonAndStatus( String status) {
        status="%"+status+"%";
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        return eventRepository.findByFindPersonAndStatus(staffInfor.getStaffId(),status);
    }

    @Override
    public List<Integer> findCountInTask(Long tid) {
        List<Integer> counts=new ArrayList<>();
        counts.add(eventRepository.findByTaskAndStatus(tid,"待处理").size());
        counts.add(eventRepository.findByTaskAndStatus(tid,"已处理").size());
        return counts;
    }

    @Override
    public List<Integer> findCountInFindPersonByToken() {
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        List<Integer> counts=new ArrayList<>();
        counts.add(eventRepository.findByFindPersonAndStatus(staffInfor.getStaffId(),"待处理").size());
        counts.add(eventRepository.findByTaskAndStatus(staffInfor.getStaffId(),"已处理").size());
        return counts;
    }

    @Override
    public void setDepartment(Department department, Long eid) {
        Event event=eventRepository.findByeid(eid);
        event.setDepartment(department);
        eventRepository.save(event);
    }

    @Override
    public void setChargePerson(StaffInfor staffInfor, Long eid) {
        Event event=eventRepository.findByeid(eid);
        event.setChargePerson(staffInfor);
        eventRepository.save(event);
    }

    @Override
    public void setFindPerson(StaffInfor staffInfor, Long eid) {
        Event event=eventRepository.findByeid(eid);
        event.setFindPerson(staffInfor);
        eventRepository.save(event);
    }

    @Override
    public void setDealPerson(StaffInfor staffInfor, Long eid) {
        Event event=eventRepository.findByeid(eid);
        event.setDealPerson(staffInfor);
        eventRepository.save(event);
    }

    @Override
    public void setOperationPerson(StaffInfor staffInfor, Long eid) {
        Event event=eventRepository.findByeid(eid);
        event.setOperationPerson(staffInfor);
        eventRepository.save(event);
    }

    @Override
    public void setTask(Task task, Long eid) {
        Event event=eventRepository.findByeid(eid);
        event.setTask(task);
        eventRepository.save(event);
    }

    @Override
    public void setDepartment(Long did, Long eid) {
        Event event=eventRepository.findByeid(eid);
        Department department=departmentRepository.findBydid(did);
        event.setDepartment(department);
        eventRepository.save(event);
    }

    @Override
    public void setChargePerson(Long staffId, Long eid) {
        Event event=eventRepository.findByeid(eid);
        StaffInfor staffInfor=staffInforRepository.findBystaffId(staffId);
        event.setChargePerson(staffInfor);
        eventRepository.save(event);
    }

    @Override
    public void setFindPerson(Long staffId, Long eid) {
        Event event=eventRepository.findByeid(eid);
        StaffInfor staffInfor=staffInforRepository.findBystaffId(staffId);
        event.setFindPerson(staffInfor);
        eventRepository.save(event);
    }

    @Override
    public void setDealPerson(Long staffId, Long eid) {
        Event event=eventRepository.findByeid(eid);
        StaffInfor staffInfor=staffInforRepository.findBystaffId(staffId);
        event.setDealPerson(staffInfor);
        eventRepository.save(event);
    }

    @Override
    public void setOperationPerson(Long staffId, Long eid) {
        Event event=eventRepository.findByeid(eid);
        StaffInfor staffInfor=staffInforRepository.findBystaffId(staffId);
        event.setOperationPerson(staffInfor);
        eventRepository.save(event);
    }

    @Override
    public void setTask(Long tid, Long eid) {
        Event event=eventRepository.findByeid(eid);
        Task task=taskRepository.findBytid(tid);
        event.setTask(task);
        eventRepository.save(event);
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findByeid(id);
    }

    boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir
                        (new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        if(dir.delete()) {
            System.out.println("目录已被删除！");
            return true;
        } else {
            System.out.println("目录删除失败！");
            return false;
        }
    }
    void dealStream(List<MultipartFile> files,String fileRoot,String type,int origin){

        for(int i=0;i<files.size();i++){
            dealFile(files.get(i),fileRoot,(i+origin)+type);
        }
    }

    void dealFile(MultipartFile file, String fileRoot,String fileName){
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        String photoFilePath=fileRoot+fileName;
        File outFile=new File(photoFilePath);
        if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
            // 创建父文件夹
            outFile.getParentFile().mkdirs();
        }
        if (outFile.getParentFile().getParentFile() != null || !outFile.getParentFile().getParentFile().isDirectory()) {
            // 创建父文件夹
            outFile.getParentFile().getParentFile().mkdirs();
        }
        try {
            fileOutputStream=new FileOutputStream(outFile);
            inputStream=file.getInputStream();
            IOUtils.copy(inputStream,fileOutputStream);
            //System.out.println(photoFilePath);
            if(photoFilePath.endsWith(".mp4")){
                String [] tmp=fileName.split("\\.");
                String name=tmp[0];
                String imgName=fileRoot+"image/"+name+".jpg";
                File tmoFile=new File(imgName);
                if (tmoFile.getParentFile() != null || !tmoFile.getParentFile().isDirectory()) {
                    // 创建父文件夹
                    tmoFile.getParentFile().mkdirs();
                }
                fetchFrame(photoFilePath,imgName);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void fetchFrame(String videofile, String framefile)
            throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(framefile);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < lenght) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabFrame();
            if ((i > 5) && (f.image != null)) {
                break;
            }
            i++;
        }
        opencv_core.IplImage img = f.image;
        int owidth = img.width();
        int oheight = img.height();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        //ff.flush();
        ff.stop();
        System.out.println(System.currentTimeMillis() - start);
    }

}
