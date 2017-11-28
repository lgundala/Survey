import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
 
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
public class DBConnect2 {

	/**
	 * @param args
	 */
	private static void doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
	  {
	    final JSch jsch = new JSch();
	    java.util.Properties configuration = new java.util.Properties();
	    configuration.put("StrictHostKeyChecking", "no");
	    
	    Session session = jsch.getSession( strSshUser, strSshHost, 22 );
	    session.setPassword( strSshPassword );
	    
	   session.setConfig(configuration);
	    session.connect();
	  /*  final Properties config = new Properties();
	    config.put( "StrictHostKeyChecking", "no" );
	    session.setConfig( config );
	     */
	   //session.connect();
	    session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
	  }
	   
	  public static void main(String[] args)
	  {
		 
	  }
	  public static void run(String surveyfile, String surveytype) {
	    System.out.println("survey file"+surveyfile);
		  try
	    {
	      String strSshUser = "nathaz";                  // SSH loging username
	      String strSshPassword = "nDXeFPuU";                   // SSH login password
	      String strSshHost = "onyx.boisestate.edu";          // hostname or ip or SSH server
	      int nSshPort = 22;                                    // remote SSH host port number
	      String strRemoteHost = "localhost";  // hostname or ip of your database server
	      int nLocalPort = 3366;                                // local port number use to bind SSH tunnel
	      int nRemotePort = 4964;                               // remote port number of your database 
	      String strDbUser = "root";                    // database loging username
	      String strDbPassword = "Tsunami10";                    // database login password
	       
	      DBConnect2.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
	       
	      Class.forName("com.mysql.jdbc.Driver");
	      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:"+nLocalPort, strDbUser, strDbPassword);
	      
	      /*read questions file*/
	    //  BufferedReader br = null;
	  		String line = "";
	  		String tabSplitBy = "	";
	  		String[] data = null;
	  	
		 java.sql.Statement stmt = null;
		 stmt = con.createStatement();
		  List<QuestionsListBean> quesBeanlist = new ArrayList<QuestionsListBean>();
		  List<SurveyLinkSheetBean> surveylinksheet = new ArrayList<SurveyLinkSheetBean>();
		  List<QuestionsSelectedBean> quesSelectedbean = new ArrayList<QuestionsSelectedBean>();
		  ResultSet resultSet = stmt.executeQuery("select * from `nathaz`.`QuestionsList`");
		  //ResultSet resultSet = statement.executeQuery();
		//  System.out.println("result set contents"+resultSet.next());
		  resultSet.next();
	    if(!resultSet.next()){
	    	BufferedReader br = new BufferedReader(new FileReader("questionslist.tsv"));
			while ((line = br.readLine() ) != null) {
				        // use tab as separator
					 data = line.split(tabSplitBy);
					
					for(int i=1;i<data.length;i++){
					//System.out.println( data[i]);
					}
					 insertQuestionsList(con,data);
					
				
				}
			 resultSet = stmt.executeQuery("select * from `nathaz`.`QuestionsList`");
	    }
	    resultSet = stmt.executeQuery("select * from `nathaz`.`QuestionsList`");
	        while (resultSet.next()) {
	            quesBeanlist.add(resultSetRow(resultSet));
	        }
		BufferedReader brh = new BufferedReader(new FileReader(surveyfile));
		/*read student responses*/
		if(surveytype.equalsIgnoreCase("student"))
		{
			line = brh.readLine();
			data = line.split(tabSplitBy);
			insertQuestionSelected(con,data, quesBeanlist, surveyfile);
			
			/*for(int i=0;i<data.length;i++){
				System.out.println( data[i]+" valueof i "+i);
				
				}*/
			ResultSet quesSel = stmt.executeQuery("select * from `nathaz`.`QuestionSelected`");
			 while (quesSel.next()) {
				 quesSelectedbean.add(quesSelRow(quesSel));
		        }
			
			//quesSelectedbean.add(addQuestionSelected(data));
			
			int row=0;
			while ((line = brh.readLine() ) != null) {
		        // use tab as separator
				row++;
			//	System.out.println(line);
			 data = line.split(tabSplitBy);
			
			/*for(int i=0;i<data.length;i++){
			System.out.println( data[i]+" valueof i "+i);
			}*/
			 int surveyid = Integer.parseInt(surveyfile.substring((surveyfile.indexOf("-")+1),(surveyfile.indexOf("0")-1)));
			 
			insertResponse(con, data, quesSelectedbean,row,surveyid);
			
			
			
			
		
		}
			System.out.println("done");
		}
		/*read instructor responses */
		if(surveytype.equalsIgnoreCase("instructor"))
		{
			line = brh.readLine();
			int x = 1;
			while ((line = brh.readLine() ) != null) {
		        // use tab as separator
			 data = line.split(tabSplitBy);
			
			for(int i=1;i<data.length;i++){
			//System.out.println( data[i]);
			}
			insertInstructor(con,data,x);
			x++;
			
		
		}
		}
		/*get survey links and ids*/
		if(surveytype.equalsIgnoreCase("survey link"))
		{
			line = brh.readLine();
			
			while ((line = brh.readLine() ) != null) {
		        // use tab as separator
			 data = line.split(tabSplitBy);
			
			for(int i=1;i<data.length;i++){
			//System.out.println( data[i]);
			}
			insertRefSurvey(con,data);
			surveylinksheet.add(addRow(data));
			
		
		}
		}
	  
	      
	      
	      
	      
	      
	      
	      con.close();
	    }
	    catch( Exception e )
	    {
	    	
	      e.printStackTrace();
	    }
	    
	    
	  
	  	      
	   /* finally
	    {
	      System.exit(0);
	    }*/
	  
	  
	  }
	  


