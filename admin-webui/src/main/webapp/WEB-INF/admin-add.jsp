<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/pagination.css">
	<style>
		.tree li {
			list-style-type: none;
			cursor:pointer;
		}
		.tree-closed {
			height : 40px;
		}
		.tree-expanded {
			height : auto;
		}
	</style>
</head>

<body>

<%--引用模板--%>
<%@include file="admin-mian-navbar-include.jsp"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="admin-mian-sidebar-include.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
					<li><a href="#">数据列表</a></li>
					<li class="active">新增</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
					<div class="panel-body">
						<form role="form">
							<div class="form-group">
								<label for="exampleInputPassword1">登陆账号</label>
								<input type="text" class="form-control" id="" placeholder="请输入登陆账号">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">登陆密码</label>
								<input type="text" class="form-control" id="exampleInputPassword1" placeholder="请输入密码">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">用户名称</label>
								<input type="text" class="form-control" id="exampleInputPassword2" placeholder="请输入用户密码">
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">邮箱地址</label>
								<input type="email" class="form-control" id="exampleInputEmail1" placeholder="请输入邮箱地址">
								<p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
							</div>
							<button type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
							<button type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%--引用js--%>
<%@include file="admin-mian-js-include.jsp"%>
<script type="text/javascript">
    $(function () {
       initPagination();
    });
    function initPagination() {
        let totalRecord=${requestScope.pageInfo.total};
		let properties={
			num_edge_entries: 3, // 边缘页数
			num_display_entries: 5, // 主体页数
			callback: pageSelectCallback, // 用户点击“ 翻页” 按钮之后执行翻页操作的回调函数
			current_page: ${requestScope.pageInfo.pageNum-1}, // 当前页， pageNum 从 1 开始，必须-1 后才可以赋值
			prev_text: "上一页",
			next_text: "下一页",
			items_per_page:${requestScope.pageInfo.pageSize} // 每页显示 5 项
		};
		$("#pagination").pagination(totalRecord,properties);
		function pageSelectCallback(current_page,jq) {
			let curr_page=current_page+1;
			window.location.href="admin/do/page.html?pageNo="+curr_page+"&keyword=${param.keyword}";
			return false
		}
    }
    
</script>

</body>
</html>
