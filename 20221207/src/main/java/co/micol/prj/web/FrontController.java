package co.micol.prj.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.MainCommand;
import co.micol.prj.common.Command;
import co.micol.prj.member.command.AjaxMemberIdCheck;
import co.micol.prj.member.command.MemberJoin;
import co.micol.prj.member.command.MemberJoinForm;
import co.micol.prj.member.command.MemberList;


//@WebServlet("*.do")
public class FrontController extends HttpServlet {
	//FrontController : 요청 분석하고 누구한테 실행할지 결정해주는것 
	//Command : 저기가서 일을하고 처리되었어요 라고 하는 아이
	private static final long serialVersionUID = 1L;
    private HashMap<String, Command> map = new HashMap<String, Command>();   
   
    public FrontController() {
        super();
      
    }

	public void init(ServletConfig config) throws ServletException {
		//명령집단 보관하는곳 map.put(K,V)
		map.put("/main.do", new MainCommand()); //처음 실행하는 페이지 
		map.put("/memberList.do", new MemberList()); //멤버목록 보기 
		map.put("/memberJoinForm.do", new MemberJoinForm());
		map.put("/AjaxMemberIdCheck.do", new AjaxMemberIdCheck()); //회원아이디 중복체크
		map.put("/memberJoin.do", new MemberJoin()); // 회원가입 처리 
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청을 분석, 실행, 결과를 돌려주는 곳 
		request.setCharacterEncoding("utf-8"); //한글깨짐방지
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String page = uri.substring(contextPath.length());
		
		Command command = map.get(page);
		String viewPage = command.exec(request, response);
				//viewPage = > "main/main" 이다
		
		//view Resolve(어떤 페이지를 보여줄건지하는것)start
		if(!viewPage.endsWith(".do")) {
			if(viewPage.startsWith("Ajax:")) {
				//ajax
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print(viewPage.substring(5));
				return;
			}else if(!viewPage.endsWith(".tiles")){
				viewPage = "WEB-INF/views/" + viewPage + ".jsp"; //타일즈 적용안하는 것 	
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect(viewPage);
		}
		//view Reslove end
	}

}
