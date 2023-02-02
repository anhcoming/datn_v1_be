package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.product.detail.OptionDto;
import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import com.ws.masterserver.entity.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOptionEntity, String> {

    @Query("SELECT o FROM ProductOptionEntity o WHERE o.productId = :id")
    List<ProductOptionEntity> findByProductId(@Param("id") String id);

    List<ProductOptionEntity> findByProductIdAndActive(String productId, Boolean active);

    @Query(value = "SELECT * FROM product_option po WHERE po.size_id = ?1 AND po.color_id = ?2 AND po.product_id = ?3",nativeQuery = true)
    ProductOptionEntity findBySizeAndColorId(String sizeId, String color,String productId);

    @Query("select DISTINCT new com.ws.masterserver.dto.customer.product.ColorResponse(" +
        "c.id,\n" +
        "c.name)\n" +
        "from ProductOptionEntity po\n" +
        "JOIN ColorEntity c on po.colorId = c.id\n" +
        "where po.sizeId = :sizeId and po.productId = :productId")
    List<ColorResponse> getListColorNameBySize(@Param("sizeId") String sizeId, @Param("productId") String productId);

    @Query("SELECT distinct po.image\n" +
            "from ProductOptionEntity po\n" +
            "where po.productId = ?1")
    List<String> findImageByProductId(String productId);

    @Query("select DISTINCT new com.ws.masterserver.dto.customer.size.response.SizeResponse(" +
            "s.id,\n" +
            "s.name)\n" +
            "from ProductOptionEntity po\n" +
            "JOIN SizeEntity s on po.sizeId = s.id\n" +
            "where po.productId = :productId")
    List<SizeResponse> findListSizeByProductId(@Param("productId") String productId);

    @Query("select new com.ws.masterserver.dto.customer.product.ColorResponse(" +
            "c.id,\n" +
            "c.name)\n" +
            "from ColorEntity c\n" +
            "where c.hex = :hex AND c.active = :active")
    ColorResponse findByHexAndActive(@Param("hex") String hex, @Param("active") boolean active);

    @Query("select new com.ws.masterserver.dto.customer.size.response.SizeResponse(" +
            "s.id,\n" +
            "s.name)\n" +
            "from SizeEntity s\n" +
            "where s.name = :sizeName AND s.active = :active")
    SizeResponse findSizeByName(@Param("sizeName") String sizeName, @Param("active") boolean active);

    @Query("select concat(c.name, ' ', s.name)\n" +
            "from ProductOptionEntity po\n" +
            "left join ColorEntity c on c.id = po.colorId\n" +
            "left join SizeEntity s on s.id = po.sizeId\n" +
            "where po.productId = ?1")
    List<String> findSpecificationDto(String id);

    @Query("select new com.ws.masterserver.dto.admin.product.detail.OptionDto(" +
            "po.id,\n" +
            "po.colorId,\n" +
            "po.sizeId,\n" +
            "po.image,\n" +
            "po.price,\n" +
            "po.qty)\n" +
            "from ProductOptionEntity po\n" +
            "where po.productId = ?1")
    List<OptionDto> findOptionsByProductId(String id);

    @Query("select p from ProductOptionEntity p where p.id = ?1 and p.productId = ?2")
    Optional<ProductOptionEntity> findByIdAndProductId(String id, String productId);
}
