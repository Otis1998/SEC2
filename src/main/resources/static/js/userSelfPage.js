var id="";
var info={};
var infoReady=0;
var orderReady=0;
var cardReady=0;
//初始化页面时，加载用户的名字和头像，并切入个人信息页
$(document).ready(getInfo());

function getInfo() {
    id = sessionStorage.getItem('id');
    getRequest(
        '/getUserInfo/' + id,
        function (res) {
            info = res.content;
            infoReady = 1;
            renderLeftBar();
            renderUserInfo();
            infoClick();
        },
        function (error) {
            alert(error);
        }
    );
}


/**
 * 个人信息页相关方法
 */
//切入个人信息页：判断个人信息是否已获取，未获取则获取并载入页面，已获取则直接显示个人信息页
function infoClick() {
    if(infoReady==0){
        getRequest(
            '/getUserInfo/'+id,
            function (res) {
                info=res.content;
                renderUserInfo();
            },
            function (error) {
                alert(error);
            }
        );
        infoReady=1;
    }
    $("#info-li").attr("class","active");
    $("#order-li").attr("class","");
    $("#card-li").attr("class","");
    
    $("#info-btn").attr("href","#");
    $("#order-btn").attr("href","javascript:orderClick()");
    $("#card-btn").attr("href","javascript:cardClick()");
    
    $("#info-div").css("display","");
    $("#order-div").css("display","none");
    $("#card-div").css("display","none");
}

function renderLeftBar() {
    if (info.profilePicture != null) {
        $("#profile-picture").attr("src", info.profilePicture);
    }
    if (info.name != null) {
        $("#name").text(info.name);
    } else {
        $("#name").text(sessionStorage.getItem("username"));
    }
}

//加载个人信息页内的个人信息
function renderUserInfo() {
    var profilePicture="/images/defaultAvatar.jpg";
    var name=sessionStorage.getItem("username");
    if(info.profilePicture!=null){
        profilePicture=info.profilePicture;
    }
    if(info.name!=null){
        name=info.name;
    }
    var userInfoDomStr="<div class='filler-container card col-md-8 col-md-offset-2' style='margin-top: 50px;padding: 50px;'>" +
        "<div class='input-group' style='width: 100%;margin-bottom: 30px'>" +
        "<div>头像：<img src='"+profilePicture+"' class='avatar-lg'></div>" +
        "<div>昵称：<label>"+name+"</label></div>" +
        "</div>"+
        "<span style='margin-right: 30px;'><a href='javascript:changeInfo()' id='change-info-a'>修改资料</a></span>" +
        "<span style='margin-right: 30px;'><a href='javascript:changePassword()' id='change-password-a'>修改密码</a></span>" +
        "</div>";
    $("#info-div").empty();
    $("#info-div").append(userInfoDomStr);
}

//修改资料个人信息的相应方法：1.显示修改个人信息模块 2.使修改个人信息连接失效
function changeInfo() {
    var changeInfoDomStr="<div class='filler-container card col-md-8 col-md-offset-2' id='change-info-div'>" +
        "<div>新头像："+
        "<input type='file' accept=\".jpg\" id='image-input'>" +
        "<small>只允许上传jpg格式的头像...</small>"+
        "<div id='image-preview'></div>"+
        "</div>"+
        "<div>新昵称："+
        "<input type='text' id='name-input'>"+
        "</div>"+
        "<button onclick='changeInfoConfirm()'>确认修改</button>"
        "</div>";
    $("#info-div").append(changeInfoDomStr);
    $("#change-info-a").attr("href","#");

    $("#image-input").on("change",function () {
        $("#image-preview").empty();
        var imageList=$("#image-input")[0].files;
        if(imageList.length==0){
            return;
        }
        var windowURL = window.URL || window.webkitURL;
        var src=windowURL.createObjectURL(imageList[0]);
        $("#image-preview").append("<img src="+src+">");
    })
}

//确认修改个人信息的相应方法：1.将头像转换为base64后和名字传给后端保存 2.重新加载页面
function changeInfoConfirm() {
    var profilePircture=info.profilePicture;
    var name=info.name;
    var nameInput=$("#name-input").val();
    if(nameInput!=null&&nameInput!=""){
        name=nameInput;
    }
    var imageList=$("#image-input")[0].files;
    if(imageList.length>0){
        var reader=new FileReader();
        reader.readAsDataURL(imageList[0]);
        reader.onloadend = function(){
            profilePircture=reader.result;
            saveUserInfo();
        }
    }
    function saveUserInfo() {
        var userInfoForm={
            "id":id,
            "profilePicture":profilePircture,
            "name":name
        };
        console.log(profilePircture);
        postRequest(
            '/changeUserInfo',
            userInfoForm,
            function (res) {
                alert("修改成功");
                reloadInfo();
            },
            function (error) {
                alert(error);
            }
        );
    }
}

