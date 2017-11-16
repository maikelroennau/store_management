package br.edu.ulbra.gestaoloja.controller;

import br.edu.ulbra.gestaoloja.input.ProductInput;
import br.edu.ulbra.gestaoloja.model.Comment;
import br.edu.ulbra.gestaoloja.model.Product;
import br.edu.ulbra.gestaoloja.repository.CommentRepository;
import br.edu.ulbra.gestaoloja.repository.ProductRepository;
import java.util.List;
import java.util.Objects;
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
    CommentRepository commentRepository;

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
    
    @GetMapping(value="/{produtoId}/comentarios")
    public ModelAndView cometariosProdudo(@PathVariable(name="produtoId") Long id) {
        Product product = productRepository.findOne(id);
        ProductInput productInput = mapper.map(product, ProductInput.class);
        ModelAndView mv = this.productForm(productInput);
        mv.setViewName("admin/produtos/comentarios");
        return mv;
    }
    
    @GetMapping(value="/{commentId}/comentarios/excluir")
    public ModelAndView excluirComentario(@PathVariable(name="commentId") Long id){
        Comment comment = commentRepository.findOne(id);
        commentRepository.delete(comment);
        return new ModelAndView("redirect:/admin/produtos/" + comment.getProduct().getId() + "/comentarios/");
    }

    @GetMapping(value="/{produtoId}/excluir")
    public ModelAndView excluirProduto(@PathVariable(name="produtoId") Long id){
        Product product = productRepository.findOne(id);
        productRepository.delete(product);
        return new ModelAndView("redirect:/admin/produtos/");
    }
}
