package com.remember5.system.modules.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.remember5.system.SystemApplicationTests;
import com.remember5.system.modules.test.domain.BlogArticle;
import com.remember5.system.modules.test.mapper.BlogArticleMapper;
import com.remember5.system.modules.test.repository.BlogArticleRepository;
import com.remember5.system.modules.test.service.impl.BlogArticleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author wangjiahao
 * @date 2022/12/9 12:19
 */
@Slf4j
class MybatisPlusTests extends SystemApplicationTests {

    @Autowired
    BlogArticleServiceImpl blogArticleService;
    @Autowired
    BlogArticleMapper blogArticleMapper;
    @Autowired
    BlogArticleRepository repository;


    @Test
    public void contextLoads() {
        // select
        blogArticleMapper.selectList(new QueryWrapper<>()).forEach(System.err::println);
        List<BlogArticle> list = blogArticleService.list();
        for (BlogArticle blogArticle : list) {
            log.info("{}", blogArticle);
        }

        // insert
        BlogArticle blogArticle = new BlogArticle();
        blogArticle.setContent("test");
        blogArticle.setTitle("test");
        blogArticle.setCreateDate(new Date());

        BlogArticle blogArticle1 = BlogArticle.builder().content("test1").title("test1").createDate(new Date()).build();

        blogArticleService.save(blogArticle);
        blogArticleService.save(blogArticle1);
        blogArticleService.save(new BlogArticle(null, "test2", "test2", new Date()));

        // update
        blogArticle.setTitle("testtesttest");
        blogArticleService.updateById(blogArticle);

        // delete
        log.info("{}", blogArticle.getId());
        blogArticleService.removeById(blogArticle.getId());


    }


    @Test
    public void test1() {
        // query
//        repository.findAll().forEach(System.err::println);

//        repository.myFind().forEach(System.err::println);
//        repository.myFind(3).forEach(System.err::println);

//        repository.saveEntity(new BlogArticle(null, "provide_entity", "provide_entity", null));


        // save
//        int save = repository.save("provider-title", "provider-content",new Date());
//        System.err.println(save);


    }
}
