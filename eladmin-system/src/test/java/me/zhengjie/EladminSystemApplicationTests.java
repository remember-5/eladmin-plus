package me.zhengjie;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.test.domain.BlogArticle;
import me.zhengjie.modules.test.mapper.BlogArticleMapper;
import me.zhengjie.modules.test.repository.BlogArticleRepository;
import me.zhengjie.modules.test.service.impl.BlogArticleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EladminSystemApplicationTests {

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
            log.info("{}",blogArticle);
        }

        // insert
        BlogArticle blogArticle = new BlogArticle();
        blogArticle.setContent("test");
        blogArticle.setTitle("test");
        blogArticle.setCreateDate(new Date());

        BlogArticle blogArticle1 = BlogArticle.builder().content("test1").title("test1").createDate(new Date()).build();

        blogArticleService.save(blogArticle);
        blogArticleService.save(blogArticle1);
        blogArticleService.save(new BlogArticle(null,"test2","test2",new Date()));

        // update
        blogArticle.setTitle("testtesttest");
        blogArticleService.updateById(blogArticle);

        // delete
        log.info("{}",blogArticle.getId());
        blogArticleService.removeById(blogArticle.getId());



    }


    @Test
    public void test1() {
        // query
//        repository.findAll().forEach(System.err::println);

//        repository.myFind().forEach(System.err::println);
        repository.myFind(3).forEach(System.err::println);

        // TODO 暂时不行，不知道为啥
        repository.saveEntity(new BlogArticle(null,"provide_entity","provide_entity",null));


        // save
//        int save = repository.save("provider-title", "provider-content",new Date());
//        System.err.println(save);


    }


    public static void main(String[] args) {
    }
}

