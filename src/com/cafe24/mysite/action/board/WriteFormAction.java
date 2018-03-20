package com.cafe24.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.vo.UserVo;

public class WriteFormAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	if ( session == null ) {
	    WebUtil.redirect( request, response, "/mysite/board" );
	    return;
	}
	UserVo authUser = ( UserVo ) session.getAttribute( "authUser" );
	if ( authUser == null ) {
	    WebUtil.redirect( request, response, "/mysite/board" );
	    return;
	}

	WebUtil.forward( request, response, "/WEB-INF/views/board/write.jsp" );
    }

}
