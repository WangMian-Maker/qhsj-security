package com.example.demo.controller;

import com.example.demo.entity.PDF;
import com.example.demo.entity.params.Page;
import com.example.demo.service.impl.PDFServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("data/pdf")
@RestController
public class PDFDownloadController {

    @Autowired
    private PDFServiceImpl pdfService;

    @PostMapping("/save")
    public String save(@RequestPart("pdf") String pdfStr, @RequestPart("pdfFile") MultipartFile pdfFile){
        PDF pdf= (PDF) JSONObject.toBean(new JSONObject(pdfStr),PDF.class);
        pdfService.save(pdf,pdfFile);
        return "success";
    }

    @PutMapping("/update")
    public String update(@RequestBody PDF pdf){
        pdfService.update(pdf);
        return "success";
    }

    @DeleteMapping("/deleteById/{id}")
    public String delete(@PathVariable("id") Long id){
        pdfService.deleteById(id);
        return "success";
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Page<PDF> findPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize")int pageSize){
        return pdfService.findPage(pageNum,pageSize);
    }

    @PostMapping("/findAll")
    public List<PDF> findAll(){
        return pdfService.findAll();
    }

    @PostMapping("/findPath/{id}")
    public String findPath(@PathVariable("id") Long id){
        return pdfService.findPath(id);
    }
}
