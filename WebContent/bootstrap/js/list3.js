/**
 * 
 */

/**
 * 
 */
var basePath = getRootPath();
$(function () {
    // 页面加载完成之后，直接发送ajax请求，要到分页数据
    doQuery(1);
    // 将按钮绑定事件
    bindEvent();
});
// 查询方法
function doQuery(pn) {
    $.ajax({
        url: "userp",
        data:"pn=" + pn,
        type:"GET",
        success:function(result){
            console.log(result);
            // 解析并显示员工数据
            build_user_table(result);
            // 解析并显示分页信息
            build_page_info(result);
            // 解析并显示分页条
            build_page_nav(result);
        }
    });
}







//解析并显示员工数据
function build_user_table(result) {
    // 清空表格
    $("#user_table tbody").empty();
    var users = result.data.pageInfo.list;
    $.each(users,function(index,item){
    	var chkboxTd = $("<td></td>").append($("<input type='checkbox' class='check-item'/>"));

        var userIdTd = $("<td></td>").append(item.sid);
        var userNameTd = $("<td></td>").append(item.uname);
        var passwordTd = $("<td></td>").append(item.snumber);
        var timeTd = $("<td></td>").append(item.stime);
        var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm user-edit-btn")
                                .append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
                                .append("编辑");
        //将编辑按钮添加自定义属性，值为当前数据的id
        editBtn.attr("user-edit-id",item.sid);
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm user-del-btn")
                                .append($("<span></span>").addClass("glyphicon glyphicon-remove"))
                                .append("删除");
        //将删除按钮添加自定义属性，值为当前数据的id
        delBtn.attr("user-del-id",item.sid);
        var operate = $("<td></td>").append(editBtn).append("&nbsp;").append(delBtn);
        $("<tr></tr>").append(chkboxTd)
        			.append(userIdTd)
                    .append(userNameTd)
                    .append(passwordTd)
                    .append(timeTd)
                    .append(operate)
                    .appendTo("#user_table tbody");
    });
}

// 解析并显示分页信息
function build_page_info(result){
    // 清空
    $("#pageInfo_area").empty();
    $("#pageInfo_area").append("当前第"+ result.data.pageInfo.pageNum 
            +"页,共"+ result.data.pageInfo.pages +"页,"
            + result.data.pageInfo.total +"条记录");
    currentPage=result.data.pageInfo.pageNum
    lastPage=result.data.pageInfo.pages
}


// 解析并显示分页条
function build_page_nav(result) {
    // 清空
    $("#pageNav_area").empty();
    // nav
    var pageNav = $("<nav></nav>").attr("aria-label","Page navigation");
    // ul
    var pageUl = $("<ul></ul>").addClass("pagination");
    // 首页
    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    // 前一页
    var previousPageLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href","#"));
    
    // 如果没有前一页，首页和前一页设置为不可点
    if(!result.data.pageInfo.hasPreviousPage){
        firstPageLi.addClass("disabled");
        previousPageLi.addClass("disabled");
    }else{
        // 点击时发送ajax请求，获取当前页数据
        firstPageLi.click(function(){
            doQuery(1);
        });
        previousPageLi.click(function(){
            doQuery(result.data.pageInfo.pageNum - 1);
        });
    }
    
    // 将首页和前一页加入到ul标签中
    pageUl.append(firstPageLi).append(previousPageLi);
    // 遍历得到中间页码号
    $.each(result.data.pageInfo.navigatepageNums,function(index,item){
        var numsLi = $("<li></li>").append($("<a></a>").append(item).attr("href","#"));
        // 所在页设置为高亮
        if(result.data.pageInfo.pageNum == item){
            numsLi.addClass("active");
        }
        // 点击时发送ajax请求，获取当前页数据
        numsLi.click(function(){
            doQuery(item);
        });
        // 将每个li页加入到ul标签中
        pageUl.append(numsLi);
    })
    // 后一页
    var NextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href","#"));
    // 末页
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
    // 如果没有后一页，末页和后一页设置为不可点
    if(!result.data.pageInfo.hasNextPage){
        NextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    }else{
        // 点击时发送ajax请求，获取当前页数据
        NextPageLi.click(function(){
            doQuery(result.data.pageInfo.pageNum + 1);
        });
        lastPageLi.click(function(){
            doQuery(result.data.pageInfo.pages);
        });
    }
    
    // 将后一页和末页加入到ul标签中
    pageUl.append(NextPageLi).append(lastPageLi);
    // 将ul标签加到nav标签中
    pageNav.append(pageUl);
    // 将nav标签加入到指定div中
    $("#pageNav_area").append(pageNav);
}

//获取项目根路径，如： http://localhost:8083/ssm-dynamic
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/ssm-dynamic/jsp/jsonList.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： ssm-dynamic/jsp/jsonList.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/ssm-dynamic
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}


//新增方法
function doAdd(formData){
    $.ajax({
        url:"usera",
        data:formData,
        type:"POST",
        success:function(result){
            //1.关闭模态框
            $("#userAddModal").modal("hide");
            alert(result.msg);
            doQuery(lastPage);

        },
        error: function() {
            alert("处理失败");
        },
    });
}

//删除方法
function doDelete(id){
    $.ajax({
        url:"userd/" + id,
        type:"GET",
        success:function(result){
            //1.关闭模态框
            $("#userDelModal").modal("hide");
            doQuery(currentPage);
        },
    error: function() {
        alert("处理失败");
    },
    });
}

