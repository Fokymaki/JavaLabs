package com.example.lab7_v6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Controller
@RequestMapping("/planes")
public class PlaneController {

    private final PlaneRepository planeRepository;

    @Autowired
    public PlaneController(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @GetMapping("/list")
    public String listPlanes(Model model) {
        model.addAttribute("planes", planeRepository.findAll());
        return "planes/list";
    }

    @GetMapping("/add")
    public String addPlaneForm(Model model) {
        model.addAttribute("plane", new Plane());
        return "planes/create";
    }

    @PostMapping("/add")
    public String addPlane(@ModelAttribute("plane") @Valid Plane plane, BindingResult result) {
        if (result.hasErrors()) {
            return "planes/create";
        }
        planeRepository.save(plane);
        return "redirect:/planes/list";
    }

    @GetMapping("/edit/{id}")
    public String editPlaneForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("plane", planeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid plane Id:" + id)));
        return "planes/update";
    }

    @PostMapping("/edit/{id}")
    public String updatePlane(@PathVariable("id") Long id, @ModelAttribute("plane") @Valid Plane plane,
                              BindingResult result) {
        if (result.hasErrors()) {
            plane.setId(Math.toIntExact(id));
            return "planes/update";
        }
        planeRepository.save(plane);
        return "redirect:/planes/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePlane(@PathVariable("id") Long id, Model model) {
        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid plane Id:" + id));
        planeRepository.delete(plane);
        return "redirect:/planes/list";
    }
}
