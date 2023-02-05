package com.ws.masterserver.utils.constants;

public enum WsCode {
    OK("200", "Thành công!"),
    CREATED("201", "Thành công!"),
    FORBIDDEN("403", "Không có quyền"),
    UPDATED("200", "Chỉnh sửa thành công !!"),
    REQUIRED_FIELD("400", "Không được để trống %s!"),
    INVALID_LENGTH("400", "Độ dài %s không hợp lệ!"),
    USER_NOT_FOUND("404", "Không tìm thấy người dùng!"),
    CATEGORY_NOT_FOUND("400", "Không tìm thấy danh mục sản phẩm!"),
    CATEGORY_EXISTS_NAME("400", "Tên danh mục đã tồn tại!"),
    COLOR_NOT_FOUND("400", "Không tìm thấy màu sản phẩm!"),
    COLOR_EXISTS_NAME("400", "Tên màu đã tồn tại"),
    COLOR_EXISTS_HEX("400", "Mã màu đã tồn tại"),
    CART_IS_EMPTY("400", "Giỏ hàng không có sản phẩm ! Không thể checkout !"),
    BRAND_NOT_FOUND("400", "Không tìm thấy thương hiệu!"),
    PRODUCT_NOT_FOUND("400", "Không tìm thấy chất liệu sản phẩm!"),
    INTERNAL_SERVER("500", "Hệ thống đang bị gián đoạn! Xin vui lòng thử lại sau"),
    BAD_REQUEST("400", "Dữ liệu không hợp lệ!"),
    MUST_LOGIN("400", "Vui lòng đăng nhập"),
    BLOG_NOT_FOUND("400", "Không tìm thấy bài viết!"),
    SIZE_SUGGEST_NOT_FOUND("400", "Không tìm thấy size phù hợp"),
    NOTIFICATION_NOT_FOUND("400", "Không tìm thấy thông báo"),
    SIZE_EXISTS_NAME("400", "Tên size đã tồn tại"),
    SIZE_EXISTS_CODE("400", "Code size đã tồn tại"),
    SIZE_NOT_FOUND("400", "Size không đã tồn tại"),

