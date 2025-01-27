
package com.courier.courierapp.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RevenueDTO {
    private Long id;
    private BigDecimal amount;
    private Date date;

    private Long packageId;
    private Long company_id;

    // Getters and setters
public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Long getPackageId() { return packageId; }

    public void setPackageId(Long packageId) { this.packageId = packageId; }

    public Long getCompany_id() { return company_id; }

    public void setCompany_id(Long company_id) { this.company_id = company_id; }
}

