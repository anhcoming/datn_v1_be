<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title><!-- CSS only -->
    <style>
        * {
            font-family: Arial, Helvetica, sans-serif;
        }
    </style>
</head>
<body>
<div id=":uf" class="ii gt" jslog="20277; u014N:xr6bB; 4:W251bGwsbnVsbCxbXV0.">
    <div id=":ue" class="a3s aiL ">
        <div style="line-height:14pt;padding:20px 0px;font-size:14px;max-width:580px;margin:0 auto">
            <div class="adM">
            </div>
            <div style="padding:0 10px;margin-bottom:25px">
                <div class="adM">

                </div>
                <p>Xin chào ${name}</p>
                <p>Cảm ơn Anh/chị đã đặt hàng tại <strong><span class="il">Shoes Store</span></strong>!</p>
                <p>Đơn hàng của Anh/chị đã được tiếp nhận, chúng tôi sẽ nhanh chóng liên hệ với Anh/chị.</p>
            </div>
            <hr>
            <div style="padding:0 10px">

                <table style="width:100%;border-collapse:collapse;margin-top:20px">
                    <thead>
                    <tr>
                        <th style="text-align:left;width:50%;font-size:medium;padding:5px 0">Thông tin mua hàng</th>
                        <th style="text-align:left;width:50%;font-size:medium;padding:5px 0">Địa chỉ nhận hàng</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="padding-right:15px">
                            <table style="width:100%">
                                <tbody>
                                <tr>
                                    <td>${name}</td>
                                </tr>

                                <tr>
                                    <td style="word-break:break-word;word-wrap:break-word"><a
                                                href="mailto:sendmaildatn@gmail.com" target="_blank">${email}</a></td>
                                </tr>

                                <tr>
                                    <td>${phone}</td>
                                </tr>

                                </tbody>
                            </table>
                        </td>
                        <td>
                            <table style="width:100%">
                                <tbody>

                                <tr>
                                    <td>${nameOfRecipient}</td>
                                </tr>

                                <tr>
                                    <td style="word-break:break-word;word-wrap:break-word">
                                        ${address}
                                    </td>
                                </tr>

                                <tr>
                                    <td>${phoneOfRecipient}</td>
                                </tr>

                                </tbody>
                            </table>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <table style="width:100%;border-collapse:collapse;margin-top:20px">
                    <thead>
                    <tr>
                        <th style="text-align:left;width:50%;font-size:medium;padding:5px 0">Phương thức thanh toán</th>
                        <th style="text-align:left;width:50%;font-size:medium;padding:5px 0">Phương thức vận chuyển</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="padding-right:15px">Thanh toán qua ${paymentMethod}</td>
                        <td>
                            Đường bộ<br>
                        </td>
                    </tr>
                    </tbody>
                </table>

            </div>
            <div style="margin-top:20px;padding:0 10px">
                <div style="padding-top:10px;font-size:medium"><strong>Thông tin đơn hàng</strong></div>
                <table style="width:100%;margin:10px 0">
                    <tbody>
                    <tr>
                        <td style="width:50%;padding-right:15px">Mã đơn hàng: ${orderCode}</td>
                        <td style="width:50%">Ngày đặt hàng: ${createDate}</td>
                    </tr>
                    </tbody>
                </table>
                <ul style="padding-left:0;list-style-type:none;margin-bottom:0">
                    <#list product as p>
                        <li>

                            <table style="width:100%;border-bottom:1px solid #e4e9eb">
                                <tbody>
                                <tr>
                                    <td style="width:100%;padding:25px 10px 0px 0" colspan="2">
                                        <div style="float:left;width:80px;height:80px;border:1px solid #ebeff2;overflow:hidden">
                                            <img style="max-width:100%;max-height:100%" src="${p.image}" class="CToWUd"
                                                 data-bit="iit">
                                        </div>
                                        <div style="margin-left:100px">
                                            <a href="">${p.productName}</a>
                                            <p style="color:#678299;margin-bottom:0;margin-top:8px">
                                                ${p.colorName} / ${p.sizeName}
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:70%;padding:5px 0px 25px">
                                        <div style="margin-left:100px">
                                            ${p.price}₫ <span style="margin-left:20px">x ${p.quantity}</span>
                                        </div>
                                    </td>
                                    <td style="text-align:right;width:30%;padding:5px 0px 25px">
                                        ${p.subtotal}₫
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </li>
                    </#list>

                </ul>

                <table style="width:100%;border-collapse:collapse;margin-bottom:50px;margin-top:10px">
                    <tbody>
                    <tr>
                        <td style="width:20%"></td>
                        <td style="width:80%">
                            <table style="width:100%;float:right">
                                <tbody>

                                <tr>
                                    <td style="padding-bottom:10px">Phí vận chuyển:</td>
                                    <td style="font-weight:bold;text-align:right;padding-bottom:10px">
                                        ${shipPrice}₫
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding-bottom:10px">Giảm giá sản phẩm:</td>
                                    <td style="font-weight:bold;text-align:right;padding-bottom:10px">
                                        ${shopPriceDiscount}₫
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding-bottom:10px">Giảm giá vận chuyển :</td>
                                    <td style="font-weight:bold;text-align:right;padding-bottom:10px">
                                        ${shipPriceDiscount}₫
                                    </td>
                                </tr>
                                <tr style="border-top:1px solid #e5e9ec">
                                    <td style="padding-top:10px">Thành tiền</td>
                                    <td style="font-weight:bold;text-align:right;font-size:16px;padding-top:10px">
                                        ${total}₫
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div style="clear:both"></div>

            <div style="padding:0 10px">
                <p style="margin:30px 0"><span style="font-weight:bold">Ghi chú: ${note}</span></p>
            </div>

            <div style="clear:both"></div>
            <div style="padding:0 10px">

                <p style="height:50px">
                  <span style="float:left;margin-top:14px;margin-right:10px">Để kiểm tra trạng thái đơn hàng, Anh/chị vui
                    lòng:
                  </span>
                    <span style="margin-top:25px;float:left">
                        <span style="padding:14px 35px;background:#357ebd">
                            <a href="http://localhost:4200/user/my-order" style="font-size:16px;text-decoration:none;color:#fff" target="_blank"
                               data-saferedirecturl="https://www.google.com/url?q=https://u670255.ct.sendgrid.net/ls/click?upn%3D-2BkNr4bxOrt410gv7KRHhn9yS8-2FoBA3p2OA7n9uM64VfyYkqzvdPGFBhmckxTXuFWlbHl_DOnDKi0m2XZr-2FcFZaXr6vzifgG3P9kLzhcTAoy5vonkd4SQxAviq6F8o41-2FNE8FVcsBrxNEBsLWMAOwhjaoaaw0To3iDNNu8nYV976ojKnmFVpjHDmHtkW-2BbJsNm8-2FcB3tkiPx4TKjwLNjjOxH0ulvcUbeFhvab1yvg-2Bzq21fNDKuA5Azm9MoagB-2BREIccYIZIQT4JhFRF93fFCzKX-2FXDakHvhTCT0PIqzpg5STxV4U-3D&amp;source=gmail&amp;ust=1660542259587000&amp;usg=AOvVaw1TCKqDRJ5jfSjOE5RVHk9p">Đăng nhập vào tài khoản</a>
                        </span>
                    </span>
                </p>

                <div style="clear:both"></div>
                <p style="margin:30px 0">Nếu Anh/chị có bất kỳ câu hỏi nào, xin liên hệ với chúng tôi tại <a
                            href="mailto:sendmaildatn@gmail.com" style="color:#357ebd"
                            target="_blank">sendmaildatn@<span class="il">gmail</span>.com</a></p>
                <p style="text-align:right"><i>Trân trọng,</i></p>
                <p style="text-align:right"><strong>Ban quản trị cửa hàng <span class="il">Shoes Store</span></strong>
                </p>
            </div>
        </div>
        <img src="https://ci4.googleusercontent.com/proxy/D4k0oqx_H0qn7knwlNiPCEeN7jZu5dEzgZVftv3THTSaMP8P5aE3_Bm4bl3mo-cP2rh0LLpZuiIaPZocxWFgq5SsJdTmCSf_jolL_Y_TuHoT8YL0ZQ-yfz3-BFtBv8HL6_lq9L_gwwjxT6NGGauyT2v4NVV4_1fzBvs5PKkxHLtBN1E6KHFTAzrMIASi7uK_ijINISvTBxrQCuH6r_vDvSfKXOQi4n81EFBPKMyYL_qnSOiN1IyR5i5NoCT6wQ7NOq9RFazm-WUrlIcuZphmS1JIZs0XwCgUEPRA1zKI6Co9UdkLOEU7qZsPtWkt2VuLalnZ269tdZ1HgXZjzL2nhV5IMNTNsF7XTvY8Yf27xNEO2-0fBrCTBlXuQSioZ7XmqQ9HWs4QOumAqGLzhj4OMCxpYeuXRo5vvGoo9g=s0-d-e1-ft#https://u670255.ct.sendgrid.net/wf/open?upn=OvuEwfqOj23IwK3XEU542tiaiJchQ7MZuCt4VObd9MRei2VNY7LKBbyTtR2l-2BzlBmZBZIugOEk5TBY8WxCT6m-2Bb5sQWdbihCQgTcPACx4wRBVuhOjegU4Lxt1VvvA3tyr7p9yKs-2BvxhWLwW4wZTmKl3iVWRlI7q-2BNZ23xG0RwtHJ62peiCNm6wDtSwEIsG0eBFR3K6-2FEqRx1HnyZ-2BhUpnAEfr-2BUfQYYABZBfaM0b0fg-3D"
             alt="" width="1" height="1" border="0"
             style="height:1px!important;width:1px!important;border-width:0!important;margin-top:0!important;margin-bottom:0!important;margin-right:0!important;margin-left:0!important;padding-top:0!important;padding-bottom:0!important;padding-right:0!important;padding-left:0!important"
             class="CToWUd" data-bit="iit" jslog="138226; u014N:xr6bB; 53:W2ZhbHNlLDJd">
        <div class="yj6qo"></div>
        <div class="adL">
        </div>
    </div>
</div>
</body>
</html>
