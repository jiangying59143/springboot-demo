package com.mall.demo.model.blog;


import com.mall.demo.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * 踩
 */
@Entity
public class ArticleThumbsDownCount extends BaseEntity<Long> {

    //踩数
    private Long thumbsDownCount;

    public ArticleThumbsDownCount() {

    }

    public ArticleThumbsDownCount(long thumbsDownCount) {
        this.thumbsDownCount = thumbsDownCount;
    }

    public Long getThumbsDownCount() {
        return thumbsDownCount;
    }

    public void setThumbsDownCount(Long thumbsDownCount) {
        this.thumbsDownCount = thumbsDownCount;
    }
}
