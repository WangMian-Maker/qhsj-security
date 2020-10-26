package com.example.demo.service.impl;

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
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StaffInforRepository staffInforRepository;
    @Override
    public void createTask(Task task) {
        if(taskRepository.maxId()==null){
            task.setTid(1l);
        }
        else {
            task.setTid(taskRepository.maxId()+1);
        }
        task.setTime(System.currentTimeMillis());
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        System.out.println(staffInfor.getStaffName()+"=====================================================");
        task.setCreator(staffInfor);
        task.setStatus("待处理");
        taskRepository.save(task.getTid(),task.getCreateTime(),task.getWeather(),task.getRecode(),task.getDealSuggest(),
                task.getDealResult(),task.getStatus(),task.getTime(),task.getOriginLine(),task.getRealLine());
        List<Long> leaderIds=new ArrayList<>();
        List<Long> workerIds=new ArrayList<>();
        List<StaffInfor> leaders=task.getLeaders();
        List<StaffInfor> workers=task.getStaffWorkers();
        if(leaders!=null){
            for(StaffInfor staffInfor1:leaders){
                leaderIds.add(staffInfor1.getStaffId());
            }
        }

        if(workers!=null){
            for(StaffInfor staffInfor1:workers){
                workerIds.add(staffInfor1.getStaffId());
            }
        }
        if(task.getDepartment()!=null){
            setDepartment(task.getTid(),task.getDepartment().getDid());
        }
        if(task.getCreator()!=null){
            setCreator(task.getTid(),task.getCreator().getStaffId());
        }
        setLeaders(task.getTid(),leaderIds);
        setStaffs(task.getTid(),workerIds);
        //taskRepository.save(task);
    }

    @Override
    public Boolean update(Task task) {
        if(taskRepository.findById(task.getTid())==null){
            return false;
        }
        taskRepository.save(task);
        return true;
    }

    @Override
    public String deleteById(Long id) {
        if(findAllEvents(id)!=null&&findAllEvents(id).size()>0){
            return "请先清空该任务的绑定事件";
        }
        taskRepository.deleteById(id);
        return "success";
    }

    @Override
    public Page<Task> findPage(int pageNum, int pageSize) {
        Page<Task> page=new Page<>();
        page.setContent(taskRepository.findPage(pageSize,pageNum*pageSize-pageSize));
        page.setTotalElements(taskRepository.findCount());
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return page;
    }



    @Override
    public Task findById(Long tid) {
        return taskRepository.findBytid(tid);
    }

    @Override
    public List<Event> findAllEvents(Long tid) {
        return eventRepository.findEventInTask(tid);
    }

    @Override
    public Page<Event> findPageEventByStatus(String status, Long tid,int pageNum,int pageSize) {
        int startPoint=pageNum*pageSize-pageSize;
        status=status==null?"%%": "%"+status+"%";
        Page<Event> eventPage=new Page<>();
        eventPage.setContent(eventRepository.findPageEventInTaskByStatus(tid,startPoint,pageSize,status));
        eventPage.setPageSize(pageSize);
        eventPage.setPageNum(pageNum);
        eventPage.setTotalElements(findAllEvents(tid)==null?0:findAllEvents(tid).size());
        eventPage.setTotalPages((int)Math.ceil((float)eventPage.getTotalElements()/(float)eventPage.getPageSize()));
        return eventPage;
    }

    @Override
    public Page<Event> findFirstPage(String status, Long tid) {
        int pageNum=1;int pageSize=10;
        int startPoint=pageNum*pageSize-pageSize;
        status=status==null?"%%": "%"+status+"%";
        Page<Event> eventPage=new Page<>();
        eventPage.setContent(eventRepository.findPageEventInTaskByStatus(tid,startPoint,pageSize,status));
        eventPage.setPageSize(pageSize);
        eventPage.setPageNum(pageNum);
        eventPage.setTotalElements(findAllEvents(tid)==null?0:findAllEvents(tid).size());
        eventPage.setTotalPages((int)Math.ceil((float)eventPage.getTotalElements()/(float)eventPage.getPageSize()));
        return eventPage;
    }

    @Override
    public List<Event> findNew(Long currentTime,String status, Long tid) {
        status="%"+status+"%";
        return eventRepository.findNew(currentTime,tid,status);
    }

    @Override
    public List<Event> findOld(Long lastTime, Long tid,String status, int size) {
        status="%"+status+"%";
        return eventRepository.findOld(lastTime,tid,status,size);
    }

    @Override
    public Page<Task> findFirstTaskPage(String status) {
        int pageNum=1;int pageSize=10;
        int startPoint=pageNum*pageSize-pageSize;
        status=status==null?"%%": "%"+status+"%";
        Page<Task> taskPage=new Page<>();
        taskPage.setContent(taskRepository.findPageByStatus(startPoint,pageSize,status));
        taskPage.setPageSize(pageSize);
        taskPage.setPageNum(pageNum);
        taskPage.setTotalElements(taskRepository.findAll()==null?0:taskRepository.findAll().size());
        taskPage.setTotalPages((int)Math.ceil((float)taskPage.getTotalElements()/(float)taskPage.getPageSize()));
        return taskPage;
    }

    @Override
    public List<Task> findTaskNew(Long currentTime, String status) {
        status="%"+status+"%";
        return taskRepository.findNew(currentTime,status);
    }

    @Override
    public List<Task> findTaskOld(Long lastTime, String status, int size) {
        status="%"+status+"%";
        return taskRepository.findOld(lastTime,status,size);
    }

    @Override
    public List<Task> findPage(int pageNum, int pageSize, String worker) {
        worker="%"+worker+"%";
        return taskRepository.findPage(pageSize,pageNum*pageSize-pageSize,worker);
    }

    @Override
    public List<Task> findPageByStatus(int pageNum, int pageSize, String status, String worker) {
        worker="%"+worker+"%";
        return taskRepository.findBystatus(status,worker,pageSize,pageSize*pageNum-pageSize);
    }

    @Override
    public List<Task> findTaskBelongStaffByToken() {
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        return taskRepository.findByStaffId(staffInfor.getStaffId());
    }


    @Override
    public Page<Task> findPageTaskBelongStaffByTokenAndStatus(int pageNum, int pageSize, String status) {
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        status="%"+status+"%";
        Page<Task> page=new Page<>();
        page.setContent(taskRepository.findingByStaffIdAndStatus(staffInfor.getStaffId(),status,pageSize,pageNum*pageSize-pageSize));
        page.setTotalElements(taskRepository.findingCountByStaffIdAndStatus(staffInfor.getStaffId(),status));
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotalPages((int)Math.ceil((float)page.getTotalElements()/(float)pageSize));
        return page;
    }

    @Override
    public void setStaffs(Long tid,List<Long> staffIds) {
        List<StaffInfor> staffInfors=new ArrayList<>();
        for(int i=0;i<staffIds.size();i++){
            StaffInfor staffInfor=staffInforRepository.findBystaffId(staffIds.get(i));
            if(staffInfor!=null)
                staffInfors.add(staffInfor);
        }
        Task task=taskRepository.findBytid(tid);
        task.setStaffWorkers(staffInfors);
        taskRepository.save(task);
    }

    @Override
    public int findCountByStatus(String status) {
        status="%"+status+"%";
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        return taskRepository.findBystatus(status,staffInfor.getStaffId()).size();
    }

    @Override
    public void setLeaders(Long tid, List<Long> staffIds) {
        List<StaffInfor> staffInfors=new ArrayList<>();
        for(int i=0;i<staffIds.size();i++){
            StaffInfor staffInfor=staffInforRepository.findBystaffId(staffIds.get(i));
            if(staffInfor!=null)
                staffInfors.add(staffInfor);
        }
        Task task=taskRepository.findBytid(tid);
        task.setLeaders(staffInfors);
        taskRepository.save(task);
    }

    @Override
    public void setDepartment(Long tid, Long did) {
        Department department=departmentRepository.findBydid(did);
        Task task=taskRepository.findBytid(tid);
        if(department!=null)
            task.setDepartment(department);
        taskRepository.save(task);
    }

    @Override
    public void setCreator(Long tid, Long staffId) {
        StaffInfor staffInfor=staffInforRepository.findBystaffId(staffId);
        Task task=taskRepository.findBytid(tid);
        if(staffInfor!=null)
            task.setCreator(staffInfor);
        taskRepository.save(task);
    }
}
