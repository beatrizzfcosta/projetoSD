package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Long uId;

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getuId() {
        return uId;
    }
}
