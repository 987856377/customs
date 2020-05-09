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

let $btn_publish = $("#btn_publish");

$btn_publish.click(function () {
    let username = $("#operator").text();
    let title = $("#title").val();
    let content = $("#content").val();
    if (username!=null&&username!=""&&title!=null&&title!=""&&content!=null&&content!="") {
        $.post("/announce/publish",{username: username, title: title, content: content}, function (response) {
            if (response.code == 200){
                swal("发布成功!", "您可以查询到本条数据" ,"success");
                $("#title").val("");
                $("#content").val("");
            } else {
                swal("发布失败!", "服务器内部错误,请联系管理员解决!", "error");
            }
        })
    }
})