package com.eagle.springdome.base;

import java.io.Serializable;

/**
 * Created by Wang Yong on 16-6-16.
 */
@SuppressWarnings("serial")
public abstract class BaseQuery implements Serializable{
    /**
     * 查询的limit
     */
    private Long limit;

    /**
     * 查询的起始位置offset
     */
    private Long offset;

    /**
     * 排序规则
     */
    private String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("BaseQuery");
        sb.append("{limit=").append(limit);
        sb.append(", offset=").append(offset);
        sb.append(", orderBy='").append(orderBy).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
