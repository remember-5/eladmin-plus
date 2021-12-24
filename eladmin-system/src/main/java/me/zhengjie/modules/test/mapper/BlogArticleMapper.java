package me.zhengjie.modules.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.test.domain.BlogArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangjiahao
 */
@Mapper
public interface BlogArticleMapper extends BaseMapper<BlogArticle> {
}