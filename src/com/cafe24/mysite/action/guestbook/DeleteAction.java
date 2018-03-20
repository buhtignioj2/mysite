package com.cafe24.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

public class DeleteAction implements Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	Long no = Long.parseLong( request.getParameter( "no" ));
	String password = request.getParameter( "password" );

	GuestbookVo vo = new GuestbookVo();
	vo.setNo( no );
	vo.setPassword( password );
	
	GuestbookDao dao = new GuestbookDao();
	dao.delete( vo );
	
	WebUtil.redirect( request, response, "/mysite/guestbook" );
    }

}
