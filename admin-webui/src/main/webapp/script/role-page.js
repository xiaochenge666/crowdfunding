//刷新页面
function refreshPage() {
    //获取pageInfo
    let pageInfo = getPageInFromRemote();
    //渲染页面
    renderPage(pageInfo,"#role-table")

}

//获取pageInfo
function getPageInFromRemote() {

    let pageInfo = null;

    $.ajax({
        url:"../role/query",
        type:"post",
        data:{
            pageNo:window.pageNo,
            pageSize:window.pageSize,
            keyword:window.keyword
        },
        async:false,
        success:function (res) {
            console.log(res);
            //查询成功！
            if (res.operationResult === 'SUCCESS') {
                pageInfo = res.queryData;
            }
            //查询失败！
            else {
                layer.msg("查询角色信息失败！")
            }
        }
    });

    return pageInfo;
}
//渲染页面,将表格渲染到id为id的元素上
function renderPage(pageInfo,id) {
    //清空表格原始内容
    $(id).empty();
    $(".pagination").empty();

    //如果没有内容
    let NoDataDom = "<tr style='text-align: center'><td colspan='4'>没有任何数据</td><tr/>";
    if (pageInfo===null||pageInfo===undefined){
        layer.info("无任何信息！");
        $(id).append(NoDataDom);
        return null;
    }

    //没有数据时
    if (pageInfo.list.length===0){
        $(id).append(NoDataDom);
    }


    let row;
    //渲染数据
    pageInfo.list.forEach(function (item) {
        let id_dom = "<td>"+item.id+"</td>";
        let check_box_dom ="<td><input type=\"checkbox\"></td>";
        let name_dom = "<td id=\"td_"+item.id+"\"\">"+item.name+"</td>";
        let btn1 = "<button type=\"button\" class=\"btn btn-success btn-xs\"><i class=\" glyphicon glyphicon-check\"></i></button>";
        let btn2 = "<button type=\"button\" id=\""+item.id+"\" onclick='edit_click(this)' class=\"edit_btn btn btn-primary btn-xs\"><i class=\" glyphicon glyphicon-pencil\"></i></button>";
        let btn3 = "<button type=\"button\" class=\"delete_btn btn btn-danger btn-xs\"><i class=\" glyphicon glyphicon-remove\"></i></button>";
        let col4 = "<td>"+btn1+" "+btn2+" "+btn3+"</td>";
        row = "<tr>"+id_dom+check_box_dom+name_dom+col4+"</tr>"
        $(id).append(row);//追加一行
    });

    //配置分页条
    paginationFun(pageInfo)

    
}

//使用分页插件
function paginationFun(pageInfo) {

    $('.pagination').pagination(pageInfo.total, {
        num_edge_entries: 1, //边缘页数
        num_display_entries: 4, //主体页数
        items_per_page:pageInfo.pageSize , //每页显示1项
        current_page:pageInfo.pageNum-1,
        prev_text: "上一页",
        next_text: "下一页",
        callback:function (index) {
            window.pageNo = index+1;
            refreshPage();
        }
    });
}

//根据关键词进行索引
function queryRoleByKeyword() {
    //获取关键字
    let keyword = $("#keyword").val();
    //初始化页码
    window.pageNo = 1;
    window.pageSize = 5;
    window.keyword = keyword;
    //更新页面
    refreshPage();
}

//显示添加对话框
function showAddDialog() {
    $("#roleAddModal").modal("show");//显示对话框
}
//关闭对话框，这个对话框是bootstrap写的
function closeAddDialog() {
    $("#roleAddModal").modal("hide");
}

//添加按钮触发时
(function() {
    $("#roleSaveBtn").click(function () {
        let name = $("#roleName").val();
        $.ajax({
            url:"../role/add",
            method:"post",
            data: {
                name:name
            },
            async:false,
            success:function (res) {
                if (res.operationResult === 'SUCCESS') {
                    layer.msg("添加成功！");
                    window.pageNo = 99999;
                    refreshPage();//刷新页面
                    return null;
                }else {
                    layer.error("添加失败:"+res.operationMessage);
                }
            }
        });
        //关闭对话框
        closeAddDialog();
    });
})();

//监听编辑按钮
function edit_click(e) {
    // console.log($(e).attr("id"))
    let id = $(e).attr("id");
    let name = $("#td_"+id)[0].innerText;

    //将name回传给更新对话框
    $("#roleName_edit").val(name);
    $("#role_id_edit").val(id);

    //打开更新对话框
    $("#roleEditModal").modal("show");

}

$("#roleEditBtn").click(function () {
    let name = $("#roleName_edit").val();
    let id = $("#role_id_edit").val();
    //发生请求
    $.ajax({
        url:"../role/edit",
        method: "post",
        data:{
            id:id,
            name:name
        },
        success:function (res) {
            if (res.operationResult === 'SUCCESS') {
                layer.msg("更新成功！");
                refreshPage();//刷新页面
                return null;
            }else {
                layer.error("添加失败:"+res.operationMessage);
            }
        }
    });
    $("#roleEditModal").modal("hide");
});


