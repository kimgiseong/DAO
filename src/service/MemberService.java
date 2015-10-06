package service;

import java.util.ArrayList;

import vo.Member;

public class MemberService implements Service {
	private Dao dao;
	
	public MemberService(){
		dao = new MemberDao();
	}

	@Override
	public void addMember(Member m) {
		// TODO Auto-generated method stub
		dao.insert(m);
	}

	@Override
	public Member getMember(String id) {
		// TODO Auto-generated method stub
		return dao.selectMember(id);
	}

	@Override
	public void editMember(Member m) {
		// TODO Auto-generated method stub
		dao.update(m);
	}

	@Override
	public void delMember(String id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}

	@Override
	public ArrayList getMember(int type) {
		// TODO Auto-generated method stub
		return dao.selectAll(type);
	}
	
}
