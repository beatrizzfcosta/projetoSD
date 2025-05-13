package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    private Long catId;

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getCatId() {
        return catId;
    }
}
