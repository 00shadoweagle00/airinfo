package com.isoft.airinfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.isoft.airinfo.db.entity.CommonUser;
import com.isoft.airinfo.service.CommonUserService;



/**
 * Servlet implementation class StuController
 */
@WebServlet("/CommonUserController")
public class CommonUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if (op.equals("findAll")) {
			findAll(request, response);
			return;
		}
		if (op.equals("saveStuInfo")) {
			saveStuInfo(request, response);
			return;
		}
		if (op.equals("deleteStuInfo")) {
			deleteStuInfo(request, response);
			return;
		}
/*		if (op.equals("findStuInfoById")) {
			findStuInfoById(request, response);
			return;
		}
		
*/
		if (op.equals("updateStuInfo")) {
			updateStuInfo(request, response);
			return;
		}
	}

	
	public void updateStuInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid = request.getParameter("id");
		String uname = request.getParameter("uname");
		String upassword = request.getParameter("upassword");

		CommonUserService service=new CommonUserService();
		CommonUser stu = new CommonUser();
		stu.setUname(uname);
		stu.setUid(Integer.parseInt(uid));
		stu.setUpassword(upassword);
		service.update(stu);

	}
	
/*	public void findStuInfoById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		CommonUserService service=new CommonUserService();
		CommonUser stu = service(Integer.parseInt(id));
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(JSON.toJSONString(stu));
		writer.close();
	}
	
	
*/	
	
	
	public void deleteStuInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id=request.getParameter("id");
		CommonUserService service=new CommonUserService();
		service.delete(Integer.parseInt(id));


	}
	
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommonUserService service=new CommonUserService();
		List<CommonUser> findAll = service.findAll();
		response.setContentType("application/json;charset=utf-8");
		String json = JSON.toJSONString(findAll);
		PrintWriter writer = response.getWriter();
		writer.print(json);
		writer.close();
	}

	public void saveStuInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("uname");
		String upassword = request.getParameter("upassword");
	
		CommonUserService service=new CommonUserService();
		CommonUser stu = new CommonUser();
		stu.setUname(uname);
		stu.setUpassword(upassword);
	
		service.update(stu);
	/*	PrintWriter writer = response.getWriter();
		if (i >= 0) {
			writer.print("success");
		} else {
			writer.print("error");
		}
		writer.close();*/
		System.out.println(uname + "," + upassword);

	}
}
