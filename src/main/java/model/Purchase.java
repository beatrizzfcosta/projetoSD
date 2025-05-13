package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    private Long purchId;

    public void setPurchId(Long pId) {
        this.purchId = purchId;
    }

    public Long getPurchId() {
        return purchId;
    }
}
