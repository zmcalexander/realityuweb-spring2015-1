package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obj.Group;
import dao.GroupsDAO;

/**
 * Servlet implementation class DeleteAllExpiredSurvlet
 * 
 * 
 *Used to Delete All Groups 
 * in DeleteGroups.jps
 * 
 */



@WebServlet("/DeleteAllExpiredSurvlet")
public class DeleteAllExpiredSurvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAllExpiredSurvlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Group expireCheck=new Group();		            
		try {
			expireCheck.DeleteGroup();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("In Delete Part");
        System.out.println("Yay! Login Valid.");
    	RequestDispatcher rd = getServletContext().getRequestDispatcher("/DeleteGroups.jsp");
    	rd.forward(request, response);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //   ==========================  doGet() Method  ============================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Calls processRequest() Method
        processRequest(request, response);
    } //end doGet

    //   ==========================  doPost() Method  ============================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Calls processRequest() Method
        processRequest(request, response);
    } //end doPost

}
