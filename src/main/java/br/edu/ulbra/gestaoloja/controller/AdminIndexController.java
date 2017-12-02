package br.edu.ulbra.gestaoloja.controller;

import br.edu.ulbra.gestaoloja.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("redirect:/admin/produtos/");
    }
}
