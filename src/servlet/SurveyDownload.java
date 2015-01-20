package servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.DbUtil;
import dao.GroupsDAO;
import dao.OccupationsDAO;
import dao.SurveysDAO;
import obj.Group;
import obj.Occupation;
import obj.Survey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Servlet implementation class SurveyDownload

 */
/*===========================================
This servlet gathers all the students surveys
based on that groups id and pulls out
the information in the data base and puts
it in a word doc.
============================================*/

@WebServlet("/SurveyDownload")
public class SurveyDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static HttpServletRequest request;  
    /**
     * @see HttpServlet#HttpServlet()
     */
	

    public SurveyDownload() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("application/octet-stream");
    	response.setHeader("Content-Disposition",
    	"attachment;filename=Surveys.doc");
    	
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
    
    	Group grp = null;
    	Survey surv = null;
    	SurveysDAO survd = new SurveysDAO();
    	List<String> lstCategories = new ArrayList<String>();
    	OccupationsDAO oc = new OccupationsDAO();
    	lstCategories = oc.findAllCategories();
    	//Create List of Lists (Occupation Name String List inside Categories List) to use in Occupation dropdown
    	//Contains occup name values for every category possibility
    	List<List<String>> loccp = new ArrayList<List<String>>();
    	loccp = oc.findOccupationNamesPerCategory();
    			
    	//Get list of all Occupations for dropdown of processed job (not based on Category)	
    	List<Occupation> lstOccupations = new ArrayList<Occupation>();
    	lstOccupations = oc.findAllOccupations();
    	SurveysDAO sd = new SurveysDAO();
    	StringBuffer writer = new StringBuffer();
    		Connection conn = null;
    		PreparedStatement stmt = null;
    		ResultSet rs = null; 		
    		Survey survey = new Survey();
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
    			String tname = grpd.tName(Integer.toString(groupID));
    			String cpname = grpd.cpName(Integer.toString(groupID)); 
    			String eventdate = grpd.eventdate(Integer.toString(groupID));  
    			//Process the ResultSet
    			while (rs.next()) {
    				
    				List<Occupation> lstOccpInfo = new ArrayList<Occupation>();
     				lstOccpInfo = oc.search("name", rs.getString(10)); //should only return one object
     				double monthlyTax = 0.0;
     				double monthlyNetAfterTax = 0.0;
     				double collegeLoan = 0.0;
     				double annualSalary = 0.0;
     				double monthlySalary = 0.0;
     				//In case survey object not in session
     				/*===================================
     				 * Has to do the algorithm as it pulls
     				 * in the surveys to get in the taxes
     				 * amount,checkbook,salary and so forth
     				 * based on the students input
     				 * because there isn't a field in the database
     				 * for these values because running it live
     				 * make the information more accurate if you had to 
     				 * resubmit anything.
     				 * 
     				 ===================================*/
     					if (rs.getString(11).equals("Yes")) {
     						monthlyTax = lstOccpInfo.get(0).getMarMonthlyTax();
     						monthlyNetAfterTax = lstOccpInfo.get(0).getMarAfterTax();
     					} else {
     						monthlyTax = lstOccpInfo.get(0).getSinMonthlyTax();
     						monthlyNetAfterTax = lstOccpInfo.get(0).getSinAfterTax();
     					}
     					annualSalary = lstOccpInfo.get(0).getAnnGrossSal();
     					monthlySalary = lstOccpInfo.get(0).getMonGrossSal();
     					collegeLoan = lstOccpInfo.get(0).getLoan();
     					// The Page layout matches perfectly with a word doc.
     					// that it will not run over another page while printing.
     					writer.append(System.getProperty("line.separator"));
				    	writer.append("Reality U Survey Results — Reality U Event Date: "+eventdate); 
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Class Information");
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Group Name: "+ name) ;
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Teacher: " + tname);
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Class Period:" + cpname);
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Student Information");
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	survey.setFname(rs.getString("fName"));
				    	writer.append("First Name:"+ rs.getString(2));  
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Last Name:"+ rs.getString(3));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Current GPA:"+ rs.getDouble(5));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Date Of Birth:" + rs.getString(4));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Gender:" + rs.getString(6));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append(System.getProperty("line.separator"));
				    	writer.append("Future Expected Education & Employment");
				    	writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append("Planned Education:" + rs.getString(8));
						writer.append(System.getProperty("line.separator"));
						writer.append("In your student survey, you chose "+ rs.getString(9));
						writer.append(System.getProperty("line.separator"));
						writer.append("as your preferred occupation."); 
						writer.append(System.getProperty("line.separator"));
						writer.append("Based on your GPA, education level, and more, your job was assigned as");
						writer.append(System.getProperty("line.separator"));
						writer.append("Assigned Occupation:"+rs.getString(10));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append("Future Expected Family Information");
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append("Married:"+ rs.getString(11));
						writer.append(System.getProperty("line.separator"));
						if(!(rs.getString(12).equals("0"))){
							writer.append("Spouse: "+ survd.findname(rs.getString(12)));
							}
							else{
								writer.append("Spouse: "+rs.getString(12));
							}
						writer.append(System.getProperty("line.separator"));
						writer.append("Children:" + rs.getString(13));
						writer.append(System.getProperty("line.separator"));
						writer.append("Number of Children:"+ rs.getInt(14));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append("Financial Status");
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append("Annual Salary:$"+annualSalary);
						writer.append(System.getProperty("line.separator"));
						writer.append("Monthly Salary:$"+monthlySalary);
						writer.append(System.getProperty("line.separator"));
						writer.append("Monthly Taxes:$"+monthlyTax);
						writer.append(System.getProperty("line.separator"));
						writer.append("Net Monthly Income:$"+monthlyNetAfterTax);
						writer.append(System.getProperty("line.separator"));
						//Gets the child support live
						if(rs.getString(11).equals("Divorced")&&(rs.getInt(14)>=1)){
							writer.append("Child Support:$"+(Math.round(annualSalary*.006)*rs.getInt(14)));
						}
						else{
							writer.append("Child Support:$"+rs.getDouble(21));
						}
						writer.append(System.getProperty("line.separator"));
						double checkBookEntry = monthlyNetAfterTax + rs.getDouble(21);
						writer.append("Checkbook Entry:$"+checkBookEntry);
						writer.append(System.getProperty("line.separator"));
						writer.append("Credit Score:$"+rs.getDouble(22));
						writer.append(System.getProperty("line.separator"));
						writer.append("Student Loans:$"+collegeLoan);
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
						writer.append(System.getProperty("line.separator"));
					

     
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