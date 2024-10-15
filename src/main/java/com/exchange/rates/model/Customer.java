package com.exchange.rates.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer",uniqueConstraints = {
        @UniqueConstraint(columnNames = "cust_id") })
@Proxy(lazy = false)
public class Customer
{
    private static final int PREMIUM_CUSTOMER_BORDER = 1000;

    @Id
    @Column(name = "cust_id", unique = true, nullable = false)
    private Integer custId;

    @Column(name = "is_premium", nullable = false)
    private Boolean isPremium;

    @OneToOne(mappedBy = "customer")
    @JsonBackReference
    private Order order;

    public Customer()
    {
    }

    public Customer(Integer custId)
    {
        this.custId = custId;
        this.isPremium = custId < PREMIUM_CUSTOMER_BORDER;
    }

    public Integer getCustId()
    {
        return custId;
    }

    public void setCustId(Integer custId)
    {
        this.custId = custId;
    }

    public Boolean getPremium()
    {
        return isPremium;
    }

    public void setPremium(Boolean premium)
    {
        isPremium = premium;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }
}
