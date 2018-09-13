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

});
// 查询方法
function doQuery(pn) {
    $.ajax({
        url: "userr",
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
    var i=1;
    $.each(users,function(index,item){
    	
    	var ii = $("<td></td>").append(i++);
      
        var userNameTd = $("<td></td>").append(item.uname);
        var passwordTd = $("<td></td>").append(item.nnumber);
        var timeTd = $("<td></td>").append(item.stime);
        var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm user-edit-btn")
                                .append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
                                .append("编辑");
        //将编辑按钮添加自定义属性，值为当前数据的id
        editBtn.attr("user-edit-id",item.uid);
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm user-del-btn")
                                .append($("<span></span>").addClass("glyphicon glyphicon-remove"))
                                .append("删除");
        //将删除按钮添加自定义属性，值为当前数据的id
        delBtn.attr("user-del-id",item.uid);
        var operate = $("<td></td>").append(editBtn).append("&nbsp;").append(delBtn);
        $("<tr></tr>").append(ii)
        			
                    .append(userNameTd)
                    .append(passwordTd)
                    .append(timeTd)
    
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

