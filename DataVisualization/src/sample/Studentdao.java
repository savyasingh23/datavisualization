package sample;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Studentdao {

	public boolean deleteData(int i) {
		int z=0;
		String SQL = "DELETE from student WHERE stu_id="+i;
		Connection conn;
		Student st = new Student();
		try {
			conn = DBConnect.connect();

			PreparedStatement preparedStatement = conn.prepareStatement(SQL);
			z=preparedStatement.executeUpdate();
			System.out.println(st);
			conn.close();

		}catch(SQLException e) {
			System.out.println("Error in DB connection");
		}
		if(z!=0) {
			return true;}
		else {
			return false;
		}
	}

	public void insertRecord(String n, double p, int b, String c, String br, int y, String g, int ay) {

		Connection conn;
		String SQL = "INSERT INTO student VALUES ("+0+",'"+n+"',"+p+",'"+b+"','"+br+"','"+c+"',"+y+",'"+g+"',"+ay+")";
		try {
			conn = DBConnect.connect();

			Statement statement = conn.createStatement();
			statement.executeUpdate(SQL);

			conn.close();
		}catch(SQLException e) {
			System.out.println("Error in DB connection");
		}
	}

	public boolean updateRecord(String n, double p, int b, String c, String br, int y, String g, int ay,int i) {
		Connection conn;int z=0;
		String SQL = "UPDATE student SET Name="+"'"+n+"',percentage="+p+",semester="+b+",branch='"+br+"',course='"+c+"',year="+y+",Gender='"+g+"',admission_year="+ay+" WHERE stu_id="+i;
		//UPDATE `datavisualization`.`student` SET `Name` = 'sunita' WHERE (`stu_id` = '1');
		try {
			conn = DBConnect.connect();

			Statement statement = conn.createStatement();
			z=statement.executeUpdate(SQL);

			conn.close();
		}catch(SQLException e) {
			System.out.println("Error in DB connection");
		}
		if(z!=0) {
			return true;}
		else {
			return false;
		}

	}
	public boolean checkUser(int i) {
		boolean z=false,x=false;
		String SQL = "Select * from student WHERE stu_id="+i;
		Connection conn;
		try {
			conn = DBConnect.connect();

			Statement statement = conn.createStatement();
			z=statement.execute(SQL);
			conn.close();
			if(z) {
				x=true;
			}
			else{
				x=false;
			}

		}catch(SQLException e) {
			System.out.println("Error in DB connection");
		}
		if(x) {
			return true;
		}
		else {
			return false;
		}

	}
}
