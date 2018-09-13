package com.isoft.airinfo.controller;




import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isoft.airinfo.db.entity.CommonUser;
import com.isoft.airinfo.db.entity.Msg;
import com.isoft.airinfo.service.CommonUserService;



@Controller
@RequestMapping("logic/user")
public class CommonUserController {

	@Autowired
	private CommonUserService service;
	
	@RequestMapping(value="info")
	private String a() {

        return "userinfo";
    }
	@RequestMapping(value="/userp",method=RequestMethod.GET)
    @ResponseBody
    public Msg getUser(
            @RequestParam(value="pn",defaultValue="1")Integer pn){// 分页参数
        
        /* 配置分页查询
        ** 引入PageHelper分页插件，即pagehelper-5.1.2.jar和jsqlparser-0.9.1.jar
        ** mybatis.xml中配置分页
        ** 调用PageHelper.startPage(pageNum[第几页], pageSize[每页显示多少条数据]);
        **/
        PageHelper.startPage(pn, 7);
        // startPage后紧跟的查询即为分页查询
        List<CommonUser> list = service.getUserList();
        // 使用pageInfo包装查询后的结果集,封装详细的分页信息,5是连续显示5页
        PageInfo pageInfo = new PageInfo(list,5);
        return Msg.success().add("pageInfo",pageInfo);
    }
    
    @RequestMapping(value="/usera",method=RequestMethod.POST)
    @ResponseBody
    public Msg addUser(CommonUser user){
    	System.out.println(user.getUid()+user.getUname()+user.getUpassword());
        service.addUser(user);
        return Msg.success();
    }

    /**
     * 根据id更新user
     */
    @RequestMapping(value="/useru",method=RequestMethod.POST)
    @ResponseBody
    public Msg updateByUserId(CommonUser user){
  /*      if(result.hasErrors()){
            // 校验失败，在模态框中显示错误的校验信息
            Map<String,String> map = new HashMap<String,String>();
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError:errors){
                System.out.println("错误字段》》》" + fieldError.getField());
                System.out.println("错误信息》》》" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errors", map);
        }else{*/
    	System.out.println(user.getUid()+user.getUname()+user.getUpassword());
            service.update(user);
            return Msg.success();
       /* }*/
    }

	
    /**
     * 根据id查询user
     * @param id
     * @return
     */
    @RequestMapping(value="/useru/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Msg getUserById(@PathVariable()int id){
        CommonUser user = service.getByID(id);
        System.out.println(user.getUid()+user.getUname()+user.getUpassword());
        return Msg.success().add("user", user);
    }
    
    
    /**
     * 根据id删除user
     */
    @ResponseBody
    @RequestMapping(value="/userd/{ids}",method=RequestMethod.GET)
    public Msg deleteUserByUserId(@PathVariable("ids")String uid){
    	 // 判断是否存在-,如果存在表示是批量删除
        if(uid.contains("-")){
            // 将id拆分为String 数组
            String[] str_uids = uid.split("-");
            // 调用批量删除方法
            service.delete(Arrays.asList(str_uids));
        }else{
            // 单个删除
        	int uidi=Integer.parseInt(uid);
            service.delete(uidi);
        }
        return Msg.success();

    }
    
    
    @RequestMapping(value="/findRepeatUserName",method=RequestMethod.GET)
    @ResponseBody
    public Msg findRepeatUserName(@RequestParam(value="userName")String userName){
        Boolean flag = service.findRepeatUserName(userName);
        if(flag){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    
    @RequestMapping(value="/login",method=RequestMethod.GET)
    @ResponseBody
    public int login(
            HttpServletRequest request // 从前台页面取得的值
            ){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean flag = LoginCheck(username, password);       
        if(flag){
            return 1;
        }else{
            
            return 0;
        }
    }
    
    @RequestMapping(value="/zhuce",method=RequestMethod.GET)
    @ResponseBody
    public int zhuce(
            HttpServletRequest request // 从前台页面取得的值
            ){
    	CommonUser user=new CommonUser();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        user.setUname(username);
        user.setUpassword(password);
        service.addUser(user); 
        return 1;
   
    }
    /**
     * 检查用户名密码是否正确
     * @param username
     * @param password
     * @return
     */
    private boolean LoginCheck(String username,String password){
        CommonUser user = service.selectByUserName(username);
        if(user == null || "".equals(user)){
            return false;
        }
        if(user.getUpassword().equals(password)){
            return true;
        }else{
            return false;
        }
    }

	// 判断登录账号是否存在
 /*   @RequestMapping("/getByName")
    public int getByName(String uname, String upassword) {
    	CommonUser user=service.getByname(uname);
    	if(user.getUid()!=null && user.getUpassword()=upassword) {
    		return 1;
    	}
    	else
    		return 0;
    	
    }
*/
	
	
/*    // 查询所有的数据
    @RequestMapping("/list")
    private ModelAndView listAll() {
        List<CommonUser> emps = service.findAll();
        ModelAndView mv = new ModelAndView("/userinfo");
        mv.addObject("empVo", emps);
        return mv;
    }

    // 新增数据
    @RequestMapping("/save")
    public ModelAndView save(CommonUser user) {
        
    	CommonUser uuser =new CommonUser();
    	uuser.setUname(user.getUname());
    	uuser.setUpassword(user.getUpassword());
        return listAll();
    }

    // 查询一条数据
    //ajax查询数据
    @ResponseBody
    @RequestMapping("/findbyuid")
    public CommonUser findByid(@RequestParam("uid") int uid) {
        return service.getByID(uid);
    }

    // 修改数据
    @RequestMapping("/update")
    public ModelAndView update(CommonUser user) {
    	CommonUser uuser =new CommonUser();
    	uuser.setUname(user.getUname());
    	uuser.setUpassword(user.getUpassword());
    	
        return listAll();
    }

    // 删除
    @RequestMapping("/delete")
    public ModelAndView delete(int uid) {
        service.delete(uid);
        return listAll();

    }
*/

}


