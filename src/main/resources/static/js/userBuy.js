let order=[];
let listToShow=[];
let numOfPage=0;
let page=0;

function showAllOrder() {
    listToShow=order;
    $("#ready").css("background-color","#ffffff");
    $("#all").css("background-color","rgba(173,173,173,0.5)");
    $("#finished").css("background-color","#ffffff");
    renderOrderList();
}

function showSomeOrder(state) {
    listToShow=[];
    order.forEach(function (order) {
        if(order.state==state){
            listToShow.push(order);
        }
    });
    if(state==0){
        $("#ready").css("background-color","rgba(173,173,173,0.5)");
        $("#all").css("background-color","#ffffff");
        $("#finished").css("background-color","#ffffff");
    }else{
        $("#ready").css("background-color","#ffffff");
        $("#all").css("background-color","#ffffff");
        $("#finished").css("background-color","rgba(173,173,173,0.5)");
    }
    renderOrderList();
}

function renderOrderList() {
    var orderUl=$('#order-list');
    var pageBar=$('#page-bar');
    orderUl.empty();
    page=0;
    pageBar.css("display","none");
    if(listToShow.length==0){//没有电影票：显示暂无电影票，不显示页码
        var noOrder= "<li class='movie-item card' style='padding: 20px'><h4>暂无电影票</h4></li>";
        orderUl.append(noOrder);
        return;
    }else if(listToShow.length>5){//超过五个订单：分页显示，显示页码
        pageBar.css("display","");
    }
    numOfPage=(listToShow.length-listToShow.length%5)/5;
    showFiveOrder();
}

//将传入的订单列表显示。需要显示：电影名称、电影海报、影厅名、座位、票的张数、放映时间、预计结束时间、票价、状态(分为可退和不可退)
function showFiveOrder() {
    var orderUl=$('#order-list');
    var lastBtn=$('#last');
    var nextBtn=$('#next');
    var pagePara=$('#page');
    orderUl.empty();
    for(var i=page*5;i<listToShow.length&&i<page*5+5;i++){
        var seatStr="";
        var seatFormList=listToShow[i].seatFormList;
        for(var item in seatFormList){
            seatStr+=seatFormList[item].rowIndex+"排"+seatFormList[item].columnIndex+"座 ";
        }
        var display=listToShow[i].state?'none':'';
        var orderDomStr="<li class='movie-item card'>" +
        "<img class='movie-img' src='" + (listToShow[i].posterUrl || "../images/defaultAvatar.jpg") + "'/>" +
        "<div class='movie-info'>" +
        "<div class='movie-title'>" +
        "<span class='primary-text'>" + listToShow[i].movieName + "</span>" +
        "<span class='label'></span>" +
        "<span class='movie-want'></span>" +
        "</div>" +
        "<div class='movie-description dark-text'><span>" + listToShow[i].hallId.toFixed(0) + "号厅 "+seatStr+"</span></div>" +
        "<div>票数：" + listToShow[i].numOfTicket.toFixed(0) + " 总价："+listToShow[i].cost.toFixed(2)+"</div>" +
        "<div style='display: flex'><span>开始时间：" + formatTime(listToShow[i].startTime) + "</span><span style='margin-left: 30px;'>结束时间：" + formatTime(listToShow[i].endTime) + "</span>" +
        "<div class='movie-operation' style='display: "+display+"'><span style='margin-left: 30px;'><a onclick='cancelOrder("+listToShow[i].orderId+")'>退票</a></span><span style='margin-left: 30px;'><a onclick='printOrder("+listToShow[i].orderId+")'>出票</a></span></div></div>" +
            "</div>"+
            "</li>";
        orderUl.append(orderDomStr);
    }
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
        '/order/cancel/'+orderId,//退票的url
        function (res) {
            alert("退票成功");
            location.reload();
        },
        function (error) {
            alert(error);
        });
}

function printOrder(orderId) {
    getRequest(
        '/order/print/'+orderId,
        function (res) {
            alert("出票成功");
            location.reload();
        },
        function (error) {
            alert(error);
        });
}