package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExample;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;

    public void setCategory(Product product){
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
    }

    public void setCategory(List<Product> list){
        for(Product each:list){
            setCategory(each);
        }
    }


    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setCategory(product);
        setFirstProductImage(product);
        return product;
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> list = productMapper.selectByExample(example);
        setCategory(list);
        setFirstProductImage(list);
        return list;
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> pis = productImageService.list(product.getId(), ProductImageService.type_single);
        if(!pis.isEmpty()){
            ProductImage pi = pis.get(0);
            product.setFirstProductImage(pi);
        }
    }

    public void setFirstProductImage(List<Product> list) {
        for (Product each:list){
            setFirstProductImage(each);
        }
    }
}
