package com.example.demo.service.impl;

import com.example.demo.config.utils.JwtTokenUtils;
import com.example.demo.entity.PDF;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.params.Page;
import com.example.demo.entity.user.SysUser;
import com.example.demo.repository.PDFRepository;
import com.example.demo.repository.user.SysUserRepository;
import com.example.demo.service.PDFService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@Transactional
@Service
public class PDFServiceImpl implements PDFService {
    @Autowired
    private PDFRepository pdfRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Override
    public boolean save(PDF pdf, MultipartFile pdfFile) {
        if(pdfRepository.findByname(pdf.getName())!=null){
            return false;
        }
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/PDF/";
        dealFile(pdfFile,rootPath,pdf.getName()+"-"+pdf.getTime()+".pdf");
        pdf.setPath("/PDF/"+pdf.getName()+"-"+pdf.getTime()+".pdf");
        Long id=1L;
        if(pdfRepository.maxId()!=null){
            id=pdfRepository.maxId()+1;
        }
        pdf.setId(id);

        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        pdfRepository.save(pdf.getId(),pdf.getName(),pdf.getTime(),pdf.getPath(),pdf.getDescribe(),pdf.getKeyWord(),staffInfor.getStaffId());
        return true;
    }

    @Override
    public void update(PDF pdf) {
        pdfRepository.save(pdf);
    }

    @Override
    public void deleteById(Long id) {
        pdfRepository.deleteById(id);
    }

    @Override
    public Page<PDF> findPage(int pageNum, int pageSize) {
        int startPoint =pageNum*pageSize-pageSize;
        Page<PDF> pdfPage=new Page<>();
        pdfPage.setContent(pdfRepository.findPage(pageSize,startPoint));
        pdfPage.setPageNum(pageNum);
        pdfPage.setPageSize(pageSize);
        pdfPage.setTotalElements(findAll().size());
        pdfPage.setTotalPages((int)Math.ceil(pdfPage.getTotalElements()/pdfPage.getPageSize()));
        return pdfPage;
    }

    @Override
    public List<PDF> findAll() {
        return pdfRepository.findAll();
    }

    @Override
    public PDF findById(Long id) {
        return pdfRepository.findByid(id);
    }

    @Override
    public String findPath(Long id) {
        PDF pdf=pdfRepository.findByid(id);
        return pdf.getPath();
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
}
