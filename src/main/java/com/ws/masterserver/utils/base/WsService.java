package com.ws.masterserver.utils.base;

import com.ws.masterserver.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WsService {
    public final AddressService addressService;
    public final BlogService blogService;
    public final CategoryService categoryService;
    public final TopicService topicService;
    public final ColorService colorService;
    public final BrandService brandService;
    public final EmailLogService emailLogService;
    public final FavouriteService favouriteService;
    public final OrderService orderService;
    public final OrderDetailService orderDetailService;
    public final OrderStatusService orderStatusService;
    public final ProductOptionService productOptionService;
    public final ProductService productService;
    public final ResetTokenService resetTokenService;
    public final ReviewMediaService reviewMediaService;
    public final ReviewService reviewService;
    public final UserService userService;
    public final CartService cartService;
    public final SizeService sizeService;
    public final SuggestService suggestService;
    public final DashboardService dashboardService;
    public final NotificationService notificationService;
    public final AdminProductService adminProductService;
    public final AdminUserService adminUserService;
    public final AdminUserInfoService adminUserInfoService;
    public final UserInfoService userInfoService;
    public final AdminDiscountService adminDiscountService;
    public final AdminDiscountCategoryService adminDiscountCategoryService;
    public final AdminDiscountCustomerTypeService adminDiscountCustomerTypeService;
    public final AdminDiscountProductService adminDiscountProductService;
    public final OrderDiscountService orderDiscountService;
    public final AdminDiscountDetailService adminDiscountDetailService;
    public final CustomerDetailService customerDetailService;
    public final ProductInfoService productInfoService;
    public final CategoryInfoService categoryInfoService;
    public final CustomerInfoService customerInfoService;
    public final CustomerTypeInfoService customerTypeInfoService;
    public final AdminDiscountTypeService adminDiscountTypeService;
    public final AdminOrderDetailService adminOrderDetailService;
    public final AdminDiscountStatusService adminDiscountStatusService;
    public final AdminOrderDetailServiceV2 adminOrderDetailServiceV2;
    public final AdminCustomerDetailService adminCustomerDetailService;
    public final CheckoutDiscountService checkoutDiscountService;
    public final AdminCategoryDetailService adminCategoryDetailService;
    public final AdminProductRevenueService adminProductRevenueService;
    public final AdminReportOverviewService adminReportOverviewService;
    public final AdminReportTimeTypeService adminReportTimeTypeService;
    public final AdminRevenueDetailReportService adminRevenueDetailReportService;
    public final TypeService typeService;
    public final AdminUserDetailReportService adminUserDetailReportService;
    public final RoleNoAuthService roleNoAuthService;
    public final AdminProductDetailService adminProductDetailService;
    public final FileService fileService;
    public final AdminSizeDetailService adminSizeDetailService;
    public final AdminSizeService adminSizeService;
    public final AdminOrderService adminOrderService;
    public final CustomerReportService customerReportService;
    public final DiscountService discountService;
    public final CheckoutDiscountServiceV2 checkoutDiscountServiceV2;
    public final AdminDiscountRevenueReportService adminDiscountRevenueReportService;
}
