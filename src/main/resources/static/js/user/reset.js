(function () {
    "use strict";

    let treeviewMenu = $('.app-menu');

    // Toggle Sidebar
    $('[data-toggle="sidebar"]').click(function(event) {
        event.preventDefault();
        $('.app').toggleClass('sidenav-toggled');
    });

    // Activate sidebar treeview toggle
    $("[data-toggle='treeview']").click(function(event) {
        event.preventDefault();
        if(!$(this).parent().hasClass('is-expanded')) {
            treeviewMenu.find("[data-toggle='treeview']").parent().removeClass('is-expanded');
        }
        $(this).parent().toggleClass('is-expanded');
    });

    // Set initial active toggle
    $("[data-toggle='treeview.'].is-expanded").parent().toggleClass('is-expanded');

    //Activate bootstrip tooltips
    $("[data-toggle='tooltip']").tooltip();

})();

let $btn_reset = $("#btn_reset")

$btn_reset.click(function () {
    let username = $("#username").val();
    let realname = $("#realname").val();
    let identity = $("#identity").val();
    let password = $("#password").val();
    let password1 = $("#password1").val();
    if (username!=null&&username!=""&&realname!=null&&realname!=""&&identity!=null&&identity!=""&&password!=null&&password!=""&&password==password1) {
        $.post("/user/reset", {username: username, realname: realname, identity: identity, password: password1}, function(data) {
            if (data == "true"){
                swal("密码重置成功!");
                $("#username").val("");
                $("#realname").val("");
                $("#identity").val("");
                $("#password").val("");
                $("#password1").val("");
            } else {
                swal("重置失败!", "服务器内部错误,请联系管理员解决!", "error");
            }
        })
    }
})