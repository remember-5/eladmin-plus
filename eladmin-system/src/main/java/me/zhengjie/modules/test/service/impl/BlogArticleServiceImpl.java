package me.zhengjie.modules.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.test.domain.BlogArticle;
import me.zhengjie.modules.test.mapper.BlogArticleMapper;
import me.zhengjie.modules.test.service.BlogArticleService;
import org.springframework.stereotype.Service;

/**
 * @author wangjiahao
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {


}



