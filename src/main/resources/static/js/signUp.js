$(document).ready(function () {
    getVerify();

    $("#signUp-btn").click(function () {
        register();
    });
    $('#index-verify-code').keypress(function (e) {
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode == 13){
            register();
        }
    });
});

function register() {
    var formData = getSignUpForm();
    if (!validateSignUpForm(formData)) {
        return;
    }

    postRequest(
        '/register',
        formData,
        function (res) {
            if (res.success) {
                // sessionStorage.setItem('role', 'user');
                // sessionStorage.setItem('username', formData.username);
                // sessionStorage.setItem('id', res.content.id);
                alert("注册成功");
                // if (sessionStorage.getItem('curUrl') == null) {
                //     window.location.href = '/index';
                // }
                window.location.href = "/signIn";
            } else {
                alert(res.message);
                getVerify();
            }
        },
        function (error) {
            alert(error);
        });
}

function getSignUpForm() {
    return {
        username: $('#signUp-name').val(),
        password: $('#signUp-password').val(),
        secondPassword: $('#signUp-second-password').val(),
        verifyCode:$("#index-verify-code").val()
    };
}

function validateSignUpForm(data) {
    var isValidate = true;
    if (!data.username || data.username.length < 4 || data.username.length > 10) {
        isValidate = false;
        $('#signUp-name').parent('.input-group').addClass('has-error');
        $('#signUp-name-error').css("visibility", "visible");
    }
    if (!data.password || data.password.length < 6 || data.password.length > 12) {
        isValidate = false;
        $('#signUp-password').parent('.input-group').addClass('has-error');
        $('#signUp-password-error').css("visibility", "visible");
    }

    if (!data.secondPassword) {
        isValidate = false;
        $('#signUp-second-password').parent('.input-group').addClass('has-error');
        $('#signUp-second-password-error').css("visibility", "visible");
        $('#signUp-second-password-error').text("请再次输入密码");
    } else if (data.secondPassword != data.password) {
        isValidate = false;
        $('#signUp-second-password').parent('.input-group').addClass('has-error');
        $('#signUp-second-password-error').css("visibility", "visible");
        $('#signUp-second-password-error').text("两次输入密码不一致");
    }

    return isValidate;
}

function getVerify(){
    $("#imgVerify").attr("src","/getVerify?"+Math.random());
}