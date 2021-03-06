package com.isoft.airinfo.controller;




import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isoft.airinfo.db.entity.CommonUser;
import com.isoft.airinfo.db.entity.Consumption;
import com.isoft.airinfo.db.entity.Msg;

import com.isoft.airinfo.service.ConsumptionService;



@Controller
@RequestMapping("logic/consumption")
public class ConsumptionController {

	@Autowired
	private ConsumptionService service;
	
	@RequestMapping(value="info")
	private String a() {

        return "consumptioninfo";
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
        List<Consumption> list = service.getUserList();
        // 使用pageInfo包装查询后的结果集,封装详细的分页信息,5是连续显示5页
        PageInfo pageInfo = new PageInfo(list,5);
        return Msg.success().add("pageInfo",pageInfo);
    }
    
    @RequestMapping(value="/usera",method=RequestMethod.POST)
    @ResponseBody
    public Msg addUser(Consumption user){
 
        service.save(user);
        return Msg.success();
    }

    /**
     * 根据id更新user
     */
    @RequestMapping(value="/useru",method=RequestMethod.POST)
    @ResponseBody
    public Msg updateByUserId(Consumption user){
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
    	
            service.update(user);
            return Msg.success();
       /* }*/
    
    }
    
    
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }




	
    /**
     * 根据id查询user
     * @param id
     * @return
     */
    
    @RequestMapping(value="/useru/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Msg getUserById(@PathVariable()int id){
        Consumption user = service.getByID(id);
        
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
    
}