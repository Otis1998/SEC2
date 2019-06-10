let orderList=[];
let listToShow=[];
let numOfPage=0;
let page=0;
//初始化界面，展示所有订单
$(document).ready(function () {
    $('#username').text(sessionStorage.getItem('username'));

    getOrderList();

    function getOrderList() {
        getRequest(
            '/order/get/' + sessionStorage.getItem('id'),
            function (res) {
                orderList=res.content;
                listToShow=orderList;
                renderOrderList();
            },
            function (error) {
                alert(error);
            });
    }
});

function showAllOrder() {
    listToShow=orderList;
    renderOrderList();
}

function showSomeOrder(state) {
    listToShow=[];
    orderList.forEach(function (order) {
        if(order.state=state){
            listToShow.push(order);
        }
    });
    renderOrderList();
}

function renderOrderList() {
    var orderUl=$('#order-list');
    var pageBar=$('#page-bar');
    orderUl.empty();
    page=0;
    pageBar.css("display","none");
    if(listToShow.length==0){//没有电影票：显示暂无电影票，不显示页码
        var pageStr= "<li class='movie-item card'><h4>暂无电影票</h4></li>";
        orderUl.append(pageStr);
    }else{//超过五个订单：分页显示，显示页码
        pageBar.css("display","");
        numOfPage=listToShow.length/5;
        showFiveOrder();
    }
}

//将传入的订单列表显示。需要显示：电影名称、电影海报、影厅名、座位、票的张数、放映时间、预计结束时间、票价、状态(分为可退和不可退)
function showFiveOrder() {
    var orderUl=$('#order-list');
    var lastBtn=$('#last');
    var nextBtn=$('#next');
    var pagePara=$('#page');
    var orderStr="";
    for(var i=page*5;i<listToShow.length&&i<page*5+5;i++){
        var seatStr="";
        for(var item in listToShow[i].seatFormList){
            seatStr+=listToShow[i][item].rowIndex+"排"+listToShow[i][item].columnIndex+"座 ";
        }
        orderStr+="<li class='movie-item card'>" +
        "<img class='movie-img' src='" + (listToShow[i].posterUrl || "../images/defaultAvatar.jpg") + "'/>" +
        "<div class='movie-info'>" +
        "<div class='movie-title'>" +
        "<span class='primary-text'>" + listToShow[i].name + "</span>" +
        "<span class='label'></span>" +
        "<span class='movie-want'></span>" +
        "</div>" +
        "<div class='movie-description dark-text'><span>" + listToShow[i].hallId + "号厅 "+seatStr+"</span></div>" +
        "<div>票数：" + listToShow[i].numOfTicket + " 总价："+listToShow[i].cost+"</div>" +
        "<div style='display: flex'><span>开始时间：" + formatTime(listToShow[i].startTime) + "</span><span style='margin-left: 30px;'>结束时间：" + formatTime(listToShow[i].endTime) + "</span>" +
        "<div class='movie-operation' style='display: "+listToShow[i].state?'':'none'+"'><a onclick='"+cancelOrder(listToShow[i].orderId)+"'>退票</a></div></div>" +
        "</div>"+
        "</li>";
    }
    orderUl.empty();
    orderUl.append(orderStr);
    if(page==0){//第一页
        lastBtn.css("display","none");
    }
    if(page==numOfPage){//最后一页
        nextBtn.css("display","none");
    }
    if(page!=0&&page!=numOfPage){
        lastBtn.css("display","");
        nextBtn.css("display","");
    }
    if(numOfPage>0){
        pagePara.text('第'+(page+1)+'页'+' / '+'共'+(numOfPage+1)+'页');
    }
}

function lastClick() {
    page-=1;
    showFiveOrder();
}

function nextClick() {
    page+=1;
    showFiveOrder();
}

function formatTime(timeStr) {
    var time=new Date(timeStr);
    var year=time.getFullYear();
    var month=("0"+(time.getMonth()+1)).slice(-2);
    var day=("0"+time.getDate()).slice(-2);
    var hour=("0"+time.getHours()).slice(-2);
    var min=("0"+time.getMinutes()).slice(-2);
    var second=("0"+time.getSeconds()).slice(-2);
    return `${year}年${month}月${day}日 ${hour}:${min}:${second}`;
}

function cancelOrder(orderId) {
    getRequest(
        '',//退票的url
        function (res) {
            alert("退票成功");
            location.reload();
        },
        function (error) {
            alert(error);
        });
}