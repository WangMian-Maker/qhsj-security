package com.example.demo.service.impl;

import com.example.demo.entity.user.SysRights;
import com.example.demo.entity.user.SysRole;
import com.example.demo.config.log.Log;
import com.example.demo.entity.user.SysUser;
import com.example.demo.repository.user.SysRightsRepository;
import com.example.demo.repository.user.SysRoleRepository;
import com.example.demo.repository.user.SysUserRepository;
import com.example.demo.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private SysRightsRepository sysRightsRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Override
    public void add(SysRole role) {
        role.setSid(getId());
        sysRoleRepository.save(role);
    }

    @Override
    public String update(SysRole role) {
        Long id=role.getSid();
        if(sysRoleRepository.findById(id)==null){
            Log.logger.error("no this id in table sys_role");
            return "找不到此id的角色";
        }
        sysRoleRepository.save(role);
        return "success";
    }

    @Override
    public String deleteById(Long sid) {
        SysRole role=sysRoleRepository.findBysid(sid);
        if(role==null){
            Log.logger.error("no this id in table sys_role");
            return "找不到此id的角色";
        }
        List<Long> userIds=sysRoleRepository.findRoleInUser(sid);
        if(userIds!=null){
            for(Long id :userIds){
                SysUser user=sysUserRepository.findBysid(id);
                List<SysRole> roles=user.getRoles();
                if(roles.contains(role))
                roles.remove(role);
                user.setRoles(roles);
                sysUserRepository.save(user);
            }
        }
        sysRoleRepository.deleteById(sid);
        return "success";
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleRepository.findAllOrderBySid();
    }

    @Override
    public Page<SysRole> findAllPage(int pageNum, int pageSize) {
        PageRequest request=PageRequest.of(pageNum-1,pageSize);
        return sysRoleRepository.findAll(request);
    }

    @Override
    public SysRole findById(Long id) {
        if(sysRoleRepository.findBysid(id)==null){
            Log.logger.error("no this id in table sys_role");
        }
        return sysRoleRepository.findBysid(id);
    }

    @Override
    public SysRole findByRoleName(String roleName) {
        return sysRoleRepository.findByroleName(roleName);
    }

    @Override
    public void addRightById(Long roleId, List<Long> ids) {
        List<SysRights> rights=sysRightsRepository.findAllMenu();
        System.out.println(rights.size()+"==================================================================");
        SysRole role=sysRoleRepository.findBysid(roleId);
        for (int i=0;i<rights.size();i++){
            if(!ids.contains(rights.get(i).getSid())){
                rights.remove(rights.get(i));
                i=i-1;
            }
            else {
                Deque<SysRights> nodes=new ArrayDeque<>();
                SysRights node=rights.get(i);
                nodes.push(node);
                while (!nodes.isEmpty()){
                    node=nodes.pop();
                    for(int k=0;k<node.getChildren().size();k++){
                        if(!ids.contains(node.getChildren().get(k).getSid())){
                            node.getChildren().remove(node.getChildren().get(k));
                            k=k-1;
                        }
                        else {
                            nodes.push(node.getChildren().get(k));
                        }
                    }
                }
            }
        }
        role.setRights(rights);
        sysRoleRepository.save(role);
    }


    Long getId(){
        List<Long> allId=sysRoleRepository.findAllSid();
        if(allId==null||allId.size()==0){
            return 1L;
        }
        for(int i=1;i<allId.size();i++){
            if(allId.get(i)-allId.get(i-1)>1){
                return allId.get(i-1)+1;
            }
        }
        return sysRoleRepository.maxId()+1;
    }
}
