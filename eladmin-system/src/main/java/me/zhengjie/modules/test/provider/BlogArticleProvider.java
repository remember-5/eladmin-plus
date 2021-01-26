package me.zhengjie.modules.test.provider;

import cn.hutool.core.date.DateUtil;
import me.zhengjie.modules.test.domain.BlogArticle;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author wangjihao
 * @date 2020/12/26
 */
public class BlogArticleProvider {

    public String findAll(){
        return new SQL()
                .SELECT("*")
                .FROM("blog_article")
                .WHERE("1=1").toString();
    }

    public String save(String title, String content, Date date){
        return new SQL()
                .INSERT_INTO("blog_article")
                .VALUES("title","${content}")
                .VALUES("content","${title}")
                .VALUES("create_date", "${date}" ).toString();
    }

    public String saveEntity(BlogArticle blogArticle){
        String s = new SQL()
                .INSERT_INTO("blog_article")
                .VALUES("title", "${content}")
                .VALUES("content", "${title}").toString();
        System.err.println(s);
        return s;
    }


    public static void main(String[] args) {
        System.err.println(DateUtil.now());
    }

}
