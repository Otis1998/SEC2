$(document).ready(function () {
    $('#username').text(sessionStorage.getItem('username'));
    sessionStorage.setItem('curUrl', '/index');
});