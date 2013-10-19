package org.ua.oblik.domain.beans;

import java.io.Serializable;

/**
 * 
 * @author Anton Bakalets
 */
public class PaginationBean implements Serializable {
    
    private static final int DEFAULT_PAGE_SIZE = 10;
    
    public static final String ASC = "asc";
    
    private int page;
    private int pageSize;
    private String sort;
    private String dir;
    private int totalPages;
    private long dataSize;

    public PaginationBean() {
        page = 1;
        pageSize = DEFAULT_PAGE_SIZE;
    }

    public PaginationBean(int pageSize) {
        this.page = 1;
        this.pageSize = pageSize;
    }

    public PaginationBean(int page, int pageSize, String property, String order) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = property;
        this.dir = order;
    }

    public long getDataSize() {
        return dataSize;
    }

    public void setDataSize(long dataSize) {
        this.dataSize = dataSize;
        this.totalPages = (int)(dataSize / pageSize) + (dataSize % pageSize == 0 ? 0 : 1);
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getTotalPages() {
        return totalPages;
    }
    
    public int getSkipResults() {
        return (page - 1) * pageSize;
    }
    
    public int getMaxResults() {
        return pageSize;
    }
}
