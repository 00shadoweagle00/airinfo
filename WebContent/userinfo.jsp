<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看信息</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css" />
<script type="text/javascript" src="jquery/jquery-3.2.1.min.js">
	
</script>

<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<style type="text/css">
td{
	text-align: center;
}
</style>
<script type="text/javascript">
	$(function() {
		initTable();
	});
	function initTable() {
		var tbl = "";
		$.ajax({
			url : "CommonUserController",
			data : {
				op : "findAll"
			},
			success : function(data) {
				$.each(data, function(i, v) {
					tbl += "<tr><td><input type='checkbox' name='_id'></td><td>"
						+ v.uid
						+ "</td><td>"
						+ v.uname
						+ "</td><td>"
						+ v.upassword
						+ "</td><td><a class='btn btn-warning' onclick='javascript:showUpdateModel("+v.uid+")'><span class='glyphicon glyphicon-edit'></span> 更新</a>&nbsp;&nbsp;&nbsp;&nbsp;"
						+"<a href=\"javascript:deleteStuInfo("+v.uid+")\" class='btn btn-success' onclick='return confirm(\"是否删除？\")'><span class='glyphicon glyphicon-remove'></span> 删除</a></td></tr>"
				});
				$("#tbl").html(tbl);
			},
			error : function() {
				alert("请求服务器失败");
			}
		});
	}
/*
	function showUpdateModel(id) {
		$("#id").show();
		$("#insertORupdateModal").modal("show");
		$("#myModalLabel").html("修改学生信息");
		$("#btn_confirm").html("修改");
		$.ajax({
			url : "StuController",
			data : {
				"op" : "findStuInfoById",
				"id" : id
			},
			success : function(result) {
				//alert(result.usex);
				//var json = JSON.parse(result);
				$("#id").val(result.id);
				$("#uname").val(result.uname);
				//此处有个Bug
				if (result.usex == 1)
					$("#usex option[value=1]").attr("selected", true);
				else
					$("#usex option[value=0]").attr("selected", true);

				$("#telephone").val(result.telephone);
			},
			error : function() {
				alert("请求服务器失败");
			}
		});

	}
*/	
	function showInsertModel() {
		$("#uid").val("");
		$("#uid").hide();
		$("#uname").val("");
		$("#usex").val();
	

		$("#insertORupdateModal").modal("show");
		$("#myModalLabel").html("添加学生信息");
		$("#btn_confirm").html("保存");
	}
	
	function saveOrupdateStuInfo() {
		var v = $("#btn_confirm").html();
		alert(v);//那个多余的保存修改提示在这儿
		if (v == "保存") {
			saveStuInfo();
		} else {
			updateStuInfo();
		}
	}
	
	function updateStuInfo() {
		var id = $("#uid").val();//Jquery的取值方法
		var uname = $("#uname").val();//Jquery的取值方法
		var usex = $("#usex").val();//Jquery的取值方法
/*		var telephone = $("#telephone").val();//Jquery的取值方法*/

		$.ajax({
			url : "CommonUserController",
			data : {
				"op" : "updateStuInfo",
				"uid" : uid,
				"uname" : uname,
				"usex" : usex,
			
			},
			success : function(result) {
				if (result == "success")
					alert("修改学生信息成功");
				else
					alert("修改学生信息失败");
				$("#insertORupdateModal").modal("hide");
				initTable();

			},
			error : function() {
				alert("失败");
			}
		});
	}
	
	function saveStuInfo() {
		//var uname = document.getElementById("uname");
		var uname = $("#uname").val();//Jquery的取值方法
		var usex = $("#usex").val();//Jquery的取值方法
		/*var telephone = $("#telephone").val();//Jquery的取值方法*/

		$.ajax({
			url : "CommonUserController",
			data : {
				"op" : "saveStuInfo",
				"uname" : uname,
				"usex" : usex,
		
			},
			success : function(result) {
				if (result == "success")
					alert("添加学生信息成功");
				else
					alert("添加学生信息失败");
				$("#insertORupdateModal").modal("hide");
				initTable();

			},
			error : function() {
				alert("失败");
			}
		});
		//alert(uname + "," + usex + "," + telephone);

	}
	
	function deleteStuInfo(id){
		
		if(id.length==0){
			alert("请输入学号");
			
			
		}else{
			$.ajax({
				url:"CommonUserController",
				data:{
					"op":"deleteStuInfo",
					"id":id
				},
				success:function(result){
					if(result==0)
					
						alert("删除失败");
					$("#deleteModal").modal("hide");
					initTable();
				},
				error:function(){
					alert("请求服务失败");
				}
				
			});
			
			
		}
		
		
		
	}
	
function deleteStuInfo_2(){
		
		
			alert("请输入学号");
			
			
		
			$.ajax({
				url:"CommonUserController",
				data:{
					"op":"deleteStuInfo",
					"id":id
				},
				success:function(result){
					if(result==0)
					
						alert("删除失败");
					$("#deleteModal").modal("hide");
					initTable();
				},
				error:function(){
					alert("请求服务失败");
				}
				
			});
			
			
		
		
		
		
	}
</script>
</head>
<body>
	<div class="container">
		<h1>信息表</h1>
		<div class="panel panel-default">
			<div class="panel-heading">
				
				<a href="javascript:showInsertModel()" class="btn  btn-info"><span
					class="glyphicon glyphicon-plus"></span> 增加</a> <a href=""
					class="btn  btn-warning"data-toggle="modal"
					data-target="#deleteModal"><span
					class="glyphicon glyphicon-minus"></span> 删除</a> <a href=""
					class="btn  btn-primary"><span
					class="glyphicon glyphicon-search"></span> 查询</a>

			</div>
			<div class="panel-body">
				<table border=1
					class="table table-bordered table-hover table-striped">
					<tr>
						<td>全选</td>
						<td>ID</td>
						<td>用户名</td>
						<td>密码</td>

					</tr>
					<tbody id="tbl">
					</tbody>
				</table>
				<div align="right">
					<ul class="pagination">
						<li><a href=""><<</a></li>
						<li><a href="">1</a></li>
						<li><a href="">2</a></li>
						<li><a href="">3</a></li>
						<li><a href="">4</a></li>
						<li><a href="">5</a></li>
						<li><a href="">>></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- 插入记录模式窗口 -->
	<div class="modal fade" id="insertORupdateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加学生信息</h4>
				</div>
				<div class="modal-body">
					<!-- <h3>添加学生信息</h3> -->
					<div>
						<input readonly="readonly" type="text" name="id" id="id"
							class="form-control" placeholder="请输入学生ID"> <br> <input
							type="text" name="uname" id="uname" class="form-control"
							placeholder="请输入学生姓名"> <br> <select id="usex"
							name="usex" class="form-control">
							<option value="1" selected="selected">男</option>
							<option value="0">女</option>
						</select> <br> <input type="number" name="telephone" id="telephone"
							class="form-control" placeholder="请输入电话号码">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btn_confirm"
						onclick="javascirpt:saveOrupdateStuInfo()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	
	<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">删除学生信息</h4>
				</div>
				<div class="modal-body">
					<input type="text" name="del_id" id="del_id" class="form-control"
						placeholder="请输入学生ID">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="deleteStuInfo_2()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>



</body>
</html>
