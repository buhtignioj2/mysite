package com.cafe24.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

public class ViewAction implements Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	long no = Long.parseLong(request.getParameter( "no" ));
	int page = Integer.parseInt( request.getParameter( "p" ) );
	String keyword = request.getParameter( "keyword" );
	
	BoardDao dao = new BoardDao();
	BoardVo vo = dao.get( no );
	
	if( vo == null ) {
	    WebUtil.redirect( request, response, request.getContextPath() + "/board?p=1&keyword=" );
	}
	dao.updateHit( no );

	request.setAttribute( "vo", vo );
	request.setAttribute( "page", page );
	request.setAttribute( "keyword", keyword );
	
	WebUtil.forward( request, response, "/WEB-INF/views/board/view.jsp" );
    }

}
