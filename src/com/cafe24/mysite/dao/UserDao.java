package com.cafe24.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cafe24.mysite.vo.UserVo;

public class UserDao {


    public boolean update(UserVo vo) {
	boolean result = false;

	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = "update users set name = ?, password = password(?), gender=? where no =?";
	    pstmt = conn.prepareStatement( sql );
	    pstmt.setString( 1, vo.getName() );
	    pstmt.setString( 2, vo.getPassword() );
	    pstmt.setString( 3, vo.getGender() );
	    pstmt.setLong( 4, vo.getNo() );

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
    
    public UserVo get( Long no ) {
	UserVo result = null;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
	    conn = getConnection();
	    String sql = "select no, name, email, gender from users where no = ? ";
	    pstmt = conn.prepareStatement( sql );
	    
	    pstmt.setLong( 1, no );

	    rs = pstmt.executeQuery();
	    
	    if( rs.next() ) {
		result = new UserVo();
		result.setNo( rs.getLong( 1 ) );
		result.setName( rs.getString( 2 ) );
		result.setEmail( rs.getString( 3 ) );
		result.setGender( rs.getString( 4 ) );
	    }
	    
	} catch ( Exception e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if( rs != null ) {
		    rs.close();
		}
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
    
    public UserVo get( String email, String password ) { 
	UserVo result = null;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
	    conn = getConnection();
	    String sql = "select no, name from users where email=? and password=password(?)";
	    pstmt = conn.prepareStatement( sql );
	    
	    pstmt.setString( 1, email );
	    pstmt.setString( 2, password );

	    rs = pstmt.executeQuery();
	    
	    if( rs.next() ) {
		result = new UserVo();
		result.setNo( rs.getLong( 1 ) );
		result.setName( rs.getString( 2 ) );
		
	    }
	    
	} catch ( Exception e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if( rs != null ) {
		    rs.close();
		}
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

    
    public boolean insert(UserVo vo) {
	boolean result = false;

	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = "insert into users values( null, ?, ?, password(?), ?, now() )";
	    pstmt = conn.prepareStatement( sql );
	    pstmt.setString( 1, vo.getName() );
	    pstmt.setString( 2, vo.getEmail() );
	    pstmt.setString( 3, vo.getPassword() );
	    pstmt.setString( 4, vo.getGender() );

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

}
