package com.example.memberservicepractice.common.pagination;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class PageDto {

    private int pageCount;  // 참조형으로 변경
    private int startPage;
    private int endPage;
    private int realEnd;
    private boolean prev, next;
    private int total;
    private Criteria criteria;

    public PageDto () {}

    public PageDto (int total, int pageCount, Criteria criteria) {
        this.total = total;
        this.pageCount = pageCount;
        this.criteria = criteria;

        this.endPage = (int)(Math.ceil(criteria.getPageNum() * 1.0 / pageCount)) * pageCount;
        this.startPage = endPage - (pageCount - 1);

        realEnd = (int)(Math.ceil(total * 1.0 / criteria.getAmount()));

        if (endPage > realEnd) {
            endPage = realEnd == 0 ? 1 : realEnd;
        }

        prev = startPage > 1;
        next = endPage < realEnd;
    }

}
