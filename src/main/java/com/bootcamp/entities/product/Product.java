package com.bootcamp.entities.product;

import com.bootcamp.entities.user.Seller;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String brand;
    private Boolean isReturnable ;
    private Boolean isCancellable ;
    private Boolean isActive ;
    private Boolean isDeleted ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductVariation> variations;

    public void addVariation(ProductVariation variation){
        if(variation != null){
            if(variations == null)
                variations = new HashSet<>();

            variations.add(variation);
            variation.setProduct(this);
        }
    }

}
