//生成菜单树
function generateTree() {//生成tree
    
    //zTree相关配置
    let setting = {
        data:{
            key:{
                url:"##"//防止点击节点后跳转
            }
        },
        view: {
            addDiyDom: addDiyDom,
            addHoverDom:addHoverDom,
            removeHoverDom:removeHoverDom
        }
    };
    //异步获取数据
    let nodeData = queryAll();
    //拿到数据后开始初始化
    $.fn.zTree.init($("#treeDemo"), setting, nodeData);
}
//发送请求查询出所有的菜单项
function queryAll() {
    let queryData=null;
    $.ajax({
        url:'../menu/queryAll',
        method:'get',
        async:false,
        success:function (res) {
            if (res.operationResult==='SUCCESS'){
                queryData = res.queryData;
            }else {
                layer.msg("查询失败！")
            }
            // console.log(res)
        }
    });

    return queryData;
}
//自定义icon
function addDiyDom(treeId,treeNode) {
    console.log(treeId,treeNode);
    $("#"+treeNode.tId+"_ico").removeClass().addClass(treeNode.icon);
}
//当鼠标移入节点时触发
function addHoverDom(treeId,treeNode) {
    let tool_bar_exist = $("#" + treeNode.tId + '_bar').length !== 0;
    //如果已经存在，则不做处理
    if (tool_bar_exist){
        return null;
    }
    //定义tool_bar相关的dom元素
    let addBtn = '<span id="addBtn" class="btn btn-success dropdown-toggle btn-xs")"><i class="fa fa-fw fa-plus rbg "></i></span>';
    let deleteBtn = '<span id="deleteBtn" class="btn btn-danger  dropdown-toggle btn-xs"><i class="fa fa-fw fa-times rbg "></i></span>';
    let editBtn = '<span id="editBtn" class="btn btn-warning dropdown-toggle btn-xs"><i class="fa fa-fw fa-edit rbg"></i></span>';

    let level = treeNode.level;//菜单级别
    let tool_bar = '';//按钮栏
    if (level===0){
        //根级别，仅有添加按钮
        tool_bar = addBtn;
    } else if (level===1){
        //具有增加，编辑，除此之外。如果没有孩子那么就可以删除
        tool_bar = addBtn+' '+editBtn ;

        if (treeNode.children.length===0){
            tool_bar = tool_bar +' '+deleteBtn;
        }
    } else {
        tool_bar = editBtn+" "+deleteBtn;
    }
    $("#"+treeNode.tId+'_span').after('<span id="'+treeNode.tId+'_bar">'+' '+tool_bar+'</span>');

    //将pid绑定到全局对象上
    window.id = treeNode.id;
    window.pid = treeNode.pid;
    window.url = treeNode.url;
    window.name = treeNode.name;
    window.icon = treeNode.icon;

    //注意！！！ 该函数必须放在以上代码之下  //为按钮绑定响应事件
    $("#addBtn").on('click',onClickAddBtn);//添加时
    $("#deleteBtn").on('click',onClickDeleteBtn);//删除时
    $("#editBtn").on('click',onClickEditBtn);//编辑时





}
//当鼠标从节点上移除时触发
function removeHoverDom (treeId,treeNode) {
    $("#" + treeNode.tId + '_bar').remove();
}

//点击添加按钮时触发
function onClickAddBtn() {
    //打开模态框
    $("#menuAddModal").modal('show');
    //为提交按钮绑定事件
    $('#menuSaveBtn').on('click',function () {
        //获取相关参数
        let pid = window.id;
        let name = $('#menuAddModal [name = "name"]').val();
        let url = $('#menuAddModal [name = "url"]').val();
        let icon = $('#menuAddModal [name ="icon"]:checked').val();
        let param = {
            pid:pid,
            name:name,
            url:url,
            icon:icon
        };
        //提交请求
        $.ajax({
            url:"../menu/do/add",
            data: JSON.stringify(param),
            contentType:"application/json;charset=UTF-8",
            method: "post",
            success:function (res) {
                if (res.operationResult === 'FAILED'){
                    layer.msg(res.operationMessage);
                    return;
                }
                layer.msg("提交成功！")
            }

        });
        //隐藏模态框
        $("#menuAddModal").modal('hide');
        //重新生成菜单树
        generateTree();
    })
}
//编辑时触发
function onClickEditBtn() {
    //获取原始数据
    let nameDom = $('#menuEditModal [name = "name"]');
    nameDom.val(window.name);
    let urlDom = $('#menuEditModal [name = "url"]');
    urlDom.val(window.url);
    let selector = '#menuEditModal [value=\''+window.icon+'\']';
    selector = selector.replace(/[\r\n]/g,"\r");
    $(selector).attr('checked','checked');
    // console.log($(selector));
    //显示模态框
    $("#menuEditModal").modal("show");
    //点击确认删除时
    $("#menuEditSaveBtn").on('click',function () {
        //获取修改后的数据
        let pid = window.pid;
        let id = window.id;
        let name = nameDom.val();
        let url = urlDom.val();
        let icon = $('#menuEditModal [name ="icon"]:checked').val();
        let param = {
            id:id,
            pid:pid,
            name:name,
            url:url,
            icon:icon
        };
        //提交请求
        $.ajax({
            url:"../menu/do/edit",
            data: JSON.stringify(param),
            contentType:"application/json;charset=UTF-8",
            async:false,
            method: "post",
            success:function (res) {
                if (res.operationResult === 'FAILED'){
                    layer.msg(res.operationMessage);
                    return;
                }
                layer.msg("提交成功！")
            }

        });
        //隐藏模态框
        $("#menuEditModal").modal('hide');
        //重新生成菜单树
        generateTree();
    });


}

//删除时触发
function onClickDeleteBtn() {
    //获取原始数据,并将其创建为dom元素
    let name_dom = "<div>"+window.name+"</div>";
    let url_dom = "<div>"+window.url+"</div>";

    let delete_dialog = $("#delete-list-items");
    //获取模态框
    delete_dialog.append(name_dom+url_dom);
    //显示模态框
    $("#menuDeleteModal").modal("show");

    //为确认删除按钮绑定响应事件
    $("#do_delete_btn").on("click",function () {
        //获取id
        let id = window.id;
        //发送删除请求
        $.ajax({
            url:'../menu/do/delete',
            method:"post",
            data:JSON.stringify({id:id}),
            async:false,
            contentType:'application/json;charset=UTF-8',
            success:function (res) {
                if (res.operationResult === 'FAILED'){
                    layer.msg(res.operationMessage);
                    return;
                }
                layer.msg("操作成功！")
            }
        });

        //隐藏模态框
        $("#menuDeleteModal").modal("hide");

        //清空对话框
        delete_dialog.empty();

        //重新生成dom树
        generateTree();

    })


}