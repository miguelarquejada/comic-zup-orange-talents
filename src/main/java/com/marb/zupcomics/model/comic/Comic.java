package com.marb.zupcomics.model.comic;

import com.marb.zupcomics.model.creator.Creator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_comic")
public class Comic {

    @Id
    @NotNull
    private Long comicId;

    @NotBlank
    private String title;

    private Double price;

    private String isbn;

    @Type(type="text")
    private String description;

    private DayOfWeek discount_day;

    @Transient
    private boolean active_discount;

    @ManyToMany
    @JoinTable(name = "tb_comic_creator",
    joinColumns = @JoinColumn(name = "comic_id"),
    inverseJoinColumns = @JoinColumn(name = "creator_id"))
    private List<Creator> creators;

    public Comic() {
    }

    public Comic(Long comicId, String title, Double price, String isbn, String description, DayOfWeek discount_day, List<Creator> creators) {
        this.comicId = comicId;
        this.title = title;
        this.price = price;
        this.isbn = isbn;
        this.description = description;
        this.discount_day = discount_day;
        this.creators = creators;
    }

    public Long getComicId() {
        return comicId;
    }

    public void setComicId(Long comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        if(isActive_discount())
            this.price = this.price - (this.price * 0.10);
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public DayOfWeek getDiscount_day() {
        return discount_day;
    }

    public void setDiscount_day(DayOfWeek discount_day) {
        this.discount_day = discount_day;
    }

    public List<Creator> getCreators() {
        return creators;
    }

    public void setCreators(List<Creator> creators) {
        this.creators = creators;
    }

    public boolean isActive_discount() {
        return LocalDate.now().getDayOfWeek().equals(this.discount_day);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comic comic = (Comic) o;
        return Objects.equals(comicId, comic.comicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comicId);
    }
}
