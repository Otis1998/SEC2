var chargeRecordReady=0;

function showChargeRecord(){
    if(chargeRecordReady==0) {
        var chargeRecordDomStr="<div id='charge-record-div'>\n" +
            "        <table border=\"0\" rules=\"rows\" id=\"record-table\" class=\"table\">\n" +
            "            <thead>\n" +
            "                <tr>\n" +
            "                    <th>充值时间</th>\n" +
            "                    <th>充值金额</th>\n" +
            "                    <th>赠送金额</th>\n" +
            "                    <th>余额</th>\n" +
            "                </tr>\n" +
            "            </thead>\n" +
            "            <tbody id=\"record-list\">\n" +
            "            </tbody>\n" +
            "        </table>\n" +
            "    </div>";
        $("#charge-record-a").after(chargeRecordDomStr);
        getRequest(
            '/vip/getChargeRecord/' + id,
            function (res) {
                chargeRecordReady = 1;
                renderChargeRecord(res.content);
            },
            function (error) {
                alert(error);
            });
    }
    $("#charge-record-div").css("display","");
    $("#charge-record-trigger").text("隐藏消费记录");
    $("#charge-record-trigger").attr("href","javascript:hideChargeRecord()");
}

function hideChargeRecord() {
    $("#charge-record-div").css("display","none");
    $("#charge-record-trigger").text("查看消费记录");
    $("#charge-record-trigger").attr("href","javascript:showChargeRecord()");
}

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