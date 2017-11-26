package br.edu.ulbra.gestaoloja.controller;

import br.edu.ulbra.gestaoloja.input.UserInput;
import br.edu.ulbra.gestaoloja.model.Role;
import br.edu.ulbra.gestaoloja.model.User;
import br.edu.ulbra.gestaoloja.repository.RoleRepository;
import br.edu.ulbra.gestaoloja.repository.UserRepository;
import br.edu.ulbra.gestaoloja.service.interfaces.UserService;
import java.util.HashSet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ModelAndView listUser(){
        ModelAndView mv = new ModelAndView("/admin/user/list");
        List<User> usuarios = (List<User>) userRepository.findAll();
        mv.addObject("users", usuarios);
        return mv;
    }

    private ModelAndView userForm(UserInput userInput){
        ModelAndView mv = new ModelAndView("/user/register/new");
        mv.addObject("user", userInput);
        mv.addObject("roles", roleRepository.findAll());
        return mv;
    }

    @GetMapping("/new")
    public ModelAndView newUser(){
        return this.userForm(new UserInput());
    }

    @PostMapping(value="/new")
    public ModelAndView newUser(UserInput userInput){
        if (userInput.getPassword() != null && userInput.getPassword().length() > 0 && !userInput.getPassword().equals(userInput.getPasswordConfirm())){
            ModelAndView mv = this.userForm(userInput);
            mv.addObject("error", "Password don't match!");
            return mv;
        }

        User user = mapper.map(userInput, User.class);
        Role role = roleRepository.findOne(userInput.getRoleId());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userService.save(user);
        System.out.println(user);
        return new ModelAndView("redirect:/");
    }  
    
    @GetMapping("/{id}")
    public ModelAndView viewUser(@PathVariable(name="id") Long id){
        User usuario = userRepository.findOne(id);
        UserInput userInput = mapper.map(usuario, UserInput.class);
        ModelAndView mv = this.userForm(userInput);
        mv.setViewName("/admin/user/update");
        return mv;
    }

    @PostMapping("/{id}")
    public ModelAndView updateUser(@PathVariable(name="id") Long id, UserInput userInput){
        User usuario = userRepository.findOne(id);
        if (userInput.getPassword() != null && userInput.getPassword().length() > 0 && !userInput.getPassword().equals(userInput.getPasswordConfirm())){
            ModelAndView mv = this.userForm(userInput);
            mv.addObject("error", "Password don't match!");
            return mv;
        }
        usuario.setUsername(userInput.getUsername());
        usuario.setPassword(userInput.getPassword());
        usuario.setName(userInput.getName());
        userRepository.save(usuario);
        return new ModelAndView("redirect:/user/?usercreated=true");
    }
    
    @GetMapping("/{id}/updatePassword")
    public ModelAndView updatePasswordView(@PathVariable(name="id") Long id){
        User usuario = userRepository.findOne(id);
        UserInput userInput = mapper.map(usuario, UserInput.class);
        ModelAndView mv = this.userForm(userInput);
        mv.setViewName("/admin/user/updatePassword");
        return mv;
    }
    
    @PostMapping("/{id}/updatePassword")
    public ModelAndView updatePassword(@PathVariable(name="id") Long id, UserInput userInput) {
        User usuario = userRepository.findOne(id);
        if (userInput.getPassword() != null && userInput.getPassword().length() > 0 && !userInput.getPassword().equals(userInput.getPasswordConfirm())){
            ModelAndView mv = this.userForm(userInput);
            mv.addObject("error", "Password don't match!");
            return mv;
        }
        usuario.setPassword(userInput.getPassword());
        userRepository.save(usuario);
        return new ModelAndView("redirect:/user/?usercreated=true");
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteUser(@PathVariable(name="id") Long id){
        User usuario = userRepository.findOne(id);
        userRepository.delete(usuario);
        return new ModelAndView("redirect:/user/?usercreated=true");
    }
}
