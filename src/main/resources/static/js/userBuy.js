let orderList=[];
let numOfPage=0;
let page=0;
//初始化界面，展示所有订单
$(document).ready(function () {
    $('#username').text(sessionStorage.getItem('username'));

    getTicketList();

    function getTicketList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                orderList=res.content;
                renderTicketList(orderList);
            },
            function (error) {
                alert(error);
            });
    }
});

//将传入的订单列表显示。需要显示：电影名称、电影海报、影厅名、座位、票的张数、放映时间、预计结束时间、票价、状态(分为可退和不可退)
function renderTicketList(list) {
    var orderList=$('#order-list');
    var pageBar=$('#page-bar');
    var lastBtn=$('#last');
    var nextBtn=$('#next');
    var pagePara=$('#page');
    orderList.empty();
    page=0;
    if(list.length==0){//没有电影票：显示暂无电影票，不显示页码
        var pageStr= "<li class='movie-item card'><h4>暂无电影票</h4></li>";
        orderList.append(pageStr);
        pageBar.css("display","none");
    }else{//超过五个订单：分页显示，显示页码
        pageBar.css("display","");
        numOfPage=list.length/5;
        showFiveOrder();
    }
}

function showFiveOrder() {
    var orderList=$('#order-list');
    var lastBtn=$('#last');
    var nextBtn=$('#next');
    var pagePara=$('#page');
    var orderStr="";
    for(var i=page*5;i<orderList.length&&i<page*5+5;i++){
        orderStr+="<li class='movie-item card'>" +
        "<img class='movie-img' src='" + (movie.posterUrl || "../images/defaultAvatar.jpg") + "'/>" +
        "<div class='movie-info'>" +
        "<div class='movie-title'>" +
        "<span class='primary-text'>" + movie.name + "</span>" +
        "<span class='label "+(!movie.status ? 'primary-bg' : 'error-bg')+"'>" + (movie.status ? '已下架' : (new Date(movie.startDate)>=new Date()?'未上映':'热映中')) + "</span>" +
        "<span class='movie-want'><i class='icon-heart error-text'></i>" + (movie.likeCount || 0) + "人想看</span>" +
        "</div>" +
        "<div class='movie-description dark-text'><span>" + movie.description + "</span></div>" +
        "<div>类型：" + movie.type + "</div>" +
        "<div style='display: flex'><span>导演：" + movie.director + "</span><span style='margin-left: 30px;'>主演：" + movie.starring + "</span>" +
        "<div class='movie-operation'><a href='/user/movieDetail?id="+ movie.id +"'>详情</a></div></div>" +
        "</div>"+
        "</li>";
    }
    orderList.empty();
    orderList.append(orderStr);
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