	private static void insertResponse(Connection con, String[] data,
			List<QuestionsSelectedBean> quesSelectedbean, int row, int surveyid) throws SQLException {
		
		 String sql;
		 int refsurvey =0;
		 int qselId = 0;
		 int IntSurId=0;
		 int QuestId=0;
		  java.sql.Statement stmt = null;
		  stmt = con.createStatement();
		  for(int i=1;i<data.length;i++){
			  for (QuestionsSelectedBean quesSelected : quesSelectedbean) {
				 refsurvey = Integer.parseInt(quesSelected.getRefSurveyId());
				 //System.out.println(refsurvey);
				 if(surveyid == refsurvey){
					// System.out.println("match found");
					 qselId=Integer.parseInt(quesSelected.getQuestionselectedID());
					 IntSurId=Integer.parseInt(quesSelected.getInstuctorSurveyId());
					 QuestId=Integer.parseInt(quesSelected.getQuestionId());
					if(i==qselId){
					//	 System.out.println(refsurvey+" "+qselId+" "+IntSurId+" "+QuestId+" i value:"+i);
						// System.out.println(data[i]);
					 sql = "INSERT INTO `nathaz`.`Response`(`ResponseId`,`ResponseValue`,`QuestionselectedID`,`RefSurveyId`,`InstructorSurveyId`,`QuestionId`)VALUES("+row+",'"+data[i]+"',"+qselId+","+refsurvey+","+IntSurId+","+QuestId+")";
					 stmt.execute(sql);
					 }
				 }
			  }
			  
		  
	      
		  }
	}

	private static QuestionsSelectedBean quesSelRow(ResultSet quesSel) throws SQLException {
		QuestionsSelectedBean qbean = new QuestionsSelectedBean();
		 qbean.setInstuctorSurveyId(quesSel.getString("InstructorSurveyId"));
		 qbean.setQuestionId(quesSel.getString("QuestionId"));
		 qbean.setQuestionselectedID(quesSel.getString("QuestionselectedID"));
		 qbean.setRefSurveyId(quesSel.getString("RefSurveyId"));
		return qbean;
	}

