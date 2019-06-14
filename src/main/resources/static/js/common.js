$(document).ready(function () {
    if (sessionStorage.getItem('id') == null) {
        $(".dropdown-menu").html("<li><a href=\"/signIn\">登录</a></li>\n" +
            "                <li><a href=\"/signUp\">注册</a></li>\n");
    }
});