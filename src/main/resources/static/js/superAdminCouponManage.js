let couponList=[]
$(document).ready(function () {
    getCouponList();
    function getCouponList(){
        getRequest(
            '/coupon/searchAllCoupons',
            function(res){
                couponList=res.content;
                renderCouponList(res.content);
            },
            function(error){
                alert(error);
            });
    }
    $('#user-targetAmount-btn').click(function(){
        $('.user-cum-info').remove();
        var cumulative=parseFloat($('#user-targetAmount').val());
        getRequest(
            '/order/getTargetUser?cumulative='+cumulative,
            function(res){
                var cumulativeList=res.content;
                var cumTBody=$('#user-cumulative-list');
                for(var i in cumulativeList){
                    var cumDomStr='<tr class="user-cum-info">'+'<td>'+cumulativeList[i].userId+'</td>'+
                    '<td>'+cumulativeList[i].cumulative+'</td>'+
                    '<td>'+'<button type="button" class="btn btn-primary" onclick="issueCoupon('+cumulativeList[i].userId+')"><span>赠 送</span></button>'+'</td>'+'</tr>';
                    cumTBody.append(cumDomStr);
                }
            },
            function(error){
                alert(error);
            });
    });
});
function issueCoupon(userId){
    var couponId=parseInt($('#select-coupon').val().slice(0,1));
    postRequest(
        '/coupon/issue?userId='+userId+'&couponId='+couponId,
        null,
        function(res){
            if(res.success){
                alert("赠送成功！")
            }else{
                alert("赠送失败！")
            }
        },
        function(error){
            alert(error)
        });
}
function renderCouponList(list){
    for(var i in list){
        var id=list[i].id;
        var name=list[i].name;
        var endTime=list[i].endTime.slice(0,10)
        var description="满"+list[i].targetAmount+"减"+list[i].discountAmount+","+endTime+"到期";
        addInSelect(id,name,description)
    }
}
function addInSelect(id,name,description){
    var select=$('#select-coupon');
    var selectDomStr='<option>'+id+':'+name+'-'+description+'</option>';
    select.append(selectDomStr);
    return;
}