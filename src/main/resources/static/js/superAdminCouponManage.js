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
});
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
    var selectDomStr='<option id="'+id+'">'+name+':'+description+'</option>';
    select.append(selectDomStr);
    return;
}