package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import vo.Member;
import vo.Employee;
import vo.Professor;
import vo.Student;

public class MemberDao implements Dao {

	Connection con;
	PreparedStatement pstmt;
	
	public MemberDao() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("dirver loading...");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void con() {
		// TODO Auto-generated method stub
		try
		{
			con  = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr", "hr");
			System.out.println("connection...");
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

	@Override
	public void discon() {
		// TODO Auto-generated method stub	
		try
		{
			con.close();
			pstmt.close();
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public void insert(Member m)  {
		// TODO Auto-generated method stub
		try {
			con();
			String sql = "insert into school(id,name, tel, addr, type, etc)"+"values(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getName());
			pstmt.setString(3, m.getTel());
			pstmt.setString(4, m.getAddr());
			pstmt.setInt(5, m.getType());
			
			if (m instanceof Professor){
				pstmt.setString(6, ((Professor)m).getDept());
			}else if(m instanceof Student){
				pstmt.setString(6, ((Student)m).getSchool());
			}else if(m instanceof Employee){
				pstmt.setString(6, ((Employee)m).getJob());
			}
			pstmt.executeUpdate();
			discon();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Member selectMember(String id) {
		// TODO Auto-generated method stub
		ResultSet rs = null;		
		Member m = null;
		
		try
		{
			con();
			String sql = "select * from school where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt("type") == 1){
					m = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6));
				}else if(rs.getInt("type") == 2){
					m = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6));
				}else if(rs.getInt("type") == 3){
					m = new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6));
				}
			}
				
			rs.close();
			discon();
		}
		catch(SQLException e)
		{
				e.printStackTrace();
		}
		return m;
	}

	@Override
	public void update(Member m) {
		// TODO Auto-generated method stub;
		try
		{
			con();
			String sql = "update school set tel=?, addr = ?, etc=? where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getTel());
			pstmt.setString(2, m.getAddr());
			if(m.getType() == 1){
				pstmt.setString(3, ((Student) m).getSchool());
			}else if(m.getType() == 2){
				pstmt.setString(3, ((Professor) m).getDept());
			}else if(m.getType() == 3){
				pstmt.setString(3, ((Employee) m).getJob());
			}
			int result = pstmt.executeUpdate();
			discon();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		try
		{
			con();
			String sql = "delete from school where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			int result = pstmt.executeUpdate();
			this.discon();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList selectAll(int type) {
		// TODO Auto-generated method stub
		ArrayList list = new ArrayList();
		ResultSet rs = null;
		try
		{
			con();
			String sql = "select * from school where type = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, type);
			rs = pstmt.executeQuery();
			
			if(type == 1){
				while(rs.next()){
					list.add(new Student(rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6)));
				}
			}else if(type == 2){
				while(rs.next()){
					list.add(new Professor(rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6)));
				}
			}else if(type == 3){
				while(rs.next()){
					list.add(new Employee(rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6)));
				}
			}
			
			discon();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}
}
