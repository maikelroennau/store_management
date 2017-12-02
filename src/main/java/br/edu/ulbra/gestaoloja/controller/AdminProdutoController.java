package br.edu.ulbra.gestaoloja.controller;

import br.edu.ulbra.gestaoloja.input.UploadInput;
import br.edu.ulbra.gestaoloja.input.ProductInput;
import br.edu.ulbra.gestaoloja.model.Comment;
import br.edu.ulbra.gestaoloja.model.Product;
import br.edu.ulbra.gestaoloja.repository.CommentRepository;
import br.edu.ulbra.gestaoloja.repository.ProductRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;

@Controller
@RequestMapping("/admin/produtos")
public class AdminProdutoController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommentRepository commentRepository;

    @Value("${gestao-loja.uploadFilePath}")
    private String uploadFilePath;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ModelAndView listarProdutos() {
        ModelAndView mv = new ModelAndView("admin/produtos/index");
        List<Product> produtos = (List<Product>) productRepository.findAll();
        mv.addObject("products", produtos);
        return mv;
    }

    @GetMapping(value = "/novo")
    public ModelAndView dadosNovoProduto() {
        return this.productForm(new ProductInput());
    }

    @PostMapping(value = "/novo")
    public ModelAndView incluirNovoProduto(ProductInput productInput, UploadInput uploadInput) throws IOException {
        Product product = mapper.map(productInput, Product.class);

        MultipartFile multipartFile = uploadInput.getMultipartFile();

        if (multipartFile != null) {
            String fileName = UUID.randomUUID().toString() + "_" + uploadInput.getMultipartFile().getOriginalFilename();
            File file = new File(uploadFilePath + "/" + fileName);
            file.createNewFile();
            multipartFile.transferTo(file);
            product.setImagePath(fileName);
        }

        productRepository.save(product);
        return new ModelAndView("redirect:/admin/produtos/");
    }

    private ModelAndView productForm(ProductInput productInput) {
        ModelAndView mv = new ModelAndView("admin/produtos/novo");
        mv.addObject("product", productInput);
        return mv;
    }

    @GetMapping(value = "/{produtoId}")
    public ModelAndView editarProduto(@PathVariable(name = "produtoId") Long id) {
        Product produto = productRepository.findOne(id);
        ProductInput productInput = mapper.map(produto, ProductInput.class);
        ModelAndView mv = this.productForm(productInput);
        mv.setViewName("admin/produtos/editar");
        return mv;
    }

    @PostMapping(value = "/{produtoId}")
    public ModelAndView salvarProduto(@PathVariable(name = "produtoId") Long id, ProductInput productInput, UploadInput uploadInput) throws IOException {
        Product product = productRepository.findOne(id);
        product.setName(productInput.getName());
        product.setDescription(productInput.getDescription());

        MultipartFile multipartFile = uploadInput.getMultipartFile();

        if (multipartFile != null && !uploadInput.getMultipartFile().getOriginalFilename().equals("")) {
            String fileName = UUID.randomUUID().toString() + "_" + uploadInput.getMultipartFile().getOriginalFilename();
            File file = new File(uploadFilePath + "/" + fileName);
            file.createNewFile();
            multipartFile.transferTo(file);
            product.setImagePath(fileName);
        }

        productRepository.save(product);
        return new ModelAndView("redirect:/admin/produtos/");
    }

    @GetMapping(value = "/{produtoId}/comentarios")
    public ModelAndView cometariosProdudo(@PathVariable(name = "produtoId") Long id) {
        Product product = productRepository.findOne(id);
        ProductInput productInput = mapper.map(product, ProductInput.class);
        ModelAndView mv = this.productForm(productInput);
        mv.setViewName("admin/produtos/comentarios");
        return mv;
    }

    @GetMapping(value = "/{commentId}/comentarios/excluir")
    public ModelAndView excluirComentario(@PathVariable(name = "commentId") Long id) {
        Comment comment = commentRepository.findOne(id);
        commentRepository.delete(comment);
        return new ModelAndView("redirect:/admin/produtos/" + comment.getProduct().getId() + "/comentarios/");
    }

    @GetMapping(value = "/{produtoId}/excluir")
    public ModelAndView excluirProduto(@PathVariable(name = "produtoId") Long id) {
        Product product = productRepository.findOne(id);
        productRepository.delete(product);
        return new ModelAndView("redirect:/admin/produtos/");
    }
}
