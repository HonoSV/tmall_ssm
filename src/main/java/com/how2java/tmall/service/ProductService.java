package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);
    void delete(int id);
    Product get(int id);
    void update(Product product);
    List<Product> list(int cid);
    void setFirstProductImage(Product product);

    void fill(List<Category> cs);
    void fill(Category c);
    void fillByRow(List<Category> cs);
}
