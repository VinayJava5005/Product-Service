package com.service.product.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity(name = "product")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private Long cost;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductQuantity productQuantity;
}
