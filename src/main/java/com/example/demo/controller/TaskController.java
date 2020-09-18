package com.example.demo.controller;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.Task;
import com.example.demo.entity.events.Event;
import com.example.demo.entity.params.TaskParam1;
import com.example.demo.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("data/task")
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private HttpServletRequest request;
    @PostMapping("/create")
    public String create(@RequestBody Task task){
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        taskService.createTask(task,token);
        return "success";
    }

    @PutMapping("/update")
    public String update(@RequestBody Task task){
        if(taskService.update(task)){
            return "success";
        }
        else {
            return "fail";
        }
    }

    @PostMapping("/setLeaders/{tid}")
    public String setLeaders(@RequestBody List<Long> staffIds,@PathVariable("tid") Long tid){
        taskService.setLeaders(tid,staffIds);
        return "success";
    }

    @PostMapping("/setDepartment/{tid}/{did}")
    public String setDepartment(@PathVariable("tid") Long tid,@PathVariable("did") Long did){
        taskService.setDepartment(tid,did);
        return "success";
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id){
        taskService.deleteById(id);
        return "success";
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Page<Task> findPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return taskService.findPage(pageNum,pageSize);
    }

    @PostMapping("/findAllEvent/{tid}")
    public List<Event> addEvent(@PathVariable("tid") Long tid){

        return taskService.findAllEvents(tid);
    }

    @PostMapping("/findPageByWorker/{pageNum}/{pageSize}/{worker}")
    public List<Task> findPageByWorker(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@PathVariable("worker") String worker){
        return taskService.findPage(pageNum,pageSize,worker);
    }

    @PostMapping("/findPageByTokenAndStatus")
    public List<Task> findPageByTokenAndStatus(@RequestBody TaskParam1 taskParam1){
        return taskService.findPageTaskBelongStaffByTokenAndStatus(taskParam1.getPageNum(),taskParam1.getPageSize(),taskParam1.getStatus());
    }
    @PostMapping("/findCountByTokenAndStatus")
    public int findCountByTokenAndStatus(@RequestBody TaskParam1 taskParam1){
        return taskService.findCountByStatus(taskParam1.getStatus());
    }

    @PostMapping("/setStaffForTask/{tid}")
    public String setStaffForTask(@PathVariable("tid") Long tid,@RequestBody List<Long> staffIds ){
        taskService.setStaffs(tid,staffIds);
        return "success";
    }

    @PostMapping("/getCountList")
    public List<Integer> getCountList(){
        List<Integer> countList=new ArrayList<>();
        countList.add(taskService.findCountByStatus("待处理"));
        countList.add(taskService.findCountByStatus("已处理"));
        countList.add(taskService.findCountByStatus("处理中"));
        return countList;
    }
}
