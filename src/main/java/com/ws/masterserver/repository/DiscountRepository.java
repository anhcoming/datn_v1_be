package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.discount.DiscountRes;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, String> {
    @Query("select d\n" +
            "from DiscountEntity d\n" +
            "where d.code like :textSearch\n" +
            "and (:status is null or d.status = :status)\n" +
            "and d.deleted = false")
    Page<DiscountEntity> search(@Param("textSearch") String textSearch,
                                @Param("status") String status, Pageable pageReq);

    @Query("select count(o.id)\n" +
            "from OrderEntity o\n" +
            "where o.discountId = ?1")
    Long getUsageNumberNow(String id);

    @Query("select (count(d) > 0) from DiscountEntity d " +
            "where upper(d.code) = upper(:code) and d.deleted = :deleted")
    boolean existsByCodeAndDeleted(@Param("code") String code, @Param("deleted") boolean deleted);

    @Query("select d from DiscountEntity d where d.id = ?1 and d.deleted = ?2")
    DiscountEntity findByIdAndDeleted(String id, boolean b);

    @Query("select d from DiscountEntity d where upper(d.code) = upper(?1) and d.deleted = false and d.status = 'ACTIVE'")
    DiscountEntity findByCode(String discountCode);

    @Query("select d\n" +
            "from DiscountEntity d\n" +
            "where d.status = 'DE_ACTIVE'\n" +
            "and d.deleted = false\n" +
            "and d.endDate is not null\n" +
            "and d.endDate >= ?1")
    List<DiscountEntity> findActiveList4Job(Date date);

    @Query("select count(d.id) > 0\n" +
            "from DiscountEntity d\n" +
            "where d.status = 'DE_ACTIVE'\n" +
            "and d.deleted = false\n" +
            "and d.endDate is not null\n" +
            "and d.endDate >= ?1")
    boolean checkTask4Job(Date date);

    @Query("select new com.ws.masterserver.dto.customer.discount.DiscountRes(\n" +
            "d.id,\n" +
            "d.startDate,\n" +
            "d.endDate,\n" +
            "d.code)\n" +
            "from DiscountCustomerEntity dc\n" +
            "join DiscountEntity d on dc.discountId = d.id\n" +
            "join UserEntity u on dc.userId = u.id\n" +
            "where u.id = :userId and d.status = 'ACTIVE' \n")
    List<DiscountRes> listMyDiscounts(@Param("userId") String userId);

    @Query("select d\n" +
            "from DiscountEntity d\n" +
            "join DiscountCustomerEntity dc on d.id = dc.discountId\n" +
            "join UserEntity u on dc.userId = u.id\n" +
            "where u.id = :userId\n" +
            "and d.status = 'ACTIVE'\n" +
            "and d.deleted = false")
    Page<DiscountEntity> searchMyDiscount(@Param("userId") String userId,Pageable pageReq);

    @Query("select d from DiscountEntity d where d.code = ?1 and d.deleted = ?2")
    DiscountEntity findByCodeAndDeleted(String discountCode, boolean b);

    @Query("select count(o.id) > 0\n" +
            "from OrderEntity o\n" +
            "where o.discountId = ?1 and o.userId = ?2")
    Boolean checkOnePerCustomerByUserId(String discountId, String userId);

    @Query("select d from DiscountEntity d where d.customerType = ?1 and d.status = ?2")
    List<DiscountEntity> findByCustomerTypeAll(String customerType, String status);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select o from DiscountEntity o where o.id = ?1")
    DiscountEntity findByIdLock(String id);
}
