let presentList=[]
//初始化界面，展示所有优惠策略
$(document).ready(function () {
    getPresentList();
    function getPresentList(){
        getRequest(
            '/present/search',
            function(res){
                presentList=res.content;
                renderUserList(res.content);
            },
            function(error){
                alert(error);
            });
    }
});
function renderUserList(list){
    for(var i in list){
        var id=list[i].id;
        var present='满'+list[i].targetAmount+'送'+list[i].presentAmount;
        var presentTBody=$('#present-list');
        var presentDomStr='<tr>'+'<td>'+id+'</td>'+
                            '<td>'+present+'</td>'+
                            '<td>'+'<button type="button" class="btn btn-primary" id="modify-btn" onclick="" data-backdrop="static" data-toggle="modal" data-target="#userEditModal"><span>修 改</span></button>'+'</td>'+
        '<td>'+'<button type="button" class="btn btn-danger" onclick="">删 除</button>'+'</td>'+
                            '</tr>';
        presentTBody.append(presentDomStr);
    }
}