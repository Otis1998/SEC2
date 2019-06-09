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

function renderChargeRecord(list) {//TODO
    var recordTable=$("#record-table");
    for(var item in list){
        var recordDomStr="";
        recordTable.append(recordDomStr)
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