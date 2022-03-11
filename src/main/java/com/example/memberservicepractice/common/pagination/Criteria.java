package com.example.memberservicepractice.common.pagination;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Data
public class Criteria {

    private int pageNum;
    private int amount;
    private String type;
    private String keyword;

    public Criteria (int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

    // 기본은 1페이지에 10개씩
    public Criteria() {
        this(1, 10);
    }

    // UriComponentsBuilder를 이용하여 파라미터 자동 생성
    public String getListLink (int pageNum) { // dto로 이동
         UriComponents uriComponents = UriComponentsBuilder.newInstance()
                 .queryParam("pageNum", pageNum)
                 .queryParam("amount", amount)
                 .queryParam("type", type)
                 .queryParam("keyword", keyword)
                 .build()
                 .encode();
         return uriComponents.toUriString();
    }

    public String[] getTypeArr () {
        return type == null ? new String[]{} : type.split("");
    }

}
