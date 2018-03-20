package com.cafe24.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.mysite.vo.BoardVo;

public class BoardDao {

    public List<BoardVo> getList(String keyword, int page, int size) {
 	List<BoardVo> list = new ArrayList<BoardVo>();

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql= null;
	try {
	    conn = getConnection();
	    
	    if( "".equals( keyword ) || keyword == null) {
		sql = "select b.no, \r\n" + 
			"       b.title, \r\n" + 
			"       b.hit, \r\n" + 
			"       DATE_FORMAT( b.reg_date, '%Y/%m/%d %h:%i:%s') as reg_date, \r\n" + 
			"       b.depth, \r\n" + 
			"       u.name, \r\n" + 
			"       b.user_no \r\n" + 
			"       from board b , users u \r\n" + 
			"      where u.`no` = b.user_no \r\n" + 
			"   order by group_no desc, order_no asc \r\n" + 
			" limit ?, ? ";
 
		pstmt = conn.prepareStatement( sql );
		
		pstmt.setInt( 1, ( (page-1) * size ) );
		pstmt.setInt( 2, size );
		
	    } else {
		sql = " select *  from ( select no, title, hit, reg_date, depth, name, user_no  \r\n" + 
			" from(  select a.no, a.title, a.hit,reg_date, a.depth, b.name, a.user_no\r\n" + 
			"  from board a, users b\r\n" + 
			"  where a.user_no = b.no\r\n" + 
			"  and (title like ? or content like ?)\r\n" + 
			"  order by group_no desc, order_no asc limit ?, ?)t)tt";
		
		pstmt = conn.prepareStatement( sql );

		pstmt.setString(1, "%" + keyword + "%");
		pstmt.setString(2, "%" + keyword + "%");
		pstmt.setInt( 3, ( (page-1) * size ) );
		pstmt.setInt( 4, size );
	    }
	    
	    rs = pstmt.executeQuery();

	    while( rs.next() ) {
		long no = rs.getLong( 1 );
		String title = rs.getString( 2 );
		int hit = rs.getInt( 3 );
		String regDate = rs.getString( 4 );
		int depth = rs.getInt( 5 );
		String userName = rs.getString( 6 );
		long userNo = rs.getLong( 7 );
			
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setTitle(title);
		vo.setHit(hit);
		vo.setRegDate(regDate);
		vo.setDepth(depth);
		vo.setUserName(userName);
		vo.setUserNo(userNo);
			
		list.add( vo );
		}
	    
	} catch ( SQLException e ) {
	    System.out.println( "error:" + e );
	} finally {
	    try {
		if ( rs != null ) {
		    rs.close();
		}
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( SQLException e ) {
		System.out.println( "error:" + e );
	    }
	}
	System.out.println( list );
	return list;
    }


    public BoardVo get(long boardNo) {
	BoardVo vo = null;

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    conn = getConnection();

	    String sql = " select no, title, content, group_no, order_no, depth, user_no from board where no = ?";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setLong( 1, boardNo );
	    rs = pstmt.executeQuery();

	    while ( rs.next() ) {
		long no = rs.getLong( 1 );
		String title = rs.getString( 2 );
		String content = rs.getString( 3 );
		int groupNo = rs.getInt( 4 );
		int orderNo = rs.getInt( 5 );
		int depth = rs.getInt( 6 );
		long userNo = rs.getLong( 7 );

		vo = new BoardVo();
		vo.setNo( no );
		vo.setTitle( title );
		vo.setContent( content );
		vo.setGroupNo( groupNo );
		vo.setOrderNo( orderNo );
		vo.setDepth( depth );
		vo.setUserNo( userNo );
	    }
	} catch ( SQLException e ) {
	    System.out.println( "error:" + e );
	} finally {
	    try {
		if ( rs != null ) {
		    rs.close();
		}
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( SQLException e ) {
		System.out.println( "error:" + e );
	    }
	}

	return vo;
    }

    public void insert(BoardVo vo) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	try {
	    conn = getConnection();
 	    if( vo.getGroupNo() == null ) {
		String sql = 
			"insert "+ 
			"  into board(no, title, content, group_no, order_no, depth, hit, reg_date, user_name, user_no)\r\n" + 
			" select null, ?, ?,  ifnull(max(group_no)+1, 1), 0, 0, 0, now(), ?, ? " + 
			" from board ";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString( 1, vo.getTitle() );
		pstmt.setString( 2, vo.getContent() );
		pstmt.setString( 3, vo.getUserName() );
		pstmt.setLong( 4, vo.getUserNo() );
 	    } else {
		String sql = 
			" insert" +
			"   into board" +
			" values( null, ?, ?, ?, ?, ?, 0, now(), ?, ? )"; 
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString( 1, vo.getTitle() );
		pstmt.setString( 2, vo.getContent() );
		pstmt.setInt( 3, vo.getGroupNo() );
		pstmt.setInt( 4, vo.getOrderNo() );
		pstmt.setInt( 5, vo.getDepth() );
		pstmt.setString( 6, vo.getUserName() );
		pstmt.setLong( 7, vo.getUserNo() );
	}
	    System.out.println( vo );

	    pstmt.executeUpdate();

	} catch ( SQLException e ) {
	    System.out.println( "error:" + e );
	} finally {
	    try {
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( SQLException e ) {
		System.out.println( "error:" + e );
	    }
	}
    }

    public boolean update(BoardVo vo) {
	boolean result = false;

	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = "update board set title = ?, content = ? where no =? and users_no = ?";
	    pstmt = conn.prepareStatement( sql );
	    pstmt.setString( 1, vo.getTitle() );
	    pstmt.setString( 2, vo.getContent() );
	    pstmt.setLong( 3, vo.getNo() );
	    pstmt.setLong( 4, vo.getUserNo() );

	    int count = pstmt.executeUpdate();
	    result = ( count == 1 );
	} catch ( Exception e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}

	return result;
    }

