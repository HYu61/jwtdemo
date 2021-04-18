package pers.hyu.jwtdemo.ui.model.response;

import java.util.List;

public class UserResp {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressResp> addressList;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressResp> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressResp> addressList) {
        this.addressList = addressList;
    }
}
