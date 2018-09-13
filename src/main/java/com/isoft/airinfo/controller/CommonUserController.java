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
            @RequestParam(value="pn",defaultValue="1")Integer pn){// ��ҳ����
        
        /* ���÷�ҳ��ѯ
        ** ����PageHelper��ҳ�������pagehelper-5.1.2.jar��jsqlparser-0.9.1.jar
        ** mybatis.xml�����÷�ҳ
        ** ����PageHelper.startPage(pageNum[�ڼ�ҳ], pageSize[ÿҳ��ʾ����������]);
        **/
        PageHelper.startPage(pn, 7);
        // startPage������Ĳ�ѯ��Ϊ��ҳ��ѯ
        List<CommonUser> list = service.getUserList();
        // ʹ��pageInfo��װ��ѯ��Ľ����,��װ��ϸ�ķ�ҳ��Ϣ,5��������ʾ5ҳ
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
     * ����id����user
     */
    @RequestMapping(value="/useru",method=RequestMethod.POST)
    @ResponseBody
    public Msg updateByUserId(CommonUser user){
  /*      if(result.hasErrors()){
            // У��ʧ�ܣ���ģ̬������ʾ�����У����Ϣ
            Map<String,String> map = new HashMap<String,String>();
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError:errors){
                System.out.println("�����ֶΡ�����" + fieldError.getField());
                System.out.println("������Ϣ������" + fieldError.getDefaultMessage());
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
     * ����id��ѯuser
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
     * ����idɾ��user
     */
    @ResponseBody
    @RequestMapping(value="/userd/{ids}",method=RequestMethod.GET)
    public Msg deleteUserByUserId(@PathVariable("ids")String uid){
    	 // �ж��Ƿ����-,������ڱ�ʾ������ɾ��
        if(uid.contains("-")){
            // ��id���ΪString ����
            String[] str_uids = uid.split("-");
            // ��������ɾ������
            service.delete(Arrays.asList(str_uids));
        }else{
            // ����ɾ��
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
            HttpServletRequest request // ��ǰ̨ҳ��ȡ�õ�ֵ
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
            HttpServletRequest request // ��ǰ̨ҳ��ȡ�õ�ֵ
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
     * ����û��������Ƿ���ȷ
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

	// �жϵ�¼�˺��Ƿ����
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
	
	
/*    // ��ѯ���е�����
    @RequestMapping("/list")
    private ModelAndView listAll() {
        List<CommonUser> emps = service.findAll();
        ModelAndView mv = new ModelAndView("/userinfo");
        mv.addObject("empVo", emps);
        return mv;
    }

    // ��������
    @RequestMapping("/save")
    public ModelAndView save(CommonUser user) {
        
    	CommonUser uuser =new CommonUser();
    	uuser.setUname(user.getUname());
    	uuser.setUpassword(user.getUpassword());
        return listAll();
    }

    // ��ѯһ������
    //ajax��ѯ����
    @ResponseBody
    @RequestMapping("/findbyuid")
    public CommonUser findByid(@RequestParam("uid") int uid) {
        return service.getByID(uid);
    }

    // �޸�����
    @RequestMapping("/update")
    public ModelAndView update(CommonUser user) {
    	CommonUser uuser =new CommonUser();
    	uuser.setUname(user.getUname());
    	uuser.setUpassword(user.getUpassword());
    	
        return listAll();
    }

    // ɾ��
    @RequestMapping("/delete")
    public ModelAndView delete(int uid) {
        service.delete(uid);
        return listAll();

    }
*/

}


