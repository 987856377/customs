let $table = $("#table")
let $reply_table = $("#reply_table")
let $btn_leave = $("#btn_leave")
let $btn_publish = $("#btn_publish")
let $btn_reply = $("#btn_reply")

$btn_leave.click(function () {
    let username = $("#operator").text();
    if (username==null||username==""){
        window.location.href="/login";
    } else {
        window.location.href="/leave";
    }
})

$btn_publish.click(function () {
    let username = $("#operator").text();
    let title = $("#title").val();
    let content = $("#content").val();
    if (username!=null&&username!=""&&title!=null&&title!=""&&content!=null&&content!=""){
        $.post("/topic/publish", {username: username, title: title, content: content},function (response) {
            if (response.code == 200){
                swal("发布成功!", "您可以查询到本条数据" ,"success");
                window.location.href="/topic";
            } else {
                swal("发布失败!", "服务器内部错误,请联系管理员解决!", "error");
            }
        })
    }
})

$btn_reply.click(function () {
    let username = $("#operator").text();
    if (username==null||username==""){
        window.location.href="/login";
    }
    let id = $("#id").val();
    let content = $("#content").val();
    if (username!=null&&username!=""&&id!=null&&id!=""&&content!=null&&content!=""){
        $.post("/reply/publish", {username: username, id: id, content: content},function (response) {
            if (response.code == 200){
                swal("发布成功!", "您可以查询到本条数据" ,"success");
                $table.bootstrapTable("refresh");
            } else {
                swal("发布失败!", "服务器内部错误,请联系管理员解决!", "error");
            }
        })
    }
})


// 后台数据回复处理
function responseHandler(response) {
    let data = response.data
    $.each(data.rows, function (i, row) {
        row.state = $.inArray(row.id, selections) !== -1
    })
    return data
}

// 操作栏格式化
function operateFormatter(value, row, index) {
    return [
        '<a class="reply" href="javascript:void(0)" title="回复" data-toggle="modal" data-target="#modalTable">',
        '<i class="fa fa-comments-o fa-lg"></i>',
        '</a>  ',
        '<a class="replyList" href="javascript:void(0)" title="回复列表" data-toggle="modal" data-target="#modalTable1">',
        '<i class="fa fa-sort-amount-asc fa-lg"></i>',
        '</a>  '
    ].join('')
}

// 操作栏 操作事件
window.operateEvents = {
    'click .reply': function (e, value, row, index) {
        $("#id").val(row.id);
    },
    'click .replyList':function (e, value, row, index) {
        $.get("/reply/list",{id: row.id} , function (response) {
            if (response.code == 200){
                $reply_table.bootstrapTable('load',response.data);
            }
        })
    }
}