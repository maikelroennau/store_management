package br.edu.ulbra.gestaoloja.controller;

import br.edu.ulbra.gestaoloja.input.CommentInput;
import br.edu.ulbra.gestaoloja.input.ProductInput;
import br.edu.ulbra.gestaoloja.model.Comment;
import br.edu.ulbra.gestaoloja.model.Product;
import br.edu.ulbra.gestaoloja.repository.CommentRepository;
import br.edu.ulbra.gestaoloja.repository.ProductRepository;
import java.util.HashMap;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    CommentRepository commentRepository;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ModelAndView listarProdutos() {
        ModelAndView mv = new ModelAndView("listarProdutos");
        List<Product> produtos = (List<Product>) productRepository.findAll();
        mv.addObject("products", produtos);
        
        HashMap<Long, Integer> positivos = new HashMap<>();
        HashMap<Long, Integer> negativos = new HashMap<>();
        
        for (Product product : produtos) {
            if (product.getComments().size() > 0) {
                for (Comment comment : product.getComments()) {
                    
                    if (!positivos.containsKey(product.getId())) {
                        positivos.put(product.getId(), 0);
                    }
                    
                    if (!negativos.containsKey(product.getId())) {
                        negativos.put(product.getId(), 0);
                    }
                    
                    if (comment.getLiked()) {
                        positivos.put(product.getId(), positivos.get(product.getId()) + 1);
                    } else {
                        negativos.put(product.getId(), negativos.get(product.getId()) + 1);
                    }
                }
            } else {
                positivos.put(product.getId(), 0);
                negativos.put(product.getId(), 0);
            }
        }
        
        mv.addObject("positivos", positivos);
        mv.addObject("negativos", negativos);
        return mv;
    }
    
    
    @GetMapping("/{produtoId}")
    public ModelAndView detalharProduto(@PathVariable(name="produtoId") Long id) {
        ModelAndView mv = new ModelAndView("detalhesProduto");
        
        Product produto = productRepository.findOne(id);
        mv.addObject("product", produto);
        mv.addObject("comment", new CommentInput());
        
        Integer positivos = 0;
        
        for (Comment comment : produto.getComments()) {
            if (comment.getProduct().getId() == id) {
                if (comment.getLiked()) {
                    positivos++;
                }
            }
        }
        
        mv.addObject("positivos", positivos);
        mv.addObject("negativos", (Integer)(produto.getComments().size() - positivos));
        return mv;
    }
    
    @PostMapping("/{produtoID}/comentario")
    public ModelAndView incluirComentario(CommentInput commentInput) {
        Comment comment = mapper.map(commentInput, Comment.class);
        commentRepository.save(comment);
        return new ModelAndView("redirect:/produtos/");
    }
}