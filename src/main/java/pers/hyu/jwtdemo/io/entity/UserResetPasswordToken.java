package pers.hyu.jwtdemo.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "password_token")
public class UserResetPasswordToken implements Serializable {
    private static final long serialVersionUID = -2410945563422732606L;

    @Id
    @GeneratedValue
    private long id;

    private String token;

    @OneToOne
    private UserEntity userEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
