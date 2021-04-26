package me.zhengjie.modules.keyword.service.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import me.zhengjie.modules.keyword.domain.KeywordfilteringContent;
import me.zhengjie.modules.keyword.service.KeywordfilteringContentService;

public class KeywordListenr extends AnalysisEventListener<KeywordfilteringContent> {

    private KeywordfilteringContentService keywordfilteringContentService;
    private Long id;
    private String url;

    public KeywordListenr(KeywordfilteringContentService keywordfilteringContentService, Long id, String url) {
        this.keywordfilteringContentService = keywordfilteringContentService;
        this.id = id;
        this.url = url;
    }

    @Override
    public void invoke(KeywordfilteringContent keywordfilteringContent, AnalysisContext analysisContext) {
        keywordfilteringContent.setKeywordfilteringId(this.id);
        keywordfilteringContent.setUrl(this.url);
        keywordfilteringContentService.create(keywordfilteringContent);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
