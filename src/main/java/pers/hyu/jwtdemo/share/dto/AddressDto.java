package pers.hyu.jwtdemo.share.dto;

import java.io.Serializable;

public class AddressDto implements Serializable {
    private static final long serialVersionUID = -1369199265832820040L;

    private long id;
    private String addressId;
    private String city;
    private String province;
    private String country;
    private String addressDetail;
    private String postalCode;
    private String type;

    private UserDto userDetail;

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

    public UserDto getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDto userDetail) {
        this.userDetail = userDetail;
    }
}