//根据id查询用户信息
function getUserById(id){
    $.ajax({
        url:"useru/" + id,
        type:"GET",
        success:function(result){
            //console.log(result);
            // 在模态框相应位置赋值
            $("#userUpdateModal_userId").val(result.data.user.sid);
            $("#userUpdateModal_userName").val(result.data.user.stime);
            $("#userUpdateModal_userPwd").val(result.data.user.snumber);
            
            $("#userUpdateModal_userPwd2").val(result.data.user.uid);
        }
    });
}

//更新方法
function doUpdate(updateFormData){
    $.ajax({
        url:"useru",
        type:"POST",
        data:updateFormData,
        success:function(result){
            console.log(result);
            if(result.code == 100){
                //1.关闭模态框
            	
                $("#userUpdateModal").modal("hide");
                doQuery(currentPage);
            }
            else{
                // 清空样式和span信息
                removeInfo("#userUpdateModal_userPwd");
             
              }
        
        }
    });
}
//将按钮绑定事件
function bindEvent(){
    //将新增按钮绑定click事件
    $("#userAddBtn").click(function(){
    	
    	//清空模态框中的样式和内容
        restModal("#userAddModal form");

        $("#userAddModal").modal({
            // 点击背景模态框不关闭
            backdrop:"static"
        });
    })
    //将删除按钮绑定click事件
    $("#userDelBtn").click(function(){
        $("#userDelModal").modal({
            
        });
    });
    
    
    
    //将保存按钮绑定click事件
    $("#userAddModalSaveBtn").click(function(){
        // 获取页面输入的数据
        var formData = $("#userAddModalForm").serialize();
        // 执行新增方法
        //alert(formData);
        doAdd(formData);
        
      /*  $.ajax({
            url:"usera",
            data:$("#userAddModalForm").serialize(),
            type:"POST",
            success:function(result){
                alert("成功");
            }
        }); */
    });
    
    
    $(document).on("click",".user-edit-btn",function(){
        //1.查出user信息，并赋值到模态框中
        var id = $(this).attr("user-edit-id");
        getUserById(id);
        // 清空样式和span信息
        removeInfo("#userUpdateModal_userPwd");
 
        //2.显示模态框
        $("#userUpdateModal").modal({
            // 点击背景模态框不关闭
            backdrop:"static"
        });
    });
    //将更新模态框中保存按钮绑定事件
    $("#userUpdateModalSaveBtn").click(function(){
        //页面验证
        
            var updateFormData = $("#userUpdateModalForm").serialize();
            
            doUpdate(updateFormData);
       
    });

    
  //将删除按钮绑定click事件
	$("#userDelBtn").click(function(){
		$("#userDelModal").modal({
			
		});
	});
	
    //将删除按钮绑定事件
    $(document).on("click",".user-del-btn",function(){
        var id = $(this).attr("user-del-id");
        //2.显示模态框
        $("#userDelModal").modal({
            // 点击背景模态框不关闭
            backdrop:"static"
        });
        $("#userDelModalSaveBtn").click(function(){
        	
            doDelete(id);
        });
    });
    
  //全选/全不选check
    $("#chkboxAll").click(function(){
        //使用attr获取的值是undefined
        //alert($(this).attr("checked"));
        //对于自定义属性，使用arrt
        //对于dom原生的属性，建议使用prop
        //alert($(this).prop("checked"));
        $(".check-item").prop("checked",$(this).prop("checked"));
    });
    //将每一个check框绑定click事件
    $(document).on("click",".check-item",function(){
        var flag = $(".check-item:checked").length == $(".check-item").length
        $("#chkboxAll").prop("checked",flag);
    });
    var ids = "";
    //将删除按钮绑定click事件
    $("#usersDelBtn").click(function(){
        ids = "";
        var usersName = "";
        $.each($(".check-item:checked"),function(){
            usersName += $(this).parents("tr").find("td:eq(2)").text() + ",\n";
            ids += $(this).parents("tr").find("td:eq(1)").text() + "-";
        });
        //去除usersName多余的逗号
        usersName = usersName.substring(0,usersName.length-2);
        //去除usersName多余的逗号
        ids = ids.substring(0,ids.length-1);
        $("#usersDelModalMsg").text("确认删除 [" + usersName + "] ?");
        $("#userDelModals").modal({
            // 点击背景模态框不关闭
            backdrop:"static"
        });
        
    });
    $("#userDelModalSaveBtns").click(function(){
        console.log(ids);
        doDelete(ids);
    });
}
    
   
function restModal(ele){
    //[0]是由于jQuery没有reset方法，在此转为dom对象，调用reset方法使表单重置
    $(ele)[0].reset();
    //移除样式和span中的内容
    $(ele).find("*").removeClass("has-error has-success has-warning");
    $(ele).find(".help-block").text("");
}
   
 
/**
 * 清空样式和显示信息
 */
function removeInfo(ele){
    $(ele).parent().removeClass("has-error has-success has-warning");
    $(ele).next("span").text("");
}

/**
 * 改变样式并显示信息
 */
function changeInfo(ele,style,msg){
    $(ele).parent().removeClass("has-error has-success has-warning");
    $(ele).parent().addClass(style);
    $(ele).next("span").text(msg);
}


