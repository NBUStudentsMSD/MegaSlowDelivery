package com.courier.courierapp.model;

import jakarta.persistence.*;


@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id",nullable = false,unique=true)
    private Users user;

    @OneToOne
    @JoinColumn(name = "office_id", nullable = false, unique = true)
   private Office office;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Users getUser() {
        return user;
    }


    public void setUser(Users user) {
        this.user = user;
    }

    public Office getOffice() {
       return office;
   }

   public void setOffice(Office office) {
       this.office = office;
   }
}


