package pers.hyu.jwtdemo.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "addresses")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 5157438670500499234L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 50)
    private String addressId;

    @Column(nullable = false, length = 15)
    private String city;

    @Column(nullable = false, length = 15)
    private String province;

    @Column(nullable = false, length = 15)
    private String country;

    @Column(nullable = false, length = 100)
    private String addressDetail;

    @Column(nullable = false, length = 6)
    private String postalCode;

    @Column(nullable = false, length = 15)
    private String type;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserEntity getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserEntity userDetail) {
        this.userDetail = userDetail;
    }


}
