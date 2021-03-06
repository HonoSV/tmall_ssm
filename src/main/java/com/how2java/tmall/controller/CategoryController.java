package com.how2java.tmall.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> cs = categoryService.list();
        int total = (int) new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(HttpSession session, Category category, UploadedImageFile uploadedImageFile) throws IOException{
        categoryService.add(category);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage image = ImageUtil.change2jpg(file);
        ImageIO.write(image, "jpg", file);
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(HttpSession session, int id){
        categoryService.delete(id);
        File file = new File(session.getServletContext().getRealPath("img/category"));
        File destFile = new File(file, id + ".jpg");
        destFile.delete();
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String get(int id, Model model){
        Category c = categoryService.get(id);
        model.addAttribute("c", c);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(HttpSession session, Category category, UploadedImageFile uploadedImageFile) throws IOException{
        categoryService.update(category);
        MultipartFile image = uploadedImageFile.getImage();
        if(null!=image && !image.isEmpty()){
            File file = new File(session.getServletContext().getRealPath("img/category"));
            File destFile = new File(file, category.getId() + ".jpg");
            image.transferTo(destFile);
            BufferedImage img = ImageUtil.change2jpg(destFile);
            ImageIO.write(img, "jpg", destFile);
        }
        return "redirect:admin_category_list";
    }
}