function reloadInfo() {
    getInfo();
}

//修改密码链接的相应方法：1.显示修改密码模块 2.使修改密码连接失效
function changePassword() {
    var changePasswordDomStr="<div class='filler-container card col-md-8 col-md-offset-2' style='padding: 30px 40px;' id='change-password-div'>" +
        "<div class=\"input-interval\">\n" +
        "                <div class=\"input-group \" id=\"basic-addon2\">\n" +
        "                    <span class=\"input-group-addon\"><i class=\"icon-lock\"></i></span>\n" +
        "                    <input id=\"signUp-password\" type=\"password\" class=\"form-control\" placeholder=\"请输入密码\" aria-describedby=\"basic-addon2\">\n" +
        "                </div>\n" +
        "                <p id=\"signUp-password-error\" class=\"notice\">密码长度应在6-12位内</p>\n" +
        "            </div>\n" +
        "\n" +
        "            <div class=\"input-interval\">\n" +
        "                <div class=\"input-group \" id=\"basic-addon3\">\n" +
        "                    <span class=\"input-group-addon\"><i class=\"icon-lock\"></i></span>\n" +
        "                    <input id=\"signUp-second-password\" type=\"password\" class=\"form-control\" placeholder=\"请再次确认密码\" aria-describedby=\"basic-addon3\">\n" +
        "                </div>\n" +
        "                <p id=\"signUp-second-password-error\" class=\"notice\">请再次输入密码</p>\n" +
        "            </div>" +
        "<button id=\"signUp-btn\" type=\"button\" class=\"btn btn-primary input-interval btn-block\" onclick='changePasswordConfirm()'>确认修改</button>" +
        "</div>";
    $("#info-div").append(changePasswordDomStr);
    $("#change-password-a").attr("href","#");
}

//确认修改密码的相应方法：1.判断两次密码是否合乎规范，不和规范则显示相应理由，合乎规范则传给后端保存 2.再恢复修改密码链接 3.移除修改密码模块
function changePasswordConfirm() {
    //
    var password= $('#signUp-password').val();
    var secondPassword= $('#signUp-second-password').val();
    if (!password || password.length < 6 || password.length > 12) {
        $('#signUp-password').parent('.input-group').addClass('has-error');
        $('#signUp-password-error').css("visibility", "visible");
        return
    }
    if (!secondPassword) {
        $('#signUp-second-password').parent('.input-group').addClass('has-error');
        $('#signUp-second-password-error').css("visibility", "visible");
        $('#signUp-second-password-error').text("请再次输入密码");
        return;
    } else if (secondPassword != password) {
        $('#signUp-second-password').parent('.input-group').addClass('has-error');
        $('#signUp-second-password-error').css("visibility", "visible");
        $('#signUp-second-password-error').text("两次输入密码不一致");
        return;
    }

    var userForm={
        "id":id,
        "identity":"",
        "username":"",
        "password":password
    }

    postRequest(
        '/changePassword',
        userForm,
        function (res) {
            alert("修改成功");
            $("#change-password-div").remove();
            $("#change-password-a").attr("href","javascript:changePassword()");
        },
        function (error) {
            alert(error);
        }
    )
}

//切入订单页：判断订单是否已获取，未获取则获取并载入页面，已获取则直接显示订单页
function orderClick() {
    if(orderReady==0){
        getRequest(
            '/order/get/' + id,
            function (res) {
                order=res.content;
                renderUserOrder();
            },
            function (error) {
                alert(error);
            });
        orderReady=1;
    }
    $("#info-li").attr("class","");
    $("#order-li").attr("class","active");
    $("#card-li").attr("class","");

    $("#info-btn").attr("href","javascript:infoClick()");
    $("#order-btn").attr("href","#");
    $("#card-btn").attr("href","javascript:cardClick()");

    $("#info-div").css("display","none");
    $("#order-div").css("display","");
    $("#card-div").css("display","none");
}

