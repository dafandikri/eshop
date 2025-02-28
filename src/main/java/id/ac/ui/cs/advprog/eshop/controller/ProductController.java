package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@Valid @ModelAttribute Product product,
                                    BindingResult result,
                                    Model model) {
        if (result.hasErrors()) {
            // Return back to the create form if validation fails
            return "CreateProduct";
        }
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }

    @GetMapping("/edit")
    public String editProductPage(@RequestParam("id") String productId, Model model) {
        Product product = service.findById(productId);
        if (product == null) {
            return "redirect:/product/list";
        }
        model.addAttribute("product", product);
        return "EditProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@Valid @ModelAttribute Product product,
                                  BindingResult result) {
        if (result.hasErrors()) {
            // Return back to the edit form if validation fails
            return "EditProduct";
        }
        service.update(product);
        return "redirect:list";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id") String productId) {
        service.deleteById(productId);
        return "redirect:/product/list";
    }
}