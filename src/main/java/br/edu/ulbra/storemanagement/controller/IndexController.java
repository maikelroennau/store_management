/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ulbra.storemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Maikel Maciel Rönnau
 */
@Controller
public class IndexController {
    
    @RequestMapping("/")
    public String index() {
        return "index";
    }
    
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }
    
    @RequestMapping("/product")
    public String product() {
        return "product";
    }
    
    @RequestMapping("/admin/products")
    public String admin_products() {
        return "products";
    }
    
    @RequestMapping("/admin/products/new")
    public String admin_products_new() {
        return "new";
    }
}
