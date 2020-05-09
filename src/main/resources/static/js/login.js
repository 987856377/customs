$('.login-content [data-toggle="flip"]').click(function() {
    $('.login-box').toggleClass('flipped');
    return false;
});

let $btn_reset = $("#btn_reset")

$btn_reset.click(function () {
    let username = $("#username").val();
    let realname = $("#realname").val();
    let identity = $("#identity").val();
    let phone = $("#phone").val();
    let password= $("#password").val();
    let password1 = $("#password1").val();
    if (username!=null&&username!=""&&realname!=null&&realname!=""&&identity!=null&&identity!=""&&password!=null&&password!=""&&password1!=null&&password1!=""&&password==password1) {
        $.post("/forget", {username: username, realname: realname, identity: identity,phone: phone, password: password1}, function(response) {
            if (response.code == 200){
                alert("密码重置成功");
                window.location.href="/login";
            } else {
                alert("服务器内部错误或您输入的信息有误");
            }
        })
    }
})