package com.example.qck.manual;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String saveManual(@ModelAttribute("manual") Manual manual){
        manualService.saveManual(manual);
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


}
