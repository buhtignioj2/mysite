package com.cafe24.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

public class DeleteFormAction implements Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String no = request.getParameter( "no" );

	request.setAttribute( "no", no );
	WebUtil.forward( request, response, "/WEB-INF/views/guestbook/deleteform.jsp" );
    }

}
