package co.micol.prj.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.common.Command;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.service.MemberVO;
import co.micol.prj.member.serviceImpl.MemberServiceImpl;

public class MemberJoin implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		//회원가입 처리 
		MemberService dao = new MemberServiceImpl();
		MemberVO vo = new MemberVO();
		int n = 0;
		//String viewPage = null; //돌려줄 페이지 나중에사용함 
		String message = null; //메세지 담는곳 
		
		vo.setMemberId(request.getParameter("memberId"));
		vo.setMemberName(request.getParameter("memberName"));
		vo.setMemberPassword(request.getParameter("memberPassword"));
		if(request.getParameter("memberAge")!="") {
			vo.setMemberAge(Integer.valueOf(request.getParameter("memberAge")));
		}
		vo.setMemberAddress(request.getParameter("memberAddress"));
		vo.setMemberTel(request.getParameter("memberTel"));
		vo.setMemberAuthor("USER"); //회원가입하면 무조건 USER다 
		
		n = dao.memberInsert(vo);
		
		if(n != 0) {
			//message = "회원가입이 성공적으로 처리되었습니다.";
			return "memberList.do";
		}else {
			message = "회원가입이 실패했습니다.";
		}
		request.setAttribute(message, message);
		return "member/memberJoin.tiles";
	}

}
