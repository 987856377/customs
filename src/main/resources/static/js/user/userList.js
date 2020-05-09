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

let $table = $('#table')
let $btn_delete = $('#btn_delete')
let $btn_update = $("#btn_update")
let selections = []

// 选中单选框
$table.on('check.bs.table uncheck.bs.table ' +
    'check-all.bs.table uncheck-all.bs.table',
    function () {
        $btn_delete.prop('disabled', !$table.bootstrapTable('getSelections').length)
        selections = getIdSelections()
    })
function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'), function (row) {
        return row.id
    })
}

// 删除按钮
$btn_delete.click(function () {
    swal({
        title: "确定删除?",
        text: "删除后将不能恢复,请谨慎操作!",
        type: "warning",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false
    }, function(isConfirm) {
        if (isConfirm) {
            let ids = getIdSelections();
            if (ids==null||ids==""){
                return;
            }
            $.post("/user/deleteMul", {ids: JSON.stringify(ids)}, function (response) {
                if (response.code == 200) {
                    swal("已删除!", "该数据已从您的个人数据中移除!", "success");
                    $table.bootstrapTable('remove', {
                        field: 'id',
                        values: ids
                    })
                    $btn_delete.prop('disabled', true)
                } else {
                    swal("删除失败!", "服务器内部错误,请联系管理员解决!", "error");
                }
            })
        } else {
            swal("已取消!", "该条数据已被保留,您可以再次查看使用!", "error");
        }
    });

})

// 更新按钮
$btn_update.click(function () {
    let id = $("#id").val();
    let username = $("#username").val();
    let realname = $("#realname").val();
    let identity = $("#identity").val();
    let phone = $("#phone").val();
    let mail = $("#mail").val();
    let roles = $("#roles").val();
    if (username!=null&&username!=""&&realname!=null&&realname!=""&&identity!=null&&identity!=""&&phone!=null&&phone!=""&&mail!=null&&mail!="") {
        $.post("/user/update",{id:id, username:username, realname:realname, identity:identity, phone:phone, mail:mail, roles:roles},function (response) {
            if (response.code == 200){
                swal("修改成功!", "该条数据已被成功更新" ,"success");
                $table.bootstrapTable('updateByUniqueId', {
                    id: id,
                    row: {
                        username : username,
                        realname : realname,
                        identity : identity,
                        phone : phone,
                        mail : mail,
                        roles : roles
                    }
                })
                $table.bootstrapTable('refresh');
            }else {
                swal("修改失败!", "服务器内部错误,请联系管理员解决!", "error");
            }
        })
    }
})

// // 分页查询
// function queryParams(params) {
//     return{
//         pageSize:params.limit,
//         pageNumber:params.offset/params.limit+1,
//         total:params.total
//     }
// }

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
        '<a class="edit" href="javascript:void(0)" title="编辑" data-toggle="modal" data-target="#modalTable">',
        '<i class="fa fa-edit fa-lg"></i>',
        '</a>  ',
        '<a class="remove" href="javascript:void(0)" title="删除">',
        '<i class="fa fa-trash fa-lg"></i>',
        '</a>'
    ].join('')
}

// 操作栏 操作事件
window.operateEvents = {
    'click .edit': function (e, value, row, index) {
        $("#id").val(row.id);
        $("#username").val(row.username);
        $("#realname").val(row.realname);
        $("#identity").val(row.identity);
        $("#phone").val(row.phone);
        $("#mail").val(row.mail);
        $("#roles").val(row.roles);
    },
    'click .remove': function (e, value, row, index) {
        swal({
            title: "确定删除?",
            text: "删除后将不能恢复,请谨慎操作!",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            closeOnConfirm: false,
            closeOnCancel: false
        }, function(isConfirm) {
            if (isConfirm) {
                let username = $("#operator").text();
                if (username == row.username) {
                    swal("您不能删除自己哦!");
                    return
                }
                $.post("/user/deleteOne", {id: row.id}, function (response) {
                    if (response.code == 200) {
                        swal("已删除!", "该数据已从您的个人数据中移除!", "success");
                        $table.bootstrapTable('remove', {
                            field: 'id',
                            values: [row.id]
                        })
                    } else {
                        swal("删除失败!", "服务器内部错误,请联系管理员解决!", "error");
                    }
                })
            } else {
                swal("已取消!", "该条数据已被保留,您可以再次查看使用!", "error");
            }
        });
    }
}
