package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Entity
@Table(name = "discount_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class DiscountCategoryEntity {
    @Id
    private String id;

    @Column(name = "discount_id")
    private String discountId;

    @Column(name = "category_id")
    private String categoryId;
}
