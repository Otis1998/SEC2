$(document).ready(function () {

    $("#login-btn").click(function () {
        login();
    });

    $('#index-password').keypress(function (e) {
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode == 13){
            login();
        }
    });

    function login() {
        var formData = getLoginForm();
        if (!validateLoginForm(formData)) {
            return;
        }

        postRequest(
            '/login',
            formData,
            function (res) {
                if (res.success) {
                    sessionStorage.setItem('username', formData.username);
                    sessionStorage.setItem('id', res.content.id);
                    if (res.content.identity == 2) {
                        sessionStorage.setItem('role', 'manager');
                        window.location.href = "/admin/movie/manage"
                    } else if(res.content.identity == 3){
                        sessionStorage.setItem('role', 'admin');
                        window.location.href = "/superAdmin/people/manage"
                    } else if (sessionStorage.getItem('curUrl') == null) {
                        sessionStorage.setItem('role', 'user');
                        window.location.href = "/index"
                    } else {
                        sessionStorage.setItem('role', 'user');
                        window.location.href = sessionStorage.getItem('curUrl');
                    }
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    }

    function getLoginForm() {
        return {
            username: $('#index-name').val(),
            password: $('#index-password').val()
        };
    }

    function validateLoginForm(data) {
        var isValidate = true;
        if (!data.username) {
            isValidate = false;
            $('#index-name').parent('.input-group').addClass('has-error');
            $('#index-name-error').css("visibility", "visible");
        }
        if (!data.password) {
            isValidate = false;
            $('#index-password').parent('.input-group').addClass('has-error');
            $('#index-password-error').css("visibility", "visible");
        }
        return isValidate;
    }
});