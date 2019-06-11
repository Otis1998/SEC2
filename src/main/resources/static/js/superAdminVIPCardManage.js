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
    $('#present-add-form-btn').click(function(){
        //添加新的优惠策略
            
        var presentForm={
            targetAmount:$('#present-add-targetAmount-input').val(),
            presentAmount:$('#present-add-presentAmount-input').val(),
        };
        postRequest(
            '/present/add',
            presentForm,
            function(res){
                $("#presentAddModal").modal('hide');
                if(res.success){
                    alert("发布成功！");
                    window.location.reload()
                }else{
                    alert("发布失败！")
                }
            },
            function(error){
                alert(error)
            });   
    });
});
function updatePresent(id){
    for(var i=0;i<presentList.length;i++){
        if(presentList[i].id==id){
            $('#present-edit-targetAmount-input').val(presentList[i].targetAmount);
            $('#present-edit-presentAmount-input').val(presentList[i].presentAmount);
        }
    }
    $('#present-edit-form-btn').click(function(){
        var presentForm={
            id:id,
            targetAmount:$('#present-edit-targetAmount-input').val(),
            presentAmount:$('#present-edit-presentAmount-input').val()
        };
        postRequest(
            '/present/update',
            presentForm,
            function(res){
                $("#presentEditModal").modal('hide');
                if(res.success){
                    alert("修改成功！");
                    window.location.reload()
                }else{
                    alert("修改失败！")
                }
            },
            function(error){
                alert(error)
            });
    });
}
function deletePresent(id){
    if(confirm("是否确认删除？")){
        postRequest(
            '/present/delete?id='+id,
            null,
            function(res){
                if(res){
                    alert("删除成功！");
                    window.location.reload()
                }else{
                    alert("删除失败！")
                }
            },
            function(error){
                alert(error)
            });
    }
}
function renderUserList(list){
    for(var i in list){
        var id=list[i].id;
        var present='满'+list[i].targetAmount+'送'+list[i].presentAmount;
        var presentTBody=$('#present-list');
        var presentDomStr='<tr>'+'<td>'+id+'</td>'+
                            '<td>'+present+'</td>'+
                            '<td>'+'<button type="button" class="btn btn-primary" id="modify-btn" onclick="updatePresent('+id+')" data-backdrop="static" data-toggle="modal" data-target="#presentEditModal"><span>修 改</span></button>'+
                            '<button type="button" class="btn btn-danger" onclick="deletePresent('+id+')">删 除</button>'+'</td>'+
                            '</tr>';
        presentTBody.append(presentDomStr);
    }
}