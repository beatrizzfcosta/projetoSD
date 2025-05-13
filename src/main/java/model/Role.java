package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    private Long rId;

    @Transient
    private boolean isLogged;

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public Long getrId() {
        return rId;
    }
}
