let userList=[];
//初始化界面，展示所有人员
$(document).ready(function () {
    getUserList();
    function getUserList(){
        getRequest(
            '/people/search',
            function(res){
                userList=res.content;
                renderUserList(res.content);
            },
            function(error){
                alert(error);
            });
    }
    $('#user-add-form-btn').click(function(){
        //验证通过后添加新的用户
            
        var userInfo={
            identity:$('#add-identity').val(),
            username:$('#user-add-username-input').val(),
            password:$('#user-add-password-input').val()
        };
        if(!validateUserInfo(userInfo)){
            return;
        }
        postRequest(
            '/people/add',
            userInfo,
            function(res){
                $("#userAddModal").modal('hide');
                if(res.success){
                    alert("添加成功！");
                    window.location.reload()
                }else{
                    alert("添加失败！")
                }
            },
            function(error){
                alert(error)
            });   
    });
});
function updateUser(id){
        var userInfo=[]
        getRequest(
            '/people/getInfo?id='+id,
            function(res){
                userInfo=res.content;
                $('#edit-identity').val(userInfo[0].identity)
                $('#user-edit-username-input').val(userInfo[0].username)
                $('#user-edit-password-input').val(userInfo[0].password)
            },
            function(error){
                alert(error)
            });
        $('#user-edit-form-btn').click(function(){
        //验证通过后修改用户信息
        var userForm={
            id:id,
            identity:$('#edit-identity').val(),
            username:$('#user-edit-username-input').val(),
            password:$('#user-edit-password-input').val()
        };
        if(!validateUserInfo(userForm)){
            return;
        }
        postRequest(
            '/people/update',
            userForm,
            function(res){
                $("#userEditModal").modal('hide');
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

function deleteUser(id){
    if(confirm("是否确认删除？")){
        postRequest(
            '/people/delete?id='+id,
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

function validateUserInfo(data) {
    var isValidate = true;
    if (!data.username || data.username.length < 4 || data.username.length > 10) {
        isValidate = false;
        $('#user-edit-username-input').parent('.form-group').addClass('has-error');
        $('.notice').css("visibility", "visible");
    }
    if (!data.password || data.password.length < 6 || data.password.length > 12) {
        isValidate = false;
        $('#user-edit-password-input').parent('.form-group').addClass('has-error');
        $('.notice').css("visibility", "visible");
    }
    return isValidate;
}
function renderUserList(list){
    for(var i in list){
        var id=list[i].id;
        var username=list[i].username;
        var password=list[i].password;
        var identity='';//身份
        if(list[i].identity==1){
            identity='售票员';
        }else if(list[i].identity==2){
            identity='经理';
        }else if(list[i].identity==3){
            identity='管理员'
        }else{
            identity='观众'
        }
        addInTbody(id,identity,username,password);
    }
}
function addInTbody(id,identity,username,password){
    var UserTableBody=$('#user-list');
    var UserDomStr=
        '<tr>' +
        '<td>'+id+'</td>' +
        '<td>'+identity+'</td>' +
        '<td>'+username+'</td>' +
        '<td>'+password+'</td>' +
        '<td>'+'<button type="button" class="btn btn-primary" id="modify-btn" onclick="updateUser('+id+')" data-backdrop="static" data-toggle="modal" data-target="#userEditModal"><span>修 改</span></button>'+'</td>'+
        '<td>'+'<button type="button" class="btn btn-danger" onclick="deleteUser('+id+')">删 除</button>'+'</td>'
        '</tr>';
    UserTableBody.append(UserDomStr);
    return;
}

