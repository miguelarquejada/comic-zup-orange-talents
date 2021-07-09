package com.marb.zupcomics.model.comic;

import com.marb.zupcomics.model.creator.CreatorContainer;

import java.util.List;

public class ComicItem {
    private Long id;
    private String title;
    private String isbn;
    private String description;
    private List<Price> prices;
    private CreatorContainer creators;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public CreatorContainer getCreators() {
        return creators;
    }

    public void setCreators(CreatorContainer creators) {
        this.creators = creators;
    }
}
