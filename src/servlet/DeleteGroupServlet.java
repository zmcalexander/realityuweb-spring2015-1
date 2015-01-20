package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GroupsDAO;

/**
 * Servlet implementation class DeleteGroupServlet
 * 
 * Work in progress
 * This Servlet was going to be able to delete one
 * Group at a time via button or check box
 * in the DeleteGroup.jsp
 * But didn't have time to finish.
 * 
 */
@WebServlet("/DeleteGroupServlet")
public class DeleteGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try{
    		String delete=(request.getParameter("deleteid"));
    		if(!((request.getParameter("deleteid"+delete))==null)){	
    	GroupsDAO gd = new GroupsDAO();
    	gd.delete(delete);
    	}
    	}
    	catch(Exception e){
    		 System.out.println(e);
    	}
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
