package com.cafe24.mysite.action.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe24.mvc.action.Action;
import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.dao.BoardDao;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

public class WriteAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();

	if ( session == null ) {
	    WebUtil.redirect( request, response, request.getContextPath() + "/board" );
	    return;
	}
	UserVo authUser = ( UserVo ) session.getAttribute( "authUser" );
	if ( authUser == null ) {
	    WebUtil.redirect( request, response, request.getContextPath() + "/board"  );
	    return;
	}

	String title = request.getParameter( "title" );
	String content = request.getParameter( "content" );
	String keyword = request.getParameter( "keyword" );
	String gno = request.getParameter( "groupNo" );
	String ono = request.getParameter( "orderNo" );
	String d = request.getParameter( "depth" );

	BoardDao dao = new BoardDao();
	BoardVo vo = new BoardVo();

	if ( gno != null ) {
	    int groupNo = Integer.parseInt( gno );
	    int orderNo = Integer.parseInt( ono );
	    int depth = Integer.parseInt( d );

	    // 같은 그룹의 orderNo 보다 큰 글 들의 order_no 1씩 증가
	    dao.increaseGroupOrder( groupNo, orderNo );

	    vo.setGroupNo( groupNo );
	    vo.setOrderNo( orderNo + 1 );
	    vo.setDepth( depth + 1 );
	    
	}

	vo.setTitle( title );
	vo.setContent( content );
	vo.setUserNo( authUser.getNo() );
	vo.setUserNo( authUser.getNo() );
	vo.setUserName( authUser.getName() );

	dao.insert( vo );

	WebUtil.redirect( request, response, "/mysite/board?p=1&keyword=" + keyword );
    }

}
