package edu.kh.project.member.model.dao;

import static edu.kh.project.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.project.common.JDBCTemplate;
import edu.kh.project.member.model.vo.Member;

public class MemberDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public MemberDAO() {

		try {
			
			prop = new Properties();
			
			
			
			String filePath = JDBCTemplate.class.getResource("/edu/kh/project/sql/member-sql.xml").getPath();
			prop.loadFromXML(new FileInputStream(filePath));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/** 로그인 dao
	 * @param member
	 * @param conn
	 * @return loginMember
	 */
	public Member login(Member member, Connection conn) throws Exception {
		
//		MEMBER_NO, MEMBER_EMAIL, MEMBER_NICKNAME, MEMBER_TEL ,
//		MEMBER_ADDRESS, PROFILE_IMG , AUTHORITY ,TO_CHAR(ENROLL_DATEL, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분" SS"초"') AS ENROLL_DATE
		Member loginMember = null;
		
		try {
			String sql = prop.getProperty("login");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberEmail());
			pstmt.setString(2, member.getMemberPw());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				loginMember = new Member();
				loginMember.setMemberNo(rs.getInt("MEMBER_NO"));
				loginMember.setMemberEmail(rs.getString("MEMBER_EMAIL"));
				loginMember.setMemberNickname(rs.getString("MEMBER_NICKNAME"));
				loginMember.setMemberTel(rs.getString("MEMBER_TEL"));
				loginMember.setMemberAddress(rs.getString("MEMBER_ADDRESS"));
				loginMember.setProfileImage(rs.getString("PROFILE_IMG"));
				loginMember.setAuthority(rs.getInt("AUTHORITY"));
				loginMember.setEnrollDate(rs.getString("ENROLL_DATE"));
				
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return loginMember;
	}

	public int signUp(Connection conn, Member member) throws Exception {

		int result = 0;
		
		
		
		
		try {
			String sql = prop.getProperty("signUp");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberEmail());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberNickname());
			pstmt.setString(4, member.getMemberTel());
			pstmt.setString(5, member.getMemberAddress());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 내 정보 수정
	 * @param conn
	 * @param member
	 * @return result(내 정보 수정 결과)
	 * @throws Exception
	 */
	public int updateMember(Connection conn, Member member) throws Exception {

		int result = 0;
		
		try { 
			String sql = prop.getProperty("updateMember");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberNickname());
			pstmt.setString(2, member.getMemberTel());
			pstmt.setString(3, member.getMemberAddress());
			pstmt.setInt(4, member.getMemberNo());
			
			result = pstmt.executeUpdate();
		
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
