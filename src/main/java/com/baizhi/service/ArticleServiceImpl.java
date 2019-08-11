package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> showAllArticles(Integer page, Integer rows) {
        List<Article> articles = articleDao.showAllArticles((page - 1) * rows, rows);
        Integer totalcount = articleDao.totalcount();
        HashMap<String, Object> maps = new HashMap<String, Object>();
        Integer total = totalcount % rows ==0?totalcount/rows:totalcount/rows+1;

        maps.put("total",total);
        maps.put("records",totalcount);
        maps.put("page",page);
        maps.put("rows",articles);
        return maps;

    }

    @Override
    public String save(Article article) {
        String uuid = UUIDUtil.getUUID();
        article.setId(uuid);
        article.setStatus("1");
        article.setUp_date(new Date());
        articleDao.save(article);
        return uuid;
    }

    @Override
    public void delete(String id) {
        articleDao.delete(id);
    }

    @Override
    public HashMap<String,Object> update(Article article) {
        HashMap<String, Object> map=new HashMap<String, Object>();
        try {
            articleDao.update(article);
            map.put("success","200");
            map.put("message","修改成功");
        }catch (Exception e){
            e.getStackTrace();
            map.put("success","400");
            map.put("message","修改失败");
        }
        return map;

    }
}
