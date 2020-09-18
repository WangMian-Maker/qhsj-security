package com.example.demo.config.rbac;

import com.example.demo.entity.user.SysRights;
import com.example.demo.entity.user.SysRole;
import com.example.demo.config.log.Log;
import com.example.demo.repository.user.SysRoleRepository;
import com.example.demo.repository.user.SysUserRepository;
import org.apache.xpath.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component("rbacService")
public class RbacServiceImpl implements RbacService{
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private SysUserRepository sysUserRepository;


    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal=authentication.getPrincipal();
        Set<String> urls=new HashSet<>();
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        System.out.println("我干哦我干哦我干哦我干哦我干哦我干哦我干哦我干哦我干哦我干哦我干哦我干哦我干哦"+principal.toString());
        if(!(principal instanceof UserDetails)&&principal!=null){
            String account=principal.toString();
            List<SysRole> tmpRoles=sysUserRepository.findByaccount(account).getRoles();
            return hasPermission(tmpRoles, request.getRequestURI());
        }
        String account=((UserDetails)principal).getUsername();
        List<SysRole> tmpRoles=sysUserRepository.findByaccount(account).getRoles();
        return hasPermission(tmpRoles, request.getRequestURI());
    }

    Boolean roleHasPermission(SysRole role,String requestUrl){
        List<SysRights> rights=role.getRights();
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        for (SysRights rights1:rights){
            if(antPathMatcher.match(rights1.getUrl(),requestUrl)){
                return true;
            }
            else {
                Deque<SysRights> nodes=new ArrayDeque<>();
                SysRights node=rights1;
                nodes.push(node);
                while (!nodes.isEmpty()){
                    node=nodes.pop();
                    for(SysRights rights2:node.getChildren()){
                        if(antPathMatcher.match(rights2.getUrl(),requestUrl)){
                            return true;
                        }
                        else {
                            nodes.push(rights2);
                        }
                    }
                }
            }
        }
        return false;
    }
    Boolean hasPermission(List<SysRole> roles,String requestUrl){
        for(SysRole role:roles){
            if(roleHasPermission(role,requestUrl)){
                return true;
            }
        }
        return false;
    }

}
