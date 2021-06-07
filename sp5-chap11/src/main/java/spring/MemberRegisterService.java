package spring;

import java.time.LocalDateTime;
import spring.AmazonSES;

public class MemberRegisterService {
	private MemberDao memberDao;
	private AmazonSES ses;
	
	public MemberRegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public Long regist(RegisterRequest req) {
		ses=new AmazonSES();
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(), req.getPassword(), req.getName(), 
				LocalDateTime.now());
		memberDao.insert(newMember);
		ses.sendEmail(newMember.getEmail());
		return newMember.getId();
	}
}