    DISCOUNT_NOT_FOUND("400", "Discount không đã tồn tại"),
    PRODUCT_OPTION_NOT_FOUND("400", "Không tìm thấy phân loại sản phẩm"),
    EMAIL_EXISTS("400", "Email đã tồn tại"),
    PHONE_EXISTS_V2("400", "Số điện thoại đã được đăng ký"),
    PAYMENT_NOT_FOUND("400", "Không tìm thấy phương thức thanh toán này"),
    PHONE_EXISTS("400", "SĐT đã tồn tại"),
    DOB_NOT_MORE_NOW("400", "Ngảy sinh không được lớn hơn hiện tại"),
    AGE_MUST_MORE("400", "Tuổi phải lớn hơn"),
    FAVOURITE_NOT_FOUND("400", "Không tìm thấy sản phẩm yêu thích"),
    AGE_MUST_LESS("400", "Tuổi phải nhỏ hơn"),
    REVIEW_NOT_FOUND("400", "Không tìm thấy đánh giá"),
    CART_NOT_FOUND("400", "Giỏ hàng không có sản phẩm ! Không thể checkout !"),
    ADDRESS_NOT_FOUND("400", "Không tìm thấy địa chỉ người dùng !"),
    DONT_CHANGE_YOURSELF("400", "Không được thay đổi chính mình"),
    USER_LOCKED("500", "Tài khoản của bạn đã bị xóa hoặc vô hiệu hóa"),
    NEW_PASS_NOT_SAME_OLD("400", "Mật khẩu mới không được trùng mật khẩu cũ"),
    PASSWORD_WRONG("400", "Mật khẩu không chính xác"),
    PERCENT_MUST_BETWEEN_0_AND_100("400", "Giá trị khuyến mãi phải từ 0 - 100"),
    MUST_SELECT_PREREQUISITE("400", "Vui lòng chọn điều kiện áp dụng"),
    MUST_SELECT_CUSTOMER_TYPE("400", "Vui lòng chọn nhóm khách hàng"),
    MUST_SELECT_DISCOUNT_TYPE("400", "Vui lòng chọn loại khuyến mãi"),
    MUST_SELECT_APPLY_TYPE("400", "Vui lòng chọn loại áp dụng khuyến mãi"),
    DATE_FORMAT_INVALID("400", "Định dạng ngày tháng không hợp lê"),
    DISCOUNT_CODE_EXISTS("400", "Mã khuyến mãi đã tồn tại"),
    NOT_CONTAIN_SPACE("400", "Không được chứa dấu cách"),
    DISCOUNT_TYPE_INVALID("400", "Loại khuyến mãi không hợp lệ"),
    END_TIME_MUST_MORE_START_TIME("400", "Thời gian kết thúc phải lớn hơn thời gian bắt đầu"),
    APPLY_CUSTOMER_TYPE_INVALID("400", "Loại khách hàng áp dụng khuyến mãi không hợp lệ"),
    STATUS_INVALID("400", "Trạng thái không hợp lệ"),
    SESSION_EXPIRED_DATE("500", "Phiên đăng nhập đã hết hạn"),
    ERROR_NOT_FOUND("404", "Không tìm thấy dữ liệu"),
    DISCOUNT_INVALID("400", "Mã khuyến mãi không hợp lệ"),
    MIN_QTY_INVALID("400", "Số lượng sản phẩm tối thiểu chưa thỏa mãn điều kiện"),
    MIN_SALE_INVALID("400", "Tổng giá trị đơn hàng tối thiểu chưa thỏa mãn điều kiện"),
    DISCOUNT_LIMIT_USAGE("400", "Mã khuyến mãi đã hết lượt sử dụng"),
    EMAIL_NOT_BLANK("400", "Email không được để trống"),
    MAX_SEND_OTP("400", "Bạn đã gửi quá 5 lần yêu cầu trong 1 ngày"),
    DISCOUNT_HAS_EXPIRED("400", "Mã khuyến mãi đã hết hạn"),
    DISCOUNT_HAS_USED_CAN_NOT_DELETE("400", "Không thể xóa những mã khuyến mãi đã được sử dụng"),
    RESET_PASSWORD_FAILED("400", "Đổi mật khẩu không thành công"),
    DATA_EMPTY("500", "Dữ liệu trống"),
    EXPORT_FAILED("500", "Xuất file không thành công"),
    NOT_EMPTY_OPTIONS("400", "Không được để trống danh sách phần loại"),
    CAN_NOT_CHANGE_PRODUCT_STATUS_BECAUSE_CATEGORY_NOT_ACTIVE("400", "Không thể thay đổi trạng thái sản phẩm thuộc danh mục đã ngừng hoạt động"),
    FILE_INVALID("400", "File không hợp lệ"),
    FILE_SIZE_OVERLOAD("400", "Dung lượng file vượt quá giới hạn"),
    PRODUCT_NAME_EXISTS("400", "Tên sản phẩm đã tồn tại"),
    ORDER_NOT_FOUND("400","Không tìm thấy đơn hàng"),
    ORDER_HAS_REJECTED_BY_ADMIN("500", "Đơn hàng bị từ chối"),
    ORDER_HAS_CANCELED_BY_CUSTOMER("500", "Khách hàng đã hủy đơn hàng"),
    SHIP_PRICE_UNSATISFACTORY("400", "Phí vận chuyển không thỏa mãn điều kiện"),
    DISCOUNT_HAS_NO_EFFECT_ON_YOUR_ORDER("400", "Mã khuyến mãi không có hiệu lực với đơn hàng của bạn"),
    TYPE_NOT_FOUND("400", "Không tìm thấy loại sản phẩm"),

    DISCOUNT_PENDINGPENDING("400", "Chưa áp dụng"),

    DISCOUNT_PENDINGACTIVE("400","Đang áp dụng"),

    DISCOUNT_PENDINGDE_ACTIVE("400","Ngừng áp dụng");

    private final String code;
    private final String message;

    WsCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
