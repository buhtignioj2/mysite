package com.cafe24.mysite.action.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

public class AddAction implements Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String name = request.getParameter( "name" );
	String password = request.getParameter( "password" );
	String content = request.getParameter( "content" );
	
	GuestbookVo vo = new GuestbookVo();
	vo.setName( name );
	vo.setPassword( password );
	vo.setContent( content );
	
	GuestbookDao dao = new GuestbookDao();
	dao.insert( vo );
	
	WebUtil.redirect( request, response, "/mysite/guestbook" );
    }

}
