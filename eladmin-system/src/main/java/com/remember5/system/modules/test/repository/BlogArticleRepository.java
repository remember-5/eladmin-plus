package com.remember5.system.modules.test.repository;

import com.remember5.system.modules.test.domain.BlogArticle;
import com.remember5.system.modules.test.provider.BlogArticleProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

/**
 * @author wangjihao
 * @date 2020/12/26
 */

public interface BlogArticleRepository {


    /**
     * 参数可以传实体，也可以单独传入
     *
     * @return /
     */
    @SelectProvider(value = BlogArticleProvider.class, method = "findAll")
    List<BlogArticle> findAll();

    /**
     * 保存文章
     *
     * @param title   /
     * @param content /
     * @param date    /
     */
    @InsertProvider(value = BlogArticleProvider.class, method = "save")
    void save(String title, String content, Date date);

    /**
     * 保存实体
     *
     * @param blogArticle /
     */
    @InsertProvider(value = BlogArticleProvider.class, method = "saveEntity")
    void saveEntity(BlogArticle blogArticle);

    /**
     * 可以这样写sql
     *
     * @return /
     */
    @Select("SELECT * FROM eladmin.blog_article WHERE 1=1")
    List<BlogArticle> myFind();

    /**
     * 查找
     *
     * @param id /
     * @return /
     */
    @Select("SELECT * FROM eladmin.blog_article WHERE id = ${id}")
    List<BlogArticle> myFind(int id);

}
