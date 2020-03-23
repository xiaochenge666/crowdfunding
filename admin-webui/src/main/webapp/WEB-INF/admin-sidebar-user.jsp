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
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float:left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input class="form-control has-success" type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
						</form>
						<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
						<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='add.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
						<br>
						<hr style="clear:both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
								<tr >
									<th width="30">#</th>
									<th width="30"><input type="checkbox"></th>
									<th>账号</th>
									<th>名称</th>
									<th>邮箱地址</th>
									<th width="100">操作</th>
								</tr>
								</thead>
								<tbody>
								<c:if test="${empty requestScope.pageInfo.list}">
									<tr>
										<td>抱歉，没有查询到任何相关数据</td>
									</tr>
								</c:if>
								<c:if test="${!empty requestScope.pageInfo.list}">
									<c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
										<tr>
											<td>${myStatus.count}</td>
											<td><input type="checkbox"></td>
											<td>${admin.loginAcct}</td>
											<td>${admin.userName}</td>
											<td>${admin.email}</td>
											<td>
												<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
												<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
												<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								</tbody>
								<tfoot>
								<tr >
									<td colspan="6" align="center">
										<ul class="pagination">
											<li class="disabled"><a href="#">上一页</a></li>
											<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">5</a></li>
											<li><a href="#">下一页</a></li>
										</ul>
									</td>
								</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
</div>
<%--引用js--%>
<%@include file="admin-mian-js-include.jsp"%>
</body>
</html>
