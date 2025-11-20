package com.example.inmemoryeventsapi.dominio.model;

/**
 * Clase de dominio para representar parámetros de paginación.
 * Sin dependencias de frameworks externos.
 */
public class Pageable {
    private int pageNumber;
    private int pageSize;
    private String sortBy;
    private String sortDirection;

    public Pageable() {
        this.pageNumber = 0;
        this.pageSize = 10;
    }

    public Pageable(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Pageable(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public int getOffset() {
        return pageNumber * pageSize;
    }
}

