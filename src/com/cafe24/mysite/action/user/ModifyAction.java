package com.cafe24.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.UserDao;
import com.cafe24.mysite.vo.UserVo;

public class ModifyAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Long no = Long.parseLong( request.getParameter( "no" ) );
	String name = request.getParameter( "name" );
	String email = request.getParameter( "email" );
	String password = request.getParameter( "password" );
	String gender = request.getParameter( "gender" );

	UserVo vo = new UserVo();
	vo.setNo( no );
	vo.setName( name );
	vo.setEmail( email );
	vo.setPassword( password );
	vo.setGender( gender );

	new UserDao().update( vo );
	
	HttpSession session = request.getSession();
	vo.setEmail( null );
	vo.setPassword( null );
	vo.setGender( null );

	session.setAttribute( "authUser", vo );

	WebUtil.redirect( request, response, "/mysite/user?a=modifyform" );
    }

}
