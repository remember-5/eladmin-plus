package me.zhengjie.modules.test.repository;

import me.zhengjie.modules.test.domain.BlogArticle;
import me.zhengjie.modules.test.provider.BlogArticleProvider;
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
     * @SelectProvider @UpdateProvider ...
     * 参数可以传实体，也可以单独传入
     * @return
     */
    @SelectProvider(value = BlogArticleProvider.class,method = "findAll")
    List<BlogArticle> findAll();

    @InsertProvider(value = BlogArticleProvider.class,method = "save")
    void save(String title, String content, Date date);

    @InsertProvider(value = BlogArticleProvider.class,method = "saveEntity")
    void saveEntity(BlogArticle blogArticle);

    /**
     * 可以这样写sql
     * @Select @Insert @Update @Delete...
     * @return
     */
    @Select("SELECT * FROM eladmin.blog_article WHERE 1=1")
    List<BlogArticle> myFind();

    @Select("SELECT * FROM eladmin.blog_article WHERE id = ${id}")
    List<BlogArticle> myFind(int id);

}
