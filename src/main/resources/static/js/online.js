let $table = $("#table")
let $btn_find = $("#btn_find")

$("#select-type").change(function () {
    let selected = $("#select-type").val();
    if (selected=="classify"){
        window.location.href="/online";
    } else{
        $("#user-input").replaceWith("<input type=\"text\" class=\"form-control\" id=\"user-input\" placeholder=\"请输入详细信息\">");
    }
})

function find(){
    let index = $("#select-type").val();
    let value = $("#user-input").val();
    if (index!=null&&index!=""&&value!=null&&value!="") {
        $.post("/junk/find",{index:index, value:value},function (response) {
            if (response.code == 200){
                $table.bootstrapTable('load', response.data);
            }
        })
    }
}

$btn_find.click(function () {
    find();
})

// 后台数据回复处理
function responseHandler(response) {
    let data = response.data
    $.each(data.rows, function (i, row) {
        row.state = $.inArray(row.id, selections) !== -1
    })
    return data
}