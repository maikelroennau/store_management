package br.edu.ulbra.gestaoloja.controller;

import br.edu.ulbra.gestaoloja.input.ProductInput;
import br.edu.ulbra.gestaoloja.model.Product;
import br.edu.ulbra.gestaoloja.repository.ProductRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/produtos")
public class AdminProdutoController {
    
    @Autowired
    ProductRepository productRepository;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping("/")
    public ModelAndView listarProdutos(){
        System.out.println("Listing on index");
        ModelAndView mv = new ModelAndView("admin/produtos/index");
        List<Product> produtos = (List<Product>) productRepository.findAll();
        mv.addObject("products", produtos);
        return mv;
    }

    @GetMapping(value = "/novo")
    public String dadosNovoProduto(){
        return "admin/produtos/novo";
    }

    @PostMapping(value="/novo")
    public String incluirNovoProduto(){
        return "admin/produtos/novo";
    }
    
    private ModelAndView productForm(ProductInput productInput){
        System.out.println("Getting form");
        ModelAndView mv = new ModelAndView("admin/produtos/novo");
        mv.addObject("product", productInput);
        return mv;
    }

    @GetMapping(value="/{produtoId}")
    public ModelAndView editarProduto(@PathVariable(name="produtoId") Long id){
        System.out.println("editarProduto");
        Product produto = productRepository.findOne(id);
        ProductInput productInput = mapper.map(produto, ProductInput.class);
        ModelAndView mv = this.productForm(productInput);
        mv.setViewName("admin/produtos/editar");
        return mv;
    }   

    @PostMapping(value="/{produtoId}")
    public String salvarProduto(@PathVariable Integer produtoId){
        System.out.println("salvarProduto");
        return "admin/produtos/editar";
    }

    @DeleteMapping(value="/{produtoId}")
    public String excluirProduto(@PathVariable Integer produtoId){
        return "admin/produtos/excluir";
    }
}
