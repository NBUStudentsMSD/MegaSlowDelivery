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

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
   private Office office;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "employee_type", nullable = false)
    private EmployeeType employeeType;

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }


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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

   public void setOffice(Office office) {
       this.office = office;
   }
}


