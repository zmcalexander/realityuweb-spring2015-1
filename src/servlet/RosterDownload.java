package servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dao.DbUtil;
import dao.GroupsDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class SurveyDownload
 */
/*===========================================
 This servlet gathers all the students
 based on that groups id and pulls out
 the information in the data base and puts
 it in a word doc.
============================================*/
@WebServlet("/RosterDownload")
public class RosterDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static HttpServletRequest request;  
    /**
     * @see HttpServlet#HttpServlet()
     */
	

    public RosterDownload() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("application/octet-stream");
    	response.setHeader("Content-Disposition",
    	"attachment;filename=ClassRoster.doc");
    	
    try{	
	System.out.println("MADE IT THIS FAR!!");
	System.out.println("Finding Group ID .. maybe");
	HttpSession ses1 = request.getSession();
	int groupID = (int)ses1.getAttribute("groupids");
	System.out.println("======"+groupID);
	
	StringBuffer sb = generateCsvFileBuffer(groupID);
	InputStream in = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
	ServletOutputStream out = response.getOutputStream();
	 
	byte[] outputByte = new byte[1];
	//copy binary contect to output stream
	while(in.read(outputByte, 0, 1) != -1)
	{
		out.write(outputByte, 0, 1);
	}
	in.close();
	out.flush();
	out.close();
    }
    catch(Exception e){
    	System.out.println("error"+e);
    }
    }
    //   ==========================  doGet() Method  ============================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Calls processRequest() Method
        processRequest(request, response); 
    }
    private static StringBuffer generateCsvFileBuffer(int groupID)
            
    {
 			
    	StringBuffer writer = new StringBuffer();
    		Connection conn = null;
    		PreparedStatement stmt = null;
    		ResultSet rs = null; 		
    		String sql = "";
			
			System.out.println("LOOK HERE FOR GROUP ID!!");
			GroupsDAO grpd = new GroupsDAO();		
    		try {
    			// Load Driver & Connect to Dbase
    			conn = DbUtil.createConnection();
    			// Create SQL Statement
    			
    	    	System.out.println("======"+groupID);

    			sql = "SELECT * FROM Survey where groupID ="+groupID+"";
    			stmt = conn.prepareStatement(sql);
    			System.out.println("SQL: " + sql);

    			// Execute Statement - Get ResultSet by Column Name
    			 rs = stmt.executeQuery();
    			 
    			String name = grpd.gName(Integer.toString(groupID));
    			//Process the ResultSet
    			
    			writer.append("Reality U Roster For Group :" + name ); 
    			while (rs.next()) {	
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("First Name:"+ rs.getString(2));  
				    	writer.append(" , ");
				    	writer.append("Last Name:"+ rs.getString(3));
				    	writer.append(" , ");
				    	writer.append("Date Of Birth:" + rs.getString(4));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append('\n');
     
     				}
    			DbUtil.close(rs);
    			DbUtil.close(stmt);
    			DbUtil.close(conn);
    			System.out.println("Closed Resources");
    		
    		} catch (Exception e) {
    			// Handle Errors for Class
    			System.out.println("Class Error. Current DB: " + e);
    		} finally {
    			// Close ResultSet, Query, and Database Connection
    			DbUtil.close(rs);
    			DbUtil.close(stmt);
    			DbUtil.close(conn);
    			System.out.println("Closed Resources");
    		} // End Try/Catch
    		System.out.println("Everything Closed");
    		return writer;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
  
    //   ==========================  doPost() Method  ============================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Calls processRequest() Method
        processRequest(request, response);
    } //end doPost

}