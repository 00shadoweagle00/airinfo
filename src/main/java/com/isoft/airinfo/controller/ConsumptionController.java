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
            @RequestParam(value="pn",defaultValue="1")Integer pn){// ��ҳ����
        
        /* ���÷�ҳ��ѯ
        ** ����PageHelper��ҳ�������pagehelper-5.1.2.jar��jsqlparser-0.9.1.jar
        ** mybatis.xml�����÷�ҳ
        ** ����PageHelper.startPage(pageNum[�ڼ�ҳ], pageSize[ÿҳ��ʾ����������]);
        **/
        PageHelper.startPage(pn, 7);
        // startPage������Ĳ�ѯ��Ϊ��ҳ��ѯ
        List<Consumption> list = service.getUserList();
        // ʹ��pageInfo��װ��ѯ��Ľ����,��װ��ϸ�ķ�ҳ��Ϣ,5��������ʾ5ҳ
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
     * ����id����user
     */
    @RequestMapping(value="/useru",method=RequestMethod.POST)
    @ResponseBody
    public Msg updateByUserId(Consumption user){
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
     * ����id��ѯuser
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
    
}