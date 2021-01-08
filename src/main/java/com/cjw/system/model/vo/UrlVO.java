package com.cjw.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UrlVO {
    private String url;
    private String method;
    private String type;

    @Override
    public String toString() {
        return "UrlVO{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
