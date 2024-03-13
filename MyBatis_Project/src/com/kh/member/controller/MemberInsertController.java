package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.common.AESCryptor;
import com.kh.member.model.service.MemberServiceImpl;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet(urlPatterns = "/insert.me", name = "insertServlet")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("WEB-INF/views/member/memberEnrollForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("UTF-8");
		// src/com/kh/common/filter/EncodingFilter.java
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		
		// email만 암호화하기
		email = AESCryptor.encrypt(email);
		
		String birthday = request.getParameter("birthday");
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		
		Member m = Member.builder()
					.userId(userId)
					.userPwd(userPwd)
					.userName(userName)
					.email(email)
					.birthday(birthday)
					.gender(gender)
					.phone(phone)
					.address(address)
					.build();
			
		int result = new MemberServiceImpl().insertMember(m);
		HttpSession session = request.getSession();
		
		
		if(result > 0) {
			// 성공
			session.setAttribute("alertMsg", "회원가입 성공");
		}else {
			// 실패
			session.setAttribute("alertMsg", "회원가입 실패");
		}
		
		response.sendRedirect(request.getContextPath());
		// 메인페이지로 이동시킴
		
		
		
		
	}

}
