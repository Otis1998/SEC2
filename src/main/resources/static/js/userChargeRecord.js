$(document).ready(function () {
    $('#username').text(sessionStorage.getItem('username'));

    getChargeRecord();

    function getChargeRecord() {
        getRequest(
            '/vip/getChargeRecord/' + sessionStorage.getItem('id'),
            function (res) {
                renderChargeRecord(res.content);
            },
            function (error) {
                alert(error);
            });
    }
});

//list中元素为ChargeRecordVO：chargeTime充值时间、chargeSum充值金额、bonusSum赠送金额、balance余额
function renderChargeRecord(list) {
    var recordTable=$("#record-table");
    var recordList=$("#record-list");
    recordList.empty();
    if(list.length==0){
        recordTable.empty();
        recordTable.text("暂无充值记录");
    }else{
        for(var item in list){
            var recordDomStr=
            '<tr>' +
            '<td>'+formatTime(list[item].chargeTime)+'</td>' +
            '<td>'+list[item].chargeSum+'</td>' +
            '<td>'+list[item].bonusSum+'</td>' +
            '<td>'+list[item].balance+'</td>' +
            '</tr>';
            recordList.append(recordDomStr)
        }
    }
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