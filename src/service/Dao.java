package service;

import java.sql.SQLException;
import java.util.ArrayList;

import vo.Member;

public interface Dao {
	void con();
	void discon();
	void insert(Member m);
	Member selectMember(String id);
	void update(Member m);
	void delete(String id);
	ArrayList selectAll(int type);
}
