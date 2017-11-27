package br.edu.ulbra.gestaoloja.controller;

import br.edu.ulbra.gestaoloja.input.UserInput;
import br.edu.ulbra.gestaoloja.service.interfaces.SecurityService;
import br.edu.ulbra.gestaoloja.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("redirect:/produtos");
        mv.addObject("loggedUser", securityService.findLoggedInUser());
        return mv;
    }
    
    @GetMapping("/login")
    public ModelAndView loginForm(){
        ModelAndView mv = new ModelAndView("/login");
        return mv;
    }
}
