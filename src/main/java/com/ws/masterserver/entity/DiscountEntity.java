package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "discount")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class DiscountEntity {
    @Id
    private String id;

    private String code;

    private String type;

    @Column(name = "type_value")
    private String typeValue;

    /**
     * Áp dụng cho
     */
    //Loại áp dụng: loại sản phẩm / sản phẩm
    @Column(name = "apply_type")
    private String applyType;

    /**
     * Mã giảm giá sẽ được tính 1 lần trên mỗi đơn hàng hay không:(khong tinh th giam gia van chuyen)
     * true: tính theo cả đơn hàng
     * false: Tính theo từng sản phẩm trong đơn hàng
     */
//    private Boolean isApplyAcross;

    /**
     * Loại khách hàng áp dụng
     */

    //mã loại khách hàng áp dụng KM
    @Column(name = "customer_type")
    private String customerType;

    /**
     * giới hạn số lần mã giảm giá được áp dụng hay không?
     */
//    @Column(name = "has_usage_limit")
//    private Boolean hasUsageLimit;

    /**
     * Nếu giới hạn số lần mã giảm giá được áp dụng
     * => Số lần mã giám giá được áp dụng
     */
    @Column(name = "usage_limit")
    private Long usageLimit;

    /**
     * Giói hạn mỗi khách hàng chỉ được sử dụng mã này 1 lần hay không(Kiểm tra bằng email) ?
     */
    @Column(name = "once_per_customer")
    private Boolean oncePerCustomer;

    /**
     * Ngày bắt đầu
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * Ngày kết thúc
     */
    @Column(name = "end_date")
    private Date endDate;

    //Điều kiện áp dụng khuyến mãi
    /**
     * loại điều kiện áp dụng khuyết mãi
     */
    @Column(name = "prerequisite_type")
    private String prerequisiteType;

    /**
     * Giá trị để thỏa màn đièu kiện áp dụng khuyến mãi
     * Tổng giá trị đơn hàng tối thiệu / Số lượng sản phẩm tối thiểu
     */
    @Column(name = "prerequisite_value")
    private String prerequisiteValue;

    /**
     * trạng thái
     * Ngung áp dụng/ đang áp dụng / chua ap dung
     */
    private String status;

    private Boolean deleted;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    private Boolean active;
}
