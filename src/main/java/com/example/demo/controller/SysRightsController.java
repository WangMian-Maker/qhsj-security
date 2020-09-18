package com.example.demo.controller;

import com.example.demo.entity.user.SysRights;
import com.example.demo.config.utils.JwtTokenUtils;
import com.example.demo.entity.user.SysRole;
import com.example.demo.entity.user.SysUser;
import com.example.demo.service.impl.SysRightsServiceImpl;
import com.example.demo.service.impl.SysRoleServiceImpl;
import com.example.demo.service.impl.SysUserServiceImpl;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("data/rights")
public class SysRightsController {
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private SysRightsServiceImpl sysRightsService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysRoleServiceImpl sysRoleService;
    @PostMapping("/save")
    public String add(@RequestBody SysRights rights){
        sysRightsService.add(rights);
        return "success";
    }
    @PutMapping("/update")
    public String update(@RequestBody SysRights rights){
        sysRightsService.update(rights);
        return "success";
    }
    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id){
        sysRightsService.deleteById(id);
        return "success";
    }
    @DeleteMapping("/deleteFrom/{sourceId}/{toId}")
    public String deleteFrom(@PathVariable("sourceId")Long sourceId,@PathVariable("toId")Long toId){
        SysRights source=sysRightsService.findById(sourceId);
        SysRights to=sysRightsService.findById(toId);
        List<SysRights> rights=to.getChildren();
        if(rights.contains(source)){
            rights.remove(source);
        }
        to.setChildren(rights);
        sysRightsService.update(to);
        return "success";
    }

    @PostMapping("/findAll")
    public List<SysRights> findAll(){
        return  sysRightsService.findAll();
    }

    @PostMapping("/add/{sourceId}/{toId}/{order}")
    public String addTo(@PathVariable("sourceId") Long sourceId,@PathVariable("toId") Long toId,@PathVariable("order") Integer order){
        sysRightsService.addTo(sourceId,toId,order);
        return "success";
    }

    @PostMapping("/getMenuByToken")
    public String getMenuByToken(){
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        List<SysRights> rights=sysRightsService.findRightsByToken(token);
        JSONArray jsonArray=JSONArray.fromArray(rights.toArray());
        String json=jsonArray.toString();
        return json;
    }
}
