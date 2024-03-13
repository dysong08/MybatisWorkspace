package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.common.AESCryptor;
import com.kh.member.model.service.MemberServiceImpl;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(urlPatterns = "/login.me" , name = "loginServlet")

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Member m = Member
				.builder()
				.userId(request.getParameter("userId"))
				.userPwd(request.getParameter("userPwd"))
				.build();
		
		
		Member loginUser = new MemberServiceImpl().loginMember(m);
		
		if(loginUser == null) {
			request.getSession().setAttribute("alertMsg", "로그인 실패!");
		}else {
			// 
			loginUser.setEmail(AESCryptor.decrypt(loginUser.getEmail()));
			request.getSession().setAttribute("loginUser", loginUser);
		}
		
		response.sendRedirect("/mybatis");
		
		
		
		
	}

}
