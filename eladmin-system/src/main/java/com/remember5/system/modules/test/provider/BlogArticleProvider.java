package com.remember5.system.modules.test.provider;

import com.remember5.system.modules.test.domain.BlogArticle;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author wangjihao
 * @date 2020/12/26
 */
public class BlogArticleProvider {

    /**
     * 拼接sql
     *
     * @return /
     */
    public String findAll() {
        return new SQL()
                .SELECT("*")
                .FROM("blog_article")
                .WHERE("1=1").toString();
    }


    /**
     * 拼接参数
     *
     * @param title   /
     * @param content /
     * @param date    /
     * @return /
     */
    public String save(String title, String content, Date date) {
        return new SQL()
                .INSERT_INTO("blog_article")
                .VALUES("title", "${content}")
                .VALUES("content", "${title}")
                .VALUES("create_date", "${date}").toString();
    }

    /**
     * 接收实体
     *
     * @param blogArticle /
     * @return /
     */
    public String saveEntity(BlogArticle blogArticle) {
        return new SQL()
                .INSERT_INTO("blog_article")
                .VALUES("title", "${content}")
                .VALUES("content", "${title}").toString();
    }
}
