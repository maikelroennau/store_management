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
        ModelAndView mv = new ModelAndView("admin/produtos/index");
        List<Product> produtos = (List<Product>) productRepository.findAll();
        mv.addObject("products", produtos);
        return mv;
    }

    @GetMapping(value = "/novo")
    public ModelAndView dadosNovoProduto(){
        return this.productForm(new ProductInput());
    }

    @PostMapping(value="/novo")
    public ModelAndView incluirNovoProduto(ProductInput productInput){
        Product product = mapper.map(productInput, Product.class);
        productRepository.save(product);
        return new ModelAndView("redirect:/admin/produtos/");
    }
    
    private ModelAndView productForm(ProductInput productInput){
        ModelAndView mv = new ModelAndView("admin/produtos/novo");
        mv.addObject("product", productInput);
        return mv;
    }

    @GetMapping(value="/{produtoId}")
    public ModelAndView editarProduto(@PathVariable(name="produtoId") Long id){
        Product produto = productRepository.findOne(id);
        ProductInput productInput = mapper.map(produto, ProductInput.class);
        ModelAndView mv = this.productForm(productInput);
        mv.setViewName("admin/produtos/editar");
        return mv;
    }   

    @PostMapping(value="/{produtoId}")
    public ModelAndView salvarProduto(@PathVariable(name="produtoId") Long id, ProductInput productInput){
        Product product = productRepository.findOne(id);
        product.setName(productInput.getName());
        product.setDescription(productInput.getDescription());
        product.setImagePath(productInput.getImagePath());
        productRepository.save(product);
        return new ModelAndView("redirect:/admin/produtos/");
    }

    @GetMapping(value="/{produtoId}/excluir")
    public ModelAndView excluirProduto(@PathVariable(name="produtoId") Long id){
        Product product = productRepository.findOne(id);
        productRepository.delete(product);
        return new ModelAndView("redirect:/admin/produtos/");
    }
}
