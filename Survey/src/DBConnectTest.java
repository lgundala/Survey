import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
public class DBConnectTest {

	/**
	 * @author Amulya
	 * attempt to excute basic mysql queries
	 * 
	 */
	private static Session doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
	{
		/*This is one of the available choices to connect to mysql
		 * If you think you know another way, you can go ahead*/
		final JSch jsch = new JSch();
		java.util.Properties configuration = new java.util.Properties();
		configuration.put("StrictHostKeyChecking", "no");

		Session session = jsch.getSession( strSshUser, strSshHost, 22 );
		session.setPassword( strSshPassword );

		session.setConfig(configuration);
		session.connect();
		session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
		return session;
	}


	public static void main(String[] args) throws SQLException {
		if (args.length<5){
			System.out.println("Usage DBConnectTest <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password>  <yourportnumber>");
		}
		else{
			Connection con = null;
			Session session = null;
			try
			{
				String strSshUser = args[0];                  // SSH loging username
				String strSshPassword = args[1];                   // SSH login password
				String strSshHost = "onyx.boisestate.edu";          // hostname or ip or SSH server
				int nSshPort = 22;                                    // remote SSH host port number
				String strRemoteHost = "localhost";  // hostname or ip of your database server
				int nLocalPort = 3371	;                                // local port number use to bind SSH tunnel
				int nRemotePort = Integer.parseInt(args[4]);                               // remote port number of your database 
				String strDbUser = args[2];                    // database loging username
				String strDbPassword = args[3];                    // database login password

				session = DBConnectTest.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);

				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:"+nLocalPort, strDbUser, strDbPassword);


				System.out.println("successful");
				java.sql.Statement stmt = null;
				stmt = con.createStatement();
				/*READ FROM TABLE*/
				
				ResultSet resultSet = stmt.executeQuery("select * from `COMPANY`.`EMPLOYEE`");
				ResultSetMetaData rsmd = resultSet.getMetaData();

				int columnsNumber = rsmd.getColumnCount();
				while (resultSet.next()) {
					for (int i = 1; i <= columnsNumber; i++) {
						if (i > 1) System.out.print(",  ");
						String columnValue = resultSet.getString(i);
						System.out.print(columnValue + " " + rsmd.getColumnName(i));
					}
					System.out.println(" ");
				}
				
//				/* update */
//				updateTable(con);
//				/*TO INSERT INTO TABLES
//				 * You can also read from a file and store in a data structure of your choice*/
//				String[] data = null;
//				insertQuestionsList(con,data);
				
				con.close();
				session.disconnect();
			}
			catch( Exception e )
			{
				

				e.printStackTrace();
			}
			finally{
				con.close();
				session.disconnect();
			}

		}
	}
	/*update works only if there is no foereign key containts*/
	/*also, without 'where', mysql tend to work in a safe mode which doesn't allow to update'*/
		private static void updateTable(Connection con) throws SQLException {
			/* these statements can also be used to load data from a file. Just re-write the query as per your requirement*/
//			  String query = "UPDATE `COMPANY`.`DEPARTMENT` SET `DNUMBER` = `DNUMBER` + 1 where `DNUMBER`>9;";
			  java.sql.Statement stmt = null;
			  stmt = con.createStatement();
//			  String sql = "UPDATE `COMPANY`.`DEPARTMENT` SET `DNUMBER` = `DNUMBER` + 1 where `DNUMBER`>9;";
			  String sql = "LOAD DATA INFILE '/home/LaxmiamulyaGundala/Documents/tweets.csv' INTO TABLE `tweets`.`tweets` COLUMNS TERMINATED BY '|' LINES TERMINATED BY '\n'";
			  stmt.execute(sql);

			  
		
	}


		private static void insertQuestionsList(Connection con, String[] data) throws SQLException {
			  String sql;
			  java.sql.Statement stmt = null;
			  stmt = con.createStatement();
			  for(int i=0;i<data.length;i++){
			   sql = "INSERT INTO `COMPANY`.`DEPT_LOCATIONS`(`DNUMBER`,`DLOCATION`)VALUES("+i+",'"+data[i]+"')";
		      stmt.execute(sql);
			  }
			  


	}












}
