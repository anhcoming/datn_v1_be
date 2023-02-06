package com.ws.masterserver.repository;



import com.ws.masterserver.dto.admin.product.detail.ReviewDto;
import com.ws.masterserver.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    List<ReviewEntity> findByProductIdAndActive(String productId,Boolean active);

    @Query(value = "select count(id) from review where active = true and product_id = ?1",nativeQuery = true)
    Integer countRatingActive(String productId);

    @Query(value = "select distinct trim(concat(coalesce(u.first_name, ''), ' ', coalesce(u.last_name, ''))) " +
            "from review r\n" +
            "join users u on r.user_id = u.id\n" +
            "where r.user_id= ?1 and r.product_id = ?2",nativeQuery = true)
    String getUserNameReview(String userId,String productId);

    @Query(value = "select avg(rating) from review where active = true and product_id = ?1",nativeQuery = true)
    Float avgRating(String productId);


    @Query(value = "SELECT * FROM review r where r.active = true and r.user_id = ?1 and r.product_id = ?2",nativeQuery = true)
    ReviewEntity findByUserIdAndProductId(String userId,String productId);

    boolean existsByUserIdAndProductId(String userId,String productId);

    @Query(value = "SELECT EXISTS (\n" +
            "    SELECT FROM review r\n" +
            "        WHERE\n" +
            "            r.user_id = ?1 AND\n" +
            "            r.product_id  = ?2\n" +
            ")",nativeQuery = true)
    boolean checkExistReview(String userId,String productId);

    @Query(value = "SELECT * FROM review where order_id =?1 and product_id =?2 \n" +
            "and review_id = 'ok'",nativeQuery = true)
    List<ReviewEntity> checkExistReviewV2(String orderId,String productId);


    @Query("select r\n" +
            "from ReviewEntity r\n" +
            "where (r.rating = :rating)\n" +
            "and (r.productId = :productId)")
    Page<ReviewEntity> search(@Param("productId") String productId, @Param("rating") Integer rating,Pageable pageReq);

    @Query("select r\n" +
            "from ReviewEntity r\n" +
            "where (r.productId = :productId)"
    )
    Page<ReviewEntity> search(@Param("productId") String productId,Pageable pageReq);

    @Query("select count(r.id) from ReviewEntity r where r.productId = ?1")
    Optional<Long> countByProductId(String id);

    @Query("SELECT new com.ws.masterserver.dto.admin.product.detail.ReviewDto(\n" +
            "r.id,\n" +
            "r.content,\n" +
            "r.rating,\n" +
            "r.userId,\n" +
            "COALESCE(CONCAT(u.firstName, ' ', u.lastName), ''),\n" +
            "r.createdDate)" +
            "FROM ReviewEntity r\n" +
            "LEFT JOIN UserEntity u ON u.id = r.userId\n" +
            "WHERE r.productId = ?1\n" +
            "ORDER BY r.createdDate DESC")
    List<ReviewDto> findReviewDtosByProductId(String id);
}

