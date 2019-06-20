var selectedSeats = [];
var scheduleId;
var order = {ticketId: [], couponId: 0};
var coupons = [];
var isVIP = false;
var useVIP = true;
var price = 0;

//加载购票界面，根据scheduleId获取当前的影片信息、影厅信息、已经锁定的座位，并显示。
$(document).ready(function () {
    //根据url得到scheduleId
    scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);

    getInfo();

    // 获取影片信息、影厅信息、已经锁定的座位，并显示
    function getInfo() {
        getRequest(
            '/ticket/get/occupiedSeats?scheduleId=' + scheduleId,
            function (res) {
                if (res.success) {
                    renderSchedule(res.content.scheduleItem, res.content.seats);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});

//初始化界面信息
function renderSchedule(schedule, seats) {
    //将影厅、票价、场次加载到选座阶段和确认订单阶段的div
    $('#schedule-hall-name').text(schedule.hallName);
    $('#order-schedule-hall-name').text(schedule.hallName);
    $('#schedule-fare').text(schedule.fare.toFixed(2));
    $('#order-schedule-fare').text(schedule.fare.toFixed(2));
    $('#schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");
    $('#order-schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");
    price = schedule.fare.toFixed(2);

    //初始化座位信息（选座阶段的div）
    var hallDomStr = "";
    var seat = "";
    for (var i = 0; i < seats.length; i++) {
        var temp = ""
        for (var j = 0; j < seats[i].length; j++) {
            var id = "seat" + i + j

            if (seats[i][j] == 0) {
                // 未选
                temp += "<button class='cinema-hall-seat-choose' id='" + id + "' onclick='seatClick(\"" + id + "\"," + i + "," + j + ")'></button>";
            }
            else if (seats[i][j] == -1) {
                // 不可用
                temp += "<button class='cinema-hall-seat-unavailable'></button>";
            } else {
                // 已选中
                temp += "<button class='cinema-hall-seat-lock'></button>";
            }
        }
        seat += "<div>" + temp + "</div>";
    }
    var hallDom =
        "<div class='cinema-hall'>" +
        "<div>" +
        "<span class='cinema-hall-name'>" + schedule.hallName + "</span>" +
        "<span class='cinema-hall-size'>" + seats.length + '*' + seats[0].length + "</span>" +
        "</div>" +
        "<div class='cinema-seat'>" + seat +
        "</div>" +
        "</div>";
    hallDomStr += hallDom;

    $('#hall-card').html(hallDomStr);
}

//点击座位所对应的方法。若是未选座位，座位class改为绿色，并显示座位号；若是已选座位，座位class改为灰色，并移除座位号，如果座位号全都移除，显示“还未选择座位”
function seatClick(id, i, j) {
    let seat = $('#' + id);
    if (seat.hasClass("cinema-hall-seat-choose")) {
        seat.removeClass("cinema-hall-seat-choose");
        seat.addClass("cinema-hall-seat");

        selectedSeats[selectedSeats.length] = [i, j]
    } else {
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-choose");

        selectedSeats = selectedSeats.filter(function (value) {
            return value[0] != i || value[1] != j;
        })
    }

    selectedSeats.sort(function (x, y) {
        var res = x[0] - y[0];
        return res === 0 ? x[1] - y[1] : res;
    });

    let seatDetailStr = "";
    if (selectedSeats.length == 0) {
        seatDetailStr += "还未选择座位"
        $('#order-confirm-btn').attr("disabled", "disabled")
    } else {
        for (let seatLoc of selectedSeats) {
            seatDetailStr += "<span>" + (seatLoc[0] + 1) + "排" + (seatLoc[1] + 1) + "座</span>";
        }
        $('#order-confirm-btn').removeAttr("disabled");
    }
    $('#seat-detail').html(seatDetailStr);
}

//确认下单对应的方法。确认后，切换显示选座div和确认订单div。
function orderConfirmClick() {
    $('#seat-state').css("display", "none");
    $('#order-state').css("display", "");

    // 1.读取选座阶段的选座信息，将座位号传给后端锁座。
    let seats = [];
    for(let i=0;i<selectedSeats.length;i++){
        seats.push({"rowIndex": selectedSeats[i][0],"columnIndex":  selectedSeats[i][1]});
    }
    let ticketForm = {"userId":parseInt(sessionStorage.getItem('id')),"scheduleId":scheduleId,"seats":seats};
    postRequest(
        '/ticket/lockSeat',
        ticketForm,
        function (res) {
            if (res.success) {
                order.ticketId=res.content;
            }
        },
        function (error) {
            alert("error");
        }
    );
    // 2.填充购票表格，显示票数/座位、总价（电影名、场次、单价已显示）
    var ticketStr = "<div>" + selectedSeats.length + "张</div>";
    for (var item in selectedSeats) {
        ticketStr += "<div>" + (selectedSeats[item][0] + 1) + "排" + (selectedSeats[item][1] + 1) + "座</div>";
    }
    $('#order-tickets').html(ticketStr);
    var total = (price * selectedSeats.length).toFixed(2);
    $('#order-total').text(total);
    $('#order-footer-total').text("总金额： ¥" + total);
    // 3.获取该用户所拥有的优惠券，并加载到选择框
    getRequest(
        '/coupon/'+sessionStorage.getItem('id')+'/get',
        function (res) {
            renderCoupons(res.content);
        },
        function (error) {
            alert(error);
        }
    );
    function renderCoupons(coupon) {
        var couponTicketStr = "";
        var bestCoupon=-1;
        var bestDiscount=0;
        if (coupon.length == 0) {
            $('#order-discount').text("优惠金额：无");
            $('#order-actual-total').text(" ¥" + total);
            $('#pay-amount').html("<div><b>金额：</b>" + total + "元</div>");
        } else {
            for (var item in coupon) {
                //将可用优惠券添加到coupons，并选择优惠最大的优惠券
                if(coupon[item].targetAmount<=total){
                    coupons.push(coupon[item]);
                    couponTicketStr += "<option>满" + coupon[item].targetAmount + "减" + coupon[item].discountAmount + "</option>";
                    if(coupon[item].discountAmount>bestDiscount){
                        bestCoupon=item;
                        bestDiscount=coupon[item].discountAmount;
                    }
                }
            }
            if(bestCoupon>=0) {
                $('#order-coupons').html(couponTicketStr);
                // 4.读取用户优惠券，选择优惠最大的优惠券并显示优惠金额和实付款
                changeCoupon(bestCoupon);
            }else{
                $('#order-discount').text("优惠金额：无");
                $('#order-actual-total').text(" ¥" + total);
                $('#pay-amount').html("<div><b>金额：</b>" + total + "元</div>");
            }
        }
    }
    // 5.获取会员信息，加载到支付模态框
    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            isVIP = res.success;
            useVIP = res.success;
            if (isVIP) {
                $('#member-balance').html("<div><b>会员卡余额：</b>" + res.content.balance.toFixed(2) + "元</div>");
            } else {
                $("#member-pay").css("display", "none");
                $("#nonmember-pay").addClass("active");

                $("#modal-body-member").css("display", "none");
                $("#modal-body-nonmember").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });
    // 6.支付成功后，校验和赠送优惠券信息。
}

function changeCoupon(couponIndex) {
    order.couponId = coupons[couponIndex].id;
    $('#order-coupons')[0].options[couponIndex].selected=true;//tips：1..options[]是DOM元素的属性，jQuery元素无法使用。2.可以用$()[0]或$().get(0)将jQuery元素转换为DOM元素，$(DOM)将DOM元素转换为jQuery元素。
    $('#order-discount').text("优惠金额： ¥" + coupons[couponIndex].discountAmount.toFixed(2));
    var actualTotal = (parseFloat($('#order-total').text()) - parseFloat(coupons[couponIndex].discountAmount)).toFixed(2);
    $('#order-actual-total').text(" ¥" + actualTotal);
    $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
}

//模态框支付方式切换对应的方法。实现会员支付和银行卡支付的切换
function switchPay(type) {
    useVIP = (type == 0);
    if (type == 0) {
        $("#member-pay").addClass("active");
        $("#nonmember-pay").removeClass("active");

        $("#modal-body-member").css("display", "");
        $("#modal-body-nonmember").css("display", "none");
    } else {
        $("#member-pay").removeClass("active");
        $("#nonmember-pay").addClass("active");

        $("#modal-body-member").css("display", "none");
        $("#modal-body-nonmember").css("display", "");
    }
}

//确认支付对应的方法。
function payConfirmClick() {
    if (useVIP) {
        postPayRequest(useVIP);
    } else {
        if (validateForm()) {
            if ($('#userBuy-cardNum').val() === "123123123" && $('#userBuy-cardPwd').val() === "123123") {
                postPayRequest(useVIP);
            } else {
                alert("银行卡号或密码错误");
            }
        }
    }
}

// 若为会员卡支付，扣除会员卡余额；若为银行卡支付，就随便吧。
function postPayRequest(useVIP) {
    // 1.若使用vip支付，
    if(useVIP){
        postRequest(
            '/ticket/vip/buy',
            order,
            function (res) {
                if(res.success==true) {
                    renderNewCoupon(res.content);
                }else {
                    showFailure(res.message);
                }
            },
            function (error) {
                alert(error);
            }
        )
    }else{
        postRequest(
            '/ticket/buy',
            order,
            function (res) {
                if(res.success==true) {
                    renderNewCoupon(res.content);
                }else {
                    showFailure(res.message);
                }
            },
            function (error) {
                alert(error);
            }
        )
    }
    $('#order-state').css("display", "none");
    $('#success-state').css("display", "");
    $('#buyModal').modal('hide');

    //显示用户新获得的优惠券
    function renderNewCoupon(coupons) {
        let successPanel=$('#success-panel');
        if(coupons.length>0){
            let congratulationStr='<div class=\"hint\">恭喜你获得优惠券！</div>';
            successPanel.append(congratulationStr);
            for(var item in coupons){
                let couponStr = '<div class=\"hint\"><strong>满' + coupons[item].targetAmount + "减" + coupons[item].discountAmount + "</strong></div>";
                successPanel.append(couponStr);
            }
        }else{
            successPanel.append('<img src="/images/success.png"/>');
        }
    }

    function showFailure(message) {
        var messageDomStr="<div class=\"hint\">"+message+"</div>";
        $("#success-panel").empty();
        $("#success-panel").append(messageDomStr);
    }
}

//银行卡支付判断输入是否为空
function validateForm() {
    $('#userBuy-cardNum').parent('.form-group').removeClass('has-error');
    $('#userBuy-cardNum-error').css("visibility", "hidden");
    $('#userBuy-cardPwd').parent('.form-group').removeClass('has-error');
    $('#userBuy-cardPwd-error').css("visibility", "hidden");
    var isValidate = true;
    if (!$('#userBuy-cardNum').val()) {
        isValidate = false;
        $('#userBuy-cardNum').parent('.form-group').addClass('has-error');
        $('#userBuy-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userBuy-cardPwd').val()) {
        isValidate = false;
        $('#userBuy-cardPwd').parent('.form-group').addClass('has-error');
        $('#userBuy-cardPwd-error').css("visibility", "visible");
    }
    return isValidate;
}