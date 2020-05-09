// 后台数据回复处理
function responseHandler(response) {
    let data = response.data
    $.each(data.rows, function (i, row) {
        row.state = $.inArray(row.id, selections) !== -1
    })
    return data
}