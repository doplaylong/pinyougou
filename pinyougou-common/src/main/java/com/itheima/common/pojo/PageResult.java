package com.itheima.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 27847
 * @version 1.0
 * @date 2019/03/03 01:57
 **/
public class PageResult implements Serializable {
    private Long total;
    private List<?> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
