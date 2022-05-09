package me.zhengjie.modules.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wangjiahao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "blog_article")
public class BlogArticle {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * title
     */
    @TableField(value = "title")
    private String title;

    /**
     * content
     */
    @TableField(value = "content")
    private String content;

    /**
     * 时间
     */
    @TableField(value = "create_date")
    private Date createDate;
}