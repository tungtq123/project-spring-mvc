package com.example.demo_laptopshop.controller.admin;

import com.example.demo_laptopshop.domain.Product;
import com.example.demo_laptopshop.service.ProductService;
import com.example.demo_laptopshop.service.UploadService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model){
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model){
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping(value = "admin/product/create")
    public String createProductPage(Model model,
                                    @ModelAttribute("newProduct") @Valid Product product,
                                    BindingResult newProductBindingResult,
                                    @RequestParam("productFile") MultipartFile file){

        if(newProductBindingResult.hasErrors()){
            return "admin/product/create";
        }

        String image = this.uploadService.handleSaveUploadFile(file, "product");
        product.setImage(image);
        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProductPage(Model model, @PathVariable long id){
        model.addAttribute("id", id);
        Product product = this.productService.getProductById(id);
        model.addAttribute("newProduct", product);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product product){
        this.productService.deleteProductById(product.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id){
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("id", product.getId());
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id){
        Product product = this.productService.getProductById(id);
        model.addAttribute("newProduct", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateProduct(Model model,
                                    @ModelAttribute("newProduct") @Valid Product product,
                                    BindingResult newProductBindingResult,
                                    @RequestParam("productFile") MultipartFile file){
        // validate
        if(newProductBindingResult.hasErrors()){
            return "admin/product/update";
        }

        Product currentProduct = this.productService.getProductById(product.getId());

        if(currentProduct != null){
            if(!file.isEmpty()){
                String image = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(image);
            }

            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setTarget(product.getTarget());

            this.productService.handleSaveProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }



}
