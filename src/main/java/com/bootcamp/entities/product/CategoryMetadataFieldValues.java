package com.bootcamp.entities.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryMetadataFieldValues {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name = "field_values", insertable = false, updatable = false)
    private String fieldValues;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryMetadataFieldId")
    private CategoryMetadataField categoryMetadataField;
}
