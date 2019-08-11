package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.HashMap;

public interface ArticleService {

    public HashMap<String, Object> showAllArticles(Integer page, Integer rows);

    public String save(Article article);

    public void delete(String id);

    public HashMap<String,Object> update(Article article);

}
