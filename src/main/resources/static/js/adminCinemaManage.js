var selectedSeats = [];
var column = 10;
var row = 5;
$(document).ready(function() {

    var canSeeDate = 0;

    initUser();
    getCanSeeDayNum();
    getCinemaHalls();

    function initUser() {
        if (sessionStorage.getItem("role") === "admin") {
            $("#left-container-bar").html(
                "<div class=\"nav-user-container\" style=\"margin-bottom: 50px;\">\n" +
                "    <img class=\"avatar-lg\" src=\"/images/defaultAvatar.jpg\" />\n" +
                "    <p class=\"title\">SuperAdmin</p>\n" +
                "</div>\n" +
                "<ul class=\"nav nav-pills nav-stacked\">\n" +
                "    <li role=\"presentation\"><a href=\"/superAdmin/people/manage\"><i class=\"icon-user\"></i> 人员管理</a></li>\n" +
                "    <li role=\"presentation\"><a href=\"/superAdmin/VIPCard/manage\"><i class=\"icon-credit-card\"></i> 会员卡优惠管理</a></li>\n" +
                "    <li role=\"presentation\"><a href=\"/superAdmin/coupon/manage\"><i class=\"icon-tag\"></i> 优惠券管理</a></li>\n" +
                "    <li role=\"presentation\"  class=\"active\"><a href=\"#\"><i class=\"icon-cogs\"></i> 影院管理</a></li>" +
                "    <li role=\"presentation\"><a href=\"/superAdmin/cinema/statistic\"><i class=\"icon-bar-chart\"></i> 影院统计</a></li>\n" +
                "    <li role=\"presentation\"><a href=\"/superAdmin/refund/manage\"><i class=\"icon-backward\"></i> 退票策略管理</a></li>" +
                "</ul>")
        }
    }

    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                renderHall(halls);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderHall(halls){
        $('#hall-card').empty();

        var hallDomStr = "";
        if (sessionStorage.getItem("role") === "admin" ) {
            $("#add-hall-button").remove();
            $("#hall-card-title").after(
                "<button type=\"button\" class=\"btn btn-primary hall-item\" id=\"add-hall-button\" " +
                "style=\"float: right\" data-backdrop=\"static\" data-toggle=\"modal\" " +
                "data-target=\"#hallModal\" >" +
                "<i class=\"icon-plus-sign\"></i> 添加影厅</button>")

            halls.forEach(function (hall) {
                var seat = renderSeatFromHall(hall);
                var hallDom =
                    "<div class='cinema-hall'>" +
                    "<div>" +
                    "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                    "<span class='cinema-hall-size' id='cinema-hall-size-text'>"+ hall.seats[0].length +'*'+ hall.seats.length +"</span>" +
                    "<button type=\"button\" class=\"btn btn-primary hall-edit-item\" data-hall='"+JSON.stringify(hall)+"' " +
                    "id='hall-"+ hall.id +"' style=\"float: right\" data-backdrop=\"static\" data-toggle=\"modal\"> " +
                    "<i class=\"icon-edit-sign\"></i> 修改影厅</button>" +
                    "</div>" +
                    "<div class='cinema-seat'>" + seat +
                    "</div>" +
                    "</div>";
                hallDomStr+=hallDom;
            });
        } else {
            halls.forEach(function (hall) {
                var seat = renderSeatFromHall(hall);
                var hallDom =
                    "<div class='cinema-hall'>" +
                    "<div>" +
                    "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                    "<span class='cinema-hall-size' id='cinema-hall-size-text'>"+ hall.seats[0].length +'*'+ hall.seats.length +"</span>" +
                    "</div>" +
                    "<div class='cinema-seat'>" + seat +
                    "</div>" +
                    "</div>";
                hallDomStr+=hallDom;
            });
        }

        $('#hall-card').append(hallDomStr);
    }

    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#canview-modify-btn').click(function () {
       $("#canview-modify-btn").hide();
       $("#canview-set-input").val(canSeeDate);
       $("#canview-set-input").show();
       $("#canview-confirm-btn").show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#hall-form-btn").click(function () {
        let seats = [];
        let name = $("#hall-name-input").val();
        for(let i=0;i<selectedSeats.length;i++){
            seats.push({"rowIndex": (selectedSeats[i][0] + 1),"columnIndex": (selectedSeats[i][1] + 1)});
        }
        let hallForm = {"name": name,"column": parseInt(column), "row": parseInt(row), "seats":seats};

        postRequest(
            '/hall/add',
            hallForm,
            function (res) {
                if (res.success) {
                    getCinemaHalls();
                    $("#hallModal").modal("hide");
                } else {
                    alert(res.message)
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#hall-edit-form-btn").click(function () {
        let seats = [];
        let name = $("#hall-name-edit-input").val();
        for(let i=0;i<selectedSeats.length;i++){
            seats.push({"rowIndex": (selectedSeats[i][0] + 1),"columnIndex": (selectedSeats[i][1] + 1)});
        }
        let hallForm = {"id": Number($('#hallEditModal')[0].dataset.id), "name": name,"column": parseInt(column), "row": parseInt(row), "seats":seats};

        postRequest(
            '/hall/update',
            hallForm,
            function (res) {
                if (res.success) {
                    getCinemaHalls();
                    $("#hallEditModal").modal("hide");
                } else {
                    alert(res.message)
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#hall-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该影厅吗");
        if (r) {
            deleteRequest(
                '/hall/delete/' + $('#hallEditModal')[0].dataset.id,
                {},
                function (res) {
                    if(res.success){
                        getCinemaHalls();
                        $("#hallEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })

    $(document).on('click','.hall-item',function () {
        $("#hall-column-input").val(column);
        $("#hall-row-input").val(row);
        renderSeatForm(renderNewSeats());
        getInputNumber();
    });

    $(document).on('click','.hall-edit-item',function (e) {
        var hall = JSON.parse(e.target.dataset.hall);
        column = hall.column;
        row = hall.row;
        $("#hall-column-edit-input").val(hall.column);
        $("#hall-row-edit-input").val(hall.row);
        $("#hall-name-edit-input").val(hall.name);
        renderSeatEditForm(renderSeatEditFromHall(hall));
        getInputNumber();
        $('#hallEditModal').modal('show');
        $('#hallEditModal')[0].dataset.id = hall.id;
        console.log(hall);
    });
});


//监控输入数据的变化
function getInputNumber() {
    $("#hall-column-input").bind("input propertychange",function(){
        column = $("#hall-column-input").val();
        renderSeatForm(renderNewSeats());
    });
    $("#hall-row-input").bind("input propertychange",function(){
        row = $("#hall-row-input").val();
        renderSeatForm(renderNewSeats());
    });
    $("#hall-column-edit-input").bind("input propertychange",function(){
        column = $("#hall-column-edit-input").val();
        renderSeatEditForm(renderNewSeats());
    });
    $("#hall-row-edit-input").bind("input propertychange",function(){
        row = $("#hall-row-edit-input").val();
        renderSeatEditForm(renderNewSeats());
    });
}

//将渲染的座位显示出来
function renderSeatForm(seatDomStr) {
    $('#hall-seat-initialize').remove();
    $('#column-input').after(seatDomStr);
}

//将渲染的座位显示出来
function renderSeatEditForm(seatDomStr) {
    $('#hall-seat-initialize').remove();
    $('#column-edit-input').after(seatDomStr);
}

function renderSeatFromHall(hall) {
    var seat = "";
    for(var i =0;i<hall.seats.length;i++){
        var temp = ""
        for(var j =0;j<hall.seats[0].length;j++){
            if (hall.seats[i][j] == 0){
                temp += "<div class='cinema-hall-seat'></div>";
            } else {
                temp += "<div class='cinema-hall-seat-unavailable'></div>";
            }
        }
        seat+= "<div>"+temp+"</div>";
    }
    return seat;
}

//渲染已有的影厅座位
function renderSeatEditFromHall(hall) {
    selectedSeats = [];
    var seat = "";
    for(var i =0;i<hall.seats.length;i++){
        var temp = "";
        for(var j =0;j<hall.seats[0].length;j++){
            var id = "seat" + i + j;
            if (hall.seats[i][j] == 0){
                selectedSeats[selectedSeats.length] = [i, j];
                temp += "<button class='cinema-hall-seat' type=\"button\" id='" + id + "' onclick='seatCancel(\"" + id + "\"," + i + "," + j + ")'></button>";
            } else {
                temp += "<button class='cinema-hall-seat-unavailable' type=\"button\" id='" + id + "' onclick='seatCancel(\"" + id + "\"," + i + "," + j + ")'></button>";
            }
        }
        seat+= "<div>"+temp+"</div>";
    }
    var hallDom =
        "<div class='cinema-hall' id='hall-seat-initialize'>" +
        "<div>" +
        "</div>" +
        "<div class='cinema-seat'>" + seat +
        "</div>" +
        "</div>";

    return hallDom;
}

//根据表单输入的内容渲染座位
function renderNewSeats() {
    selectedSeats = [];
    var hallDomStr = "";
    var seat = "";
    for (var i = 0;i < row ;i++){
        var temp = "";
        for (var j = 0;j < column; j++) {
            var id = "seat" + i + j;
            selectedSeats[selectedSeats.length] = [i, j];
            temp += "<button class='cinema-hall-seat' type=\"button\" id='" + id + "' onclick='seatCancel(\"" + id + "\"," + i + "," + j + ")'></button>";
        }
        seat+= "<div>"+temp+"</div>";
    }
    var hallDom =
        "<div class='cinema-hall' id='hall-seat-initialize'>" +
        "<div>" +
        "</div>" +
        "<div class='cinema-seat'>" + seat +
        "</div>" +
        "</div>";
    hallDomStr += hallDom;

    return hallDomStr;
}

//点击座位所对应的方法。座位class改为红色；若是已选座位，座位class改为绿色
function seatCancel(id, i, j) {
    let seat = $('#' + id);
    if (seat.hasClass("cinema-hall-seat")) {
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-unavailable");

        selectedSeats = selectedSeats.filter(function (value) {
            return value[0] != i || value[1] != j;
        })
    } else {
        seat.removeClass("cinema-hall-seat-unavailable");
        seat.addClass("cinema-hall-seat");

        selectedSeats[selectedSeats.length] = [i, j];
    }

    selectedSeats.sort(function (x, y) {
        var res = x[0] - y[0];
        return res === 0 ? x[1] - y[1] : res;
    });
}