let ticketList=[];
//初始化界面，展示所有订单
$(document).ready(function () {
    $('#username').text(sessionStorage.getItem('username'));

    getTicketList();

    function getTicketList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                ticketList=res.content;
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }
});

//所有订单对应的方法，展示所有订单
function renderAllTicket() {
    renderTicketList(ticketList);
}
//展示某种状态的订单，state：0 未完成，1 已完成，2 已失效
function renderTicket(state) {
    let newList=[];
    ticketList.forEach(function (ticket) {
        if(ticket.state==state){
            newList.push(ticket);
        }
    });
    renderTicketList(newList);
}

//将传入的订单列表显示。需要显示：电影名称、影厅名、座位、放映时间、预计结束时间、状态
function renderTicketList(list) {
    var ticketTableBody=$('#ticket-list');
    ticketTableBody.empty();
    if(list.length==0){
        ticketTableBody.text("暂无电影票");
    }else {
        //ticket属性：id 电影票id，userId 用户id，scheduleId 排片id，columnIndex 列号，rowIndex 行号，state 状态，time 创建时间
        for (var i in list) {
            //1.根据list获取scheduleId 座位 状态（需要显示座位 状态）
            var seat = (list[i].rowIndex + 1) + "排" + (list[i].columnIndex + 1) + "座";//座位
            var state="";//状态
            if (list[i].state == "1") {
                state = "已完成";
            } else {
                state = "已失效";
            }
            // console.log(seat);
            //2.根据scheduleId获取影厅号 放映起始时间 和电影名称（需要显示影厅名称 起始时间 电影名称） TODO 改为异步
            getSynRequest(
                '/schedule/' + list[i].scheduleId,
                function (res) {
                    //3.显示
                    addInTBody(res.content,seat,state);
                },
                function (error) {
                    alert(error)
                },
            );
        }
    }
}

function addInTBody(schedule,seat,state) {// TODO 修改style
    var ticketTableBody=$('#ticket-list');
    var ticketDomStr=
        '<tr>' +
        '<td>'+schedule.movieName+'</td>' +
        '<td>'+schedule.hallName+'</td>' +
        '<td>'+seat+'</td>' +
        '<td>'+formatTime(schedule.startTime)+'</td>' +
        '<td>'+formatTime(schedule.endTime)+'</td>' +
        '<td>'+state+'</td>' +
        '</tr>';

    ticketTableBody.prepend(ticketDomStr);
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