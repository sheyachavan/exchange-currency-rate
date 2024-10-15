package com.exchange.rates.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "order_details")
@Proxy(lazy = false)
public class Order
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true, nullable = false)
    private Integer orderId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @MapsId
    @JoinColumn(name = "cust_id")
    @JsonManagedReference
    private Customer customer;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "creation_time")
    private Long createTime;

    @Transient
    private Integer waitTime;

    @Transient
    private Integer position;

    public Order()
    {
    }

    public Order(long creationTime, Integer quantity, Customer customer)
    {
        this.createTime = creationTime;
        this.quantity = quantity;
        this.customer = customer;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public Order setOrderId(Integer orderId)
    {
        this.orderId = orderId;
        return this;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public Order setCustomer(Customer customer)
    {
        this.customer = customer;
        return this;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public Order setQuantity(Integer quantity)
    {
        this.quantity = quantity;
        return this;
    }

    public Long getCreateTime()
    {
        return createTime;
    }

    public Order setCreateTime(Long createTime)
    {
        this.createTime = createTime;
        return this;
    }

    public Integer getWaitTime()
    {
        return waitTime;
    }

    public Order setWaitTime(Integer waitTime)
    {
        this.waitTime = waitTime;
        return this;
    }

    public Integer getPosition()
    {
        return position;
    }

    public Order setPosition(Integer position)
    {
        this.position = position;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity && orderId.equals(order.orderId) && createTime.equals(order.createTime);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(orderId, createTime, quantity);
    }


}
