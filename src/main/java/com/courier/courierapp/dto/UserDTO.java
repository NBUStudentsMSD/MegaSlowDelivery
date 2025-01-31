package com.courier.courierapp.dto;

import com.courier.courierapp.model.Role;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserDTO implements java.io.Serializable {

    private String username;

    private String password;

    @Enumerated
    private Role role;

    private Long company_id;
    private Long office_id;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public Long getOffice_id() {
        return office_id;
    }
}
