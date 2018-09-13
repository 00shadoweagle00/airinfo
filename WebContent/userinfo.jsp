<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>查看信息</title>
	<script type="text/javascript" src="jquery/jquery-3.2.1.min.js">
		
	</script>

<script language="JavaScript">
    $(function () {
        $("#search").click(function () {
            $.ajax({
                       type: "post",
                       url: "logic/user/findbyuid",
                       data: $("#uid"),
                       dataType: "json",
                       success: function (data) {
                           var html = "";
                           html +=
                               "<td>" + data.uid + "</td><td>" + data.uname + "</td><td>" + data.upassword
                               + "</td>";
                           $("#user").html(html);
                       },
                       //null值不会被success回调函数捕获，因此可以通过error来实现该功能
                       error: function () {
                           alert("请输入正确的编号！");
                       }
                   })
        })
    })
</script>


</head>
<body>
 <center>
    <div style="margin-top: 25px">
        请输入所要查询的学号:<input type="text" id="uid" name="uid"/><input id="search" style="margin-left: 10px" type="button" value="搜索"><br/>
    </div>
    <div style="margin-top: 50px">
        <table border="1">
            <tr>
                <td>编号</td>
                <td>用户名</td>
                <td>密码</td>

            </tr>
            <tr id="user">

            </tr>
        </table>
    </div>
</center>

</body>
</html>
