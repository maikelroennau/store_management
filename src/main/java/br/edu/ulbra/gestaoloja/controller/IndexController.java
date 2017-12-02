package br.edu.ulbra.gestaoloja.controller;

import br.edu.ulbra.gestaoloja.service.interfaces.SecurityService;
import br.edu.ulbra.gestaoloja.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Value("${gestao-loja.uploadFilePath}")
    private String uploadFilePath;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("redirect:/produtos");
        mv.addObject("loggedUser", securityService.findLoggedInUser());
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView loginForm() {
        ModelAndView mv = new ModelAndView("/login");
        return mv;
    }

    @GetMapping("/files/{fileName:.+}")
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("fileName") String fileName) {
        return new FileSystemResource(uploadFilePath + "/" + fileName);
    }
}
