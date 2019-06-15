var id="";
var info={};
var infoReady=0;
var orderReady=0;
var cardReady=0;
//初始化页面时，加载用户的名字和头像，并切入个人信息页
$(document).ready(function () {
    id=sessionStorage.getItem('id');
    getRequest(
        '/getUserInfo/'+id,
        function (res) {
            info=res.content;
            infoReady=1;
            renderLeftBar();
            renderUserInfo();
            infoClick();
        },
        function (error) {
            alert(error);
        }
    );

    function renderLeftBar(){
        if(info.profilePicture!=null){
            $("#profile-picture").attr("src",info.profilePicture);
        }
        if(info.name!=null){
            $("#name").text(info.name);
        }
    }
});

/**
 * 个人信息页相关方法
 */
//切入个人信息页
function infoClick() {
    //判断个人信息是否以获取，为获取则获取并载入页面，以获取则直接显示个人信息页
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

function renderUserInfo() {
    var profilePicture="/images/defaultAvatar.jpg";
    var name=sessionStorage.getItem("username");
    if(info.profilePicture!=null){
        profilePicture=info.profilePicture;
    }
    if(info.name!=null){
        name=info.name;
    }
    //TODO 调整格式
    var userInfoDomStr="<div class='filler-container card col-md-8 col-md-offset-2'>" +
        "<div class='input-group' style='width: 100%'>" +
        "<div>头像：<img src='"+profilePicture+"' class='avatar-lg'></div>" +
        "<div>昵称：<label>"+name+"</label></div>" +
        "<a href='javascript:changeInfo()'>修改资料</a>||" +
        "<a href='javascript:changePassword()'>修改密码</a>" +
        "</div>"+
        "</div>";
    $("#info-div").append(userInfoDomStr);
}

function changeInfo() {

}

function changePassword() {

}

//切入订单页
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

function renderUserOrder() {
    var userOrderDomStr="<div class=\"filler-container card col-md-10 col-lg-offset-1\">\n" +
        "            <div class=\"input-group\" style=\"width: 100%\">\n" +
        "                <h4>我的电影票</h4>\n" +
        "                <button class=\"btn btn-default my-btn\" id=\"all\" type=\"button\" onclick=\"showAllOrder()\">所有订单</button>\n" +
        "                <button class=\"btn btn-default my-btn\" id=\"finished\" type=\"button\" onclick=\"showSomeOrder(1)\">已完成</button>\n" +
        "                <button class=\"btn btn-default my-btn\" id=\"ready\" type=\"button\" onclick=\"showSomeOrder(0)\">即将开始</button>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <ul class=\"movie-on-list col-md-10 col-lg-offset-1 order-list-padding-justify\" id=\"order-list\">\n" +
        "        </ul>\n" +
        "        <div class=\"filler-container card col-md-10 col-lg-offset-1\" id=\"page-bar\" style=\"display: none\">\n" +
        "            <div class=\"input-group\" style=\"width: 100%\">\n" +
        "                <button class=\"btn btn-default\" id=\"last\" type=\"button\" onclick=\"lastClick()\">上一页</button>\n" +
        "                <p id=\"page\"></p>\n" +
        "                <button class=\"btn btn-default\" id=\"next\" type=\"button\" onclick=\"nextClick()\">下一页</button>\n" +
        "            </div>\n" +
        "        </div>";
    $("#order-div").append(userOrderDomStr);
    showAllOrder();
}

//切入卡包页
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

function renderUserCard() {
    var userCardDomStr="<!--非会员-->\n" +
        "    <div class=\"card col-md-8 col-md-offset-2\" id=\"nonmember-card\" style=\"display: none;\">\n" +
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
        "    <div class=\"card col-md-8 col-md-offset-2\" id=\"member-card\" style=\"visibility: hidden;\">\n" +
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
    $("#card-div").append(userCardDomStr);
}

