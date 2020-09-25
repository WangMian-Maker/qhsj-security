package com.example.demo.service.impl;

import com.example.demo.entity.PDF;
import com.example.demo.entity.params.Page;
import com.example.demo.repository.PDFRepository;
import com.example.demo.service.PDFService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Transactional
@Service
public class PDFServiceImpl implements PDFService {
    @Autowired
    private PDFRepository pdfRepository;
    @Override
    public void save(PDF pdf, MultipartFile pdfFile) {

        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/PDF/";
        dealFile(pdfFile,rootPath,pdf.getName()+"-"+pdf.getTime()+".pdf");
        pdf.setPath("/static/PDF/"+pdf.getName()+"-"+pdf.getTime()+".pdf");
        Long id=1L;
        if(pdfRepository.maxId()!=null){
            id=pdfRepository.maxId()+1;
        }
        pdf.setId(id);
        pdfRepository.save(pdf.getId(),pdf.getName(),pdf.getTime(),pdf.getPath());
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
        return null;
    }

    @Override
    public PDF findById(Long id) {
        return null;
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
