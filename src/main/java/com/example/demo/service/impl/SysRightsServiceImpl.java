package com.example.demo.service.impl;

import com.example.demo.config.utils.JwtTokenUtils;
import com.example.demo.entity.user.SysRights;
import com.example.demo.config.log.Log;
import com.example.demo.entity.user.SysRole;
import com.example.demo.entity.user.SysUser;
import com.example.demo.repository.user.SysRightsRepository;
import com.example.demo.repository.user.SysUserRepository;
import com.example.demo.service.SysRightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Service
public class SysRightsServiceImpl implements SysRightsService {
    @Autowired
    private SysRightsRepository sysRightsRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Override
    public void add(SysRights rights) {
        rights.setSid(getId());
        sysRightsRepository.save(rights);
    }

    @Override
    public String update(SysRights rights) {
        Long id=rights.getSid();
        if(sysRightsRepository.findById(id)==null){
            Log.logger.error("no this id in table sys_right");
            return "没有此id的用户权限";
        }
        sysRightsRepository.save(rights);
        return "success";
    }

    @Override
    public String deleteById(Long sid) {
        if(sysRightsRepository.findById(sid)==null){
            Log.logger.error("no this id in table sys_right");
            return "没有此id的用户权限";
        }
        sysRightsRepository.deleteById(sid);
        return "success";
    }

    @Override
    public List<SysRights> findAll() {
        return sysRightsRepository.findAllMenu();
    }

    @Override
    public Page<SysRights> findAllPage(int pageNum, int pageSize) {
        PageRequest request=PageRequest.of(pageNum-1,pageSize);
        return sysRightsRepository.findAll(request);
    }

    @Override
    public SysRights findById(Long id) {
        if(sysRightsRepository.findBysid(id)==null){
            Log.logger.error("no this id in table sys_rights");
        }
        return sysRightsRepository.findBysid(id);
    }

    @Override
    public void addTo(Long source, Long to,Integer order) {
        SysRights sourceR=sysRightsRepository.findBysid(source);
        SysRights toR=sysRightsRepository.findBysid(to);
        List<SysRights> rights=toR.getChildren();
        sourceR.setM_order("c"+order.toString()+toR.getM_order());
        if(!rights.contains(sourceR)){
            rights.add(sourceR);
        }
        //String [] tmps=rights.get(0).getM_order().split("c|r");
        List<SysRights> m_rights= makeIndex(rights);
        toR.setChildren(m_rights);
        sysRightsRepository.save(sourceR);
        sysRightsRepository.save(toR);

    }

    @Override
    public List<SysRights> findRightsByToken(String token) {
        String account= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(account);
        List<Long> ids=RightsIds(user.getRoles());
        List<SysRights> rights=sysRightsRepository.findAllMenu();
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
        rights=makeIndex(rights);
        return  rights;
    }
    List<Long> RightsIds(List<SysRole> roles){
        //List<SysRights> rights=sysRightsRepository.findAllMenu();
        List<Long> ids=new ArrayList<>();
        for(SysRole role:roles){
            //SysRole role=sysRoleRepository.findBysid(roleId);
            List<SysRights> rights=role.getRights();
            for (int i=0;i<rights.size();i++){
                if(!ids.contains(rights.get(i).getSid())){
                    ids.add(rights.get(i).getSid());
                }
                Deque<SysRights> nodes=new ArrayDeque<>();
                SysRights node=rights.get(i);
                nodes.push(node);
                while (!nodes.isEmpty()){
                    node=nodes.pop();
                    for(int k=0;k<node.getChildren().size();k++){
                        if(!ids.contains(node.getChildren().get(k).getSid())){
                            ids.add(node.getChildren().get(k).getSid());
                        }
                        nodes.push(node.getChildren().get(k));
                    }
                }
            }
        }
        return  ids;
    }

    Integer GetOrder(String order){
        String [] tmps=order.split("c|r");

        return Integer.parseInt(tmps[1]);
    }
//    @Override
//    public void set(Long cid, Long pid) {
//        SysRights cRights=sysRightsRepository.findBysid(cid);
//        SysRights pRights=sysRightsRepository.findBysid(pid);
//        List<SysRights> rights=pRights.getChildren();
//        rights.add(cRights);
//        rights=makeIndex(rights);
//        pRights.setChildren(rights);;
//        sysRightsRepository.save(pRights);
//    }

    public List<SysRights> makeIndex(List<SysRights> rights){
        for(int i=0;i<rights.size()-1;i++){
            for(int j=0;j<rights.size()-i-1;j++){
                System.out.println(GetOrder(rights.get(j).getM_order())+"asd:"+j);
                System.out.println(GetOrder(rights.get(j+1).getM_order())+"asd:"+(j+1));
                System.out.println("=====================");
                if(GetOrder(rights.get(j).getM_order())>= GetOrder(rights.get(j+1).getM_order())){
                    SysRights tmp=rights.get(j);
                    rights.set(j,rights.get(j+1));
                    rights.set(j+1,tmp);

                }
            }
        }
        return rights;
    }



    Long getId(){
        List<Long> allId=sysRightsRepository.findAllSid();
        if(allId==null||allId.size()==0){
            return 1L;
        }
        for(int i=1;i<allId.size();i++){
            if(allId.get(i)-allId.get(i-1)!=1){
                return allId.get(i-1)+1;
            }
        }
        return sysRightsRepository.maxId()+1;
    }
}
