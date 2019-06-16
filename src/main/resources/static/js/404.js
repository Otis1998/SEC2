$(document).ready(function () {
    var role = sessionStorage.getItem('role');
    if (role === 'manager') {
        $("#home-btn").attr("href","/admin/movie/manage");
    }
    else if (role === 'admin') {
        $("#home-btn").attr("href","/superAdmin/people/manage");
    }
});