	private static SurveyLinkSheetBean addRow(String[] data) {
		  SurveyLinkSheetBean bean = new SurveyLinkSheetBean(); 
		    bean.setInstructorname(data[0]);
		    bean.setOrganization(data[1]);
		    bean.setInstructorEmail(data[2]);
		    bean.setStudentSurveyId(data[3]);
		    bean.setSurveyLink(data[4]);
		    bean.setGeneratedId(data[5]);	    
		    return bean;
	}

	private static void insertInstructor(Connection con, String[] data, int x) throws SQLException {
		  String sql;
		  java.sql.Statement stmt = null;
		  stmt = con.createStatement();
		  for(int i=0;i<data.length;i++){
		  
		  }
		   sql = "INSERT INTO `nathaz`.`Instructor Survey`(`InstructorSurveyId`,`InstructorName`,`InstructorEmail`)VALUES("+x+",'"+data[2]+"','"+data[4]+"')";
	      stmt.execute(sql);
		  
		  
	}

	private static void insertQuestionSelected(Connection con, String[] data, List<QuestionsListBean> quesBeanlist, String filename) throws SQLException {
		String sql;
		  java.sql.Statement stmt = null;
		  stmt = con.createStatement();
		  System.out.println(filename.substring((filename.indexOf("-")+1),(filename.indexOf("0")-1)));
		 int surveyid = Integer.parseInt(filename.substring((filename.indexOf("-")+1),(filename.indexOf("0")-1)));
		 System.out.println(surveyid);
		   String quesDesc;
	        for(int j=0;j<data.length;j++){
	        for (QuestionsListBean questionsListBean : quesBeanlist) {
				
	        	quesDesc= questionsListBean.getQuestionDesc();
	        	//System.out.println(quesDesc);
	        	//System.out.println(data[j]);
	        	if(data[j].equalsIgnoreCase(quesDesc)){
	        		sql = "INSERT INTO `nathaz`.`QuestionSelected`(`QuestionselectedID`,`RefSurveyId`,`InstructorSurveyId`,`QuestionId`)VALUES("+j+","+surveyid+","+surveyid+","+Integer.parseInt(questionsListBean.getQuesitonId())+")";
	        		stmt.execute(sql);
	       	      //System.out.println("match found");
	        	}
	        	
	        	
	        	
			}
	        }
	        
	      System.out.println("done"); 
	     
	    
	}

	public static QuestionsListBean resultSetRow(ResultSet resultSet) throws SQLException {
		QuestionsListBean bean = new QuestionsListBean(); 
	    bean.setQuesitonId(resultSet.getString("QuestionId"));
	    bean.setQuestionDesc(resultSet.getString("QuestionDescription"));
	    
	    return bean;
	}
		  
		
	

	private static void insertQuestionsList(Connection con, String[] data) throws SQLException {
		  String sql;
		  java.sql.Statement stmt = null;
		  stmt = con.createStatement();
		  for(int i=0;i<data.length;i++){
		   sql = "INSERT INTO `nathaz`.`QuestionsList`(`QuestionId`,`QuestionDescription`)VALUES("+i+",'"+data[i]+"')";
	      stmt.execute(sql);
		  }
		  
		
		
	}

	private static void insertRefSurvey(Connection con, String[] data) throws SQLException {
		  String sql;
		  java.sql.Statement stmt = null;
		  stmt = con.createStatement();
		 System.out.println(data[4]);
		 System.out.println(data[5]);
		  sql = "INSERT INTO `nathaz`.`ReferenceSurvey`(`RefSurveyId`,`RefSurveyLink`)VALUES("+data[5]+",'"+data[4]+"')";
	      stmt.execute(sql);
		
	}

	public static void getSurveyDetails(String surveyfile, String surveytype) throws SQLException{
		  //DBConnect2 db = new DBConnect2();
		  DBConnect2.run(surveyfile,surveytype);
	  }
	  

}
