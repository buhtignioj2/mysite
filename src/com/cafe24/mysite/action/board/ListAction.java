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

public class ListAction implements Action {
    private static final int LIST_SIZE = 5;
    private static final int PAGE_SIZE =5;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	int currentPage = ( request.getParameter( "p" ) == null ) ? 1 : Integer.parseInt( request.getParameter( "p" ));
	String keyword = request.getParameter( "keyword" );
	
	BoardDao dao = new BoardDao();
	
	int totalCount = dao.getTotalCount( keyword );
	int pageCount = (int)Math.ceil( (double)totalCount / LIST_SIZE );
	int blockCount = (int)Math.ceil( (double )pageCount / PAGE_SIZE );
	int currentBlock = (int)Math.ceil( (double )currentPage / PAGE_SIZE );
	
	int beginPage = (currentBlock == 0) ? 1: ( currentBlock -1 ) * PAGE_SIZE + 1;
	int prevPage = (currentBlock > 1 ) ? ( currentBlock -1 ) * PAGE_SIZE : 0;
	int nextPage = (currentBlock < blockCount ) ? currentBlock * PAGE_SIZE + 1 : 0;
	int endPage = ( nextPage > 0 ) ? ( beginPage - 1 ) + LIST_SIZE : pageCount;
	
	List<BoardVo> list = dao.getList( keyword, currentPage, LIST_SIZE );
	
	request.setAttribute( "list", list );
	request.setAttribute( "totalCount", totalCount );
	request.setAttribute( "listSize", LIST_SIZE );
	request.setAttribute( "currentPage", currentPage );
	request.setAttribute( "beginPage", beginPage );
	request.setAttribute( "endPage", endPage );
	request.setAttribute( "prevPage", prevPage );
	request.setAttribute( "nextPage", nextPage );
	request.setAttribute( "keyword", keyword );
	
	
	WebUtil.forward( request, response, "/WEB-INF/views/board/list.jsp" );
    }

}
