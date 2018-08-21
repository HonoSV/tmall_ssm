package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_list")
    public String list(Model model, Page page, int cid){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> list = productService.list(cid);
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + cid);
        Category category = categoryService.get(cid);
        model.addAttribute("c", category);
        model.addAttribute("ps",list);
        return "admin/listProduct";
    }

    @RequestMapping("admin_product_add")
    public String add(Product product){
        Date now = new Date();
        product.setCreateDate(now);
        productService.add(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id){
        int cid = productService.get(id).getCid();
        productService.delete(id);
        return "redirect:admin_product_list?cid=" + cid;
    }

    @RequestMapping("admin_product_edit")
    public String edit(Model model, int id){
        Product product = productService.get(id);
//        Category category = categoryService.get(product.getCid());
//        product.setCategory(category);
        model.addAttribute("p", product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product product){
        productService.update(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }
}
