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
let $btn_find = $("#btn_find")

$btn_find.click(function () {
    let index = $("#select-type").val();
    let value = $("#user-input").val();
    if (index!=null&&index!=""&&value!=null&&value!="") {
        $.post("/topic/find",{index:index, value:value},function (response) {
            if (response.code == 200){
                $table.bootstrapTable('load', response.data);
            }
        })
    }
})

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
            $.post("/topic/deleteMul", {ids: JSON.stringify(ids)}, function (response) {
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
        '<a class="remove" href="javascript:void(0)" title="删除">',
        '<i class="fa fa-trash fa-lg"></i>',
        '</a>'
    ].join('')
}

// 操作栏 操作事件
window.operateEvents = {
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
                $.post("/topic/deleteOne", {id: row.id}, function (response) {
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