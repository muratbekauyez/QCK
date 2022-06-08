package com.example.qck.controller;

import com.example.qck.service.ManualService;
import com.example.qck.model.Manual;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/manuals")
@AllArgsConstructor
public class ManualController {

    @Autowired
    private final ManualService manualService;

    @GetMapping
    public String manualPage(Model model){
        model.addAttribute("listManuals", manualService.getAllManuals());
        return "manuals/manuals";
    }

    @GetMapping("/new")
    public String showNewManualForm(Model model){
        Manual manual = new Manual();
        model.addAttribute("manual", manual);
        return "manuals/new_manual";
    }

    @PostMapping("/saveManual")
    public String saveManual(@RequestParam Map<String, String> requestParams, @RequestParam("content")MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        manualService.saveManual(requestParams, multipartFile, ra);
        return "redirect:/manuals";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateManualForm(@PathVariable(value = "id") Long id, Model model){
        Manual manual = manualService.getManualById(id);
        model.addAttribute("manual", manual);
        return "manuals/update_manual";
    }

    @PostMapping("/updateManual")
    public String updateManual(@ModelAttribute("manual") Manual manual){
        manualService.updateManual(manual);
        return "redirect:/manuals";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id")Long id){
        manualService.deleteById(id);
        return "redirect:/manuals";
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id")Long id, HttpServletResponse response) throws Exception {
        manualService.downloadFile(id, response);
    }


}