//载入订单信息
function renderUserOrder() {
    var userOrderDomStr="<div class=\"filler-container card col-md-10 col-lg-offset-1\" style='margin-top: 50px;'>\n" +
        "            <div class=\"input-group\" style=\"width: 100%; margin-left: 20px\">\n" +
        "                <h4>我的电影票</h4>\n" +
        "                <button class=\"btn btn-default my-btn\" id=\"all\" type=\"button\" onclick=\"showAllOrder()\">所有订单</button>\n" +
        "                <button class=\"btn btn-default my-btn\" id=\"finished\" type=\"button\" onclick=\"showSomeOrder(1)\">已完成</button>\n" +
        "                <button class=\"btn btn-default my-btn\" id=\"ready\" type=\"button\" onclick=\"showSomeOrder(0)\">即将开始</button>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <ul class=\"movie-on-list col-md-10 col-lg-offset-1 order-list-padding-justify\" id=\"order-list\">\n" +
        "        </ul>\n" +
        "        <div class=\"filler-container card col-md-10 col-lg-offset-1\" id=\"page-bar\" style=\"display: none;\">\n" +
        "            <div class=\"input-group\" style=\"width: 100%\">\n" +
        "                <button class=\"btn btn-default\" id=\"last\" type=\"button\" onclick=\"lastClick()\">上一页</button>\n" +
        "                <p id=\"page\"></p>\n" +
        "                <button class=\"btn btn-default\" id=\"next\" type=\"button\" onclick=\"nextClick()\">下一页</button>\n" +
        "            </div>\n" +
        "        </div>";
    $("#order-div").empty();
    $("#order-div").append(userOrderDomStr);
    showAllOrder();
}

function reloadOrder() {
    orderReady=0;
    orderClick();
}

//切入卡包页：判断卡包是否已获取，未获取则获取并载入页面，已获取则直接显示卡包页
function cardClick() {
    if(cardReady==0){
        getVIP();
        getCoupon();
        renderUserCard();
        cardReady=1;
    }
    $("#info-li").attr("class","");
    $("#order-li").attr("class","");
    $("#card-li").attr("class","active");

    $("#info-btn").attr("href","javascript:infoClick()");
    $("#order-btn").attr("href","javascript:orderClick()");
    $("#card-btn").attr("href","#");

    $("#info-div").css("display","none");
    $("#order-div").css("display","none");
    $("#card-div").css("display","");
}

//载入卡包信息
function renderUserCard() {
    var userCardDomStr="<!--非会员-->\n" +
        "    <div class=\"card col-md-8 col-md-offset-2\" id=\"nonmember-card\" style=\"display: none; margin-top: 50px;\">\n" +
        "        <div class=\"header\">\n" +
        "            <div class=\"title\">会员卡</div>\n" +
        "            <div class=\"state\">非会员</div>\n" +
        "        </div>\n" +
        "        <div class=\"line\">\n" +
        "            <div>成为VIP会员</div>\n" +
        "            <hr/>\n" +
        "        </div>\n" +
        "        <div class=\"info\">\n" +
        "            <div class=\"price\"><b id=\"member-buy-price\"></b>元/张</div>\n" +
        "            <div class=\"description\">享有充值满赠优惠，永久有效</div>\n" +
        "            <button onclick=\"buyClick()\">立即购买</button>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "\n" +
        "    <!--会员-->\n" +
        "    <div class=\"card col-md-8 col-md-offset-2\" id=\"member-card\" style=\"visibility: hidden; margin-top: 50px;\">\n" +
        "        <div class=\"header\">\n" +
        "            <div class=\"title\">会员卡</div>\n" +
        "            <div class=\"state\">VIP</div>\n" +
        "        </div>\n" +
        "        <div class=\"line\">\n" +
        "            <div>会员信息</div>\n" +
        "            <hr/>\n" +
        "        </div>\n" +
        "        <div class=\"info\">\n" +
        "            <div class=\"content\">\n" +
        "                <div class=\"line\">\n" +
        "                    <div><b>会员卡号：</b></div>\n" +
        "                    <div id=\"member-id\"></div>\n" +
        "                </div>\n" +
        "                <div class=\"line\">\n" +
        "                    <div><b>入会日期：</b></div>\n" +
        "                    <div id=\"member-joinDate\"></div>\n" +
        "                </div>\n" +
        "                <div class=\"line\">\n" +
        "                    <div><b>充值优惠：</b></div>\n" +
        "                    <div id=\"member-description\">\n" +
        "                        \n" +
        "                    </div>\n" +
        "                </div>\n" +
        "\n" +
        "                <hr/>\n" +
        "\n" +
        "                <div class=\"line\">\n" +
        "                    <div><b>余额：</b></div>\n" +
        "                    <div id=\"member-balance\"></div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "            <button onclick=\"chargeClick()\">立即充值</button>\n" +
        "        </div>\n" +
        "        <div class=\"line\" id='charge-record-a'>\n" +
        "            <div><a href=\"javascript:showChargeRecord()\" id='charge-record-trigger'>查看充值记录</a></div>\n" +
        "            <hr/>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "\n" +
        "    <!--优惠券-->\n" +
        "    <div class=\"card col-md-8 col-md-offset-2\">\n" +
        "        <div class=\"header\">\n" +
        "            <div class=\"title\">优惠券</div>\n" +
        "        </div>\n" +
        "        <div id=\"coupon-list\"></div>\n" +
        "    </div>";
    $("#card-div").empty();
    $("#card-div").append(userCardDomStr);
}

function reloadCard() {
    cardReady=0;
    chargeRecordReady=0;
    cardClick();
}