package muin.mvc.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import muin.mvc.model.dao.AuthoritiesDAO;
import muin.mvc.model.dao.MemberDAO;
import muin.mvc.model.dto.AuthorityDTO;
import muin.mvc.model.dto.MemberDTO;
import muin.mvc.model.dto.WishListDTO;
import muin.mvc.model.util.Constants;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private AuthoritiesDAO authoritiesDAO;
	/*
	 * 비밀번호 암호화를 위한 객체를 주입받는다 
	 */
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Override
	public MemberDTO findMemberById(String id) {		
		return memberDAO.findMemberById(id);
	}

	@Override
	public List<String> getAddressList() {		
		return memberDAO.getAddressList();
	}

	@Override
	public List<MemberDTO> findMemberListByAddress(String address) {		
		return memberDAO.findMemberListByAddress(address);
	}

	@Override
	public MemberDTO login(MemberDTO memberVO) {
		System.out.println(memberVO.getMemberEmail() +"/t" +memberVO.getMemberPwd());
		return memberDAO.login(memberVO);
	}

	@Override
	public int getMemberCount() {
		return memberDAO.getMemberCount();
	}

	@Override
	public void updateMember(MemberDTO vo) {
		memberDAO.updateMember(vo);
	}
    @Transactional
	@Override
	public void registerMember(MemberDTO vo) {
    	//비밀번호 암호화
    	//System.out.println("vo.getPassword() : " + vo.getPassword());
        String encodedPassword = passwordEncoder.encode(vo.getMemberPwd());
        //System.out.println("encodedPassword : " + encodedPassword);
        vo.setMemberPwd(encodedPassword);
		memberDAO.registerMember(vo);		
		
		//권한등록
		/*AuthorityVO authority=new AuthorityVO(vo.getId(),"ROLE_MEMBER");
		memberDAO.registerRole(authority);*/
		authoritiesDAO.insertAuthority(new AuthorityDTO(vo.getMemberEmail(), Constants.ROLE_MEMBER));
		if(vo.getUserType().equals("1")) {
			authoritiesDAO.insertAuthority(new AuthorityDTO(vo.getMemberEmail(), Constants.ROLE_ADMIN));
		}			
	}

	@Override
	public String idcheck(String id) {
		int count=memberDAO.idcheck(id);
		return (count==0) ? "ok":"fail"; 	
	}

	@Override
	public List<AuthorityDTO> selectAuthorityByUsername(String username) {
		
		return authoritiesDAO.selectAuthorityByUserName(username);
	}

	@Override
	public List<MemberDTO> memberList() {
		
		return memberDAO.memberList();
	}

	@Override
	public boolean confirmPwd(String memberEmail, String memberPwd) {
		
		return memberDAO.confirmPwd(memberEmail,memberPwd);
	}

	@Override
	public boolean withdrawal(MemberDTO member) throws Exception {
		System.out.println("1");
		if(memberDAO.withdrawal(member)==1) {
			return false;
		} else {
			System.out.println("2");
			return true;
		}
	}

	@Override
	public int insertWishList(Long memberId, Long movieNo) {
		return memberDAO.insertWishList(memberId, movieNo);
	}

	@Override
	public WishListDTO checkWishList(Long memberId, Long movieNo) {
		return memberDAO.checkWishList(memberId, movieNo);
	}
	

	@Override
	public List<WishListDTO> myWishList(Long memberId) {
		return memberDAO.myWishList(memberId);
	}

	@Override
	public List<MemberDTO> listAll(String searchOption, String keyword) throws Exception {
		return memberDAO.listAll(searchOption,keyword);
	}

	@Override
	public int countMember(String searchOption, String keyword) throws Exception {
		return memberDAO.countMember(searchOption,keyword);
	}

	
}