    public boolean delete(Long no) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();

	    String sql = "delete from board where no = ?";
	    pstmt = conn.prepareStatement( sql );
	    pstmt.setLong( 1, no );

	    int count = pstmt.executeUpdate();
	    result = ( count == 1 );

	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}
	return result;
    }

    public int getTotalCount(String keyword) {
	int totalCount = 0;

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	try {
	    conn = getConnection();
	    if ( "".equals( keyword ) ) {
		sql = "select count(*) from board";
		pstmt = conn.prepareStatement( sql );
	    } else {
		sql = "select count(*) from board where title like ? or content like ?";
		pstmt = conn.prepareStatement( sql );

		pstmt.setString( 1, "%" + keyword + "%" );
		pstmt.setString( 2, "%" + keyword + "%" );
	    }
	    rs = pstmt.executeQuery();
	    if ( rs.next() ) {
		totalCount = rs.getInt( 1 );
	    }

	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( rs != null ) {
		    rs.close();
		}
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( Exception e ) {
		e.printStackTrace();
	    }
	}

	return totalCount;
    }
    
	public void increaseGroupOrder( Integer groupNo, Integer orderNo ){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set order_no = order_no+1 where group_no = ? and order_no > ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, groupNo );
			pstmt.setInt(2, orderNo );
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
	}

    private Connection getConnection() throws SQLException {
	Connection conn = null;
	try {
	    // 1. 드라이버 로딩
	    Class.forName( "com.mysql.jdbc.Driver" );

	    // 2. 연결하기
	    String url = "jdbc:mysql://localhost/webdb";
	    conn = DriverManager.getConnection( url, "webdb", "webdb" );
	} catch ( ClassNotFoundException e ) {
	    System.out.println( "드러이버 로딩 실패:" + e );
	}

	return conn;
    }

    public void updateHit(long no) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	try {
		conn = getConnection();
		
		String sql = "update board set hit = hit + 1 where no = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setLong(1, no);
		
		pstmt.executeUpdate();
	} catch (SQLException e) {
		System.out.println( "error:" + e );
	} finally {
		try {
			if( pstmt != null ) {
				pstmt.close();
			}
			if( conn != null ) {
				conn.close();
			}
		} catch ( SQLException e ) {
			System.out.println( "error:" + e );
		}  
	}
    }

}
