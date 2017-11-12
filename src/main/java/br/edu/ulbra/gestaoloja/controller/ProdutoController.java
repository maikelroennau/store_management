package br.edu.ulbra.gestaoloja.controller;

import br.edu.ulbra.gestaoloja.model.Product;
import br.edu.ulbra.gestaoloja.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProductRepository productRepository;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping("/{produtoId}")
    public String listarProduto(@PathVariable Integer produtoId) {
        return "listarProduto";
    }

    @GetMapping
    public ModelAndView listarProdutos() {
        ModelAndView mv = new ModelAndView("listarProduto");
        List<Product> produtos = (List<Product>) productRepository.findAll();
        mv.addObject("products", produtos);
        return mv;
    }
}