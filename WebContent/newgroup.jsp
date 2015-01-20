<%--*******************************************************************
 *	RealityUWeb: newgroup.jsp
 	Edited by Josh 9/28/14
 *  03/22/2014
 *******************************************************************--%>
<%@page import="java.util.List"%>
<%@page import = "java.util.GregorianCalendar"%>
<%@page import = "java.util.Calendar" %>
<% 
//RESET ALL SESSION OBJECTS TO NULL TO CLEAR OUT VALUES
obj.Group grpp = null;
List<obj.Survey> lstSurvs = null;
obj.Occupation ocp = null;
obj.Survey survey = null;
String mssg = null;
HttpSession ses1 = request.getSession();
//For newgroup.jsp
	//DON'T CLEAR FOR THIS PAGE
//For opengroup.jsp
ses1.setAttribute("openGrp", grpp);
ses1.setAttribute("lstSurveys", lstSurvs);
ses1.setAttribute("editGroupMsg", mssg);
ses1.setAttribute("isProcessed", mssg);
//For occupations.jsp
ses1.setAttribute("occupsMsg", mssg);
//For editoccups.jsp
ses1.setAttribute("editOccp", ocp);
ses1.setAttribute("editOccupsMsg", mssg);
//For surveyview.jsp
ses1.setAttribute("surveyviewGrp", grpp);
ses1.setAttribute("viewSurvey", survey);
ses1.setAttribute("surveyMssg", mssg);
//For surveprocessed.jsp	
ses1.setAttribute("surveyprocessGrp", grpp);
ses1.setAttribute("processSurvey", survey);
ses1.setAttribute("surveyProcMsg", mssg);

obj.Group newGrp =(obj.Group)session.getAttribute("newGrp");
%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">

<title>Communities In Schools - Reality U Admin New Group</title>
<meta charset="utf-8">
<!--Edge = render site in highest standards mode possible 8, 9..., chrome = use Google engine if installed-->

<meta http-equiv="pragma" content="no-cache">

<link rel="stylesheet" href="css/reset.css" media="screen">
<link rel="stylesheet" href="css/mainstyles.css" media="screen">

<!--FOR STICKY FOOTER IN OLDER IE BROWSERS (except IE 7 OK)-->
<!--[if (lt IE 9) & (!(IE 7))]>
	<style type="text/css">
		#wrapper {display:table; height:100%;}
	</style>
<![endif]-->


<!-- FORM VALIDATION -->
<script type="text/javascript">
	//Value of 'btn' set via onclick on submit/clear buttons at end of form
	var btn = "";
	
    function validate(myform) {
    	if (btn == "submit") {
       		//Validate Form only on 'submit' button (not for 'clear' button)
	        var num = 0;
	        var message = "";
	        if(myform.name.value == "") {
	            message += "- Group Name must be completed \n";
	            num = 1;
	        } 
	        if(myform.highschool.value == "") {
	            message += "- High School must be completed \n";
	            num = 1;
	        }
	        if(myform.teacher.value == "") {
	            message += "- Teacher must be completed \n";
	            num = 1;
	        }
	        if(myform.classPeriod.value == "") {
	            message += "- Period must be completed \n";
	            num = 1;
	        }	
	        if(myform.surveyStartDate.value == "") {
	            message += "- Survey Start Date must be completed \n";
	            num = 1;
	        }
	        if(myform.surveyEndDate.value == "") {
	            message += "- Survey End Date must be completed \n";
	            num = 1;
	        }
	        if(myform.eventDate.value == "") {
	            message += "- Event Date must be completed \n";
	            num = 1;
	        }
	
	        if (num == 1) { 
	            alert ("Please complete or correct the following required fields: \n\n"+message);
	            return false;
	        } else {
	            return true;
	        } //end if
    	} //end button if
    } //end func
</script>

</head>

<body>
<div id="wrapper">
 
<!--HEADER-->
<div id="header">

<img id="logoImg" src="images/cislogo.png" width="200" height="150" alt="Communities In Schools Logo">


<!--Header Text-->
<img id="headerText" src="images/realityuhead.png" width="600" height="80" alt="Reality University Program">
<!--REALITY U LOGO-->
<img id="logoImnewGrp" src="images/realityulogo.png" width="100" height="95" alt="Reality U Logo">

<!--NAVIGATION-->
<div id="nav">
  <ul>
  	<li><a href="index.jsp">Home</a></li>	
	<li><a href="adminhome.jsp">Admin Home</a></li>
	<li><a href="newgroup.jsp">New Group</a></li>
    <li><a href="opengroup.jsp">Open Group</a></li>
	<li><a href="occupations.jsp">Edit Occupations</a></li>
    <li><a href="helpadmin.html">Help</a></li>
  </ul>
</div><!--END NAVIGATION-->


</div><!--END HEADER-->




<!--MAIN CONTENT CONTAINER -->
<div id="main">

<br><br>

<fieldset>
<h3>Admininstration - Add New Group</h3>
</fieldset>



<br><br>

<div id="mainArea">

<!--START FORM-->
<form id="newGroupForm" method="post" action="http://localhost:8080/RealityUWeb/NewGroupServlet" onSubmit="return validate(this);">

<fieldset>
<br><br>
<div class="FixedHeightContainer">
<div class="Content">
<% 
//If form never been filled in yet, all values are blank
if (session.getAttribute("newGrp") == null) {
%>
	<div>
		<label for="name">Group Name:</label>	
		<input style="width:10%" type="text" name="name" value="">
	</div>
	<div>
		<label for="highschool">High School:</label>
		<input style="width:10%" type="text" name="highschool" value="">
	</div>
	<div>
		<label for="teacher">Teacher:</label>
		<input style="width:10%" type="text" name="teacher" value="">
	</div>
	<div>
		<label for="classPeriod">Period:</label>
		<input style="width:10%" type="text" name="classPeriod" value="">
		
			
	</div>
	<label for=Drop-Top>Survey Start Date:</label>
	<select name=ssMonth>
	<option value="Month">Month</option>
	<option value="1">Jan</option>
	<option value="2">Feb</option>
	<option value="3">March</option>
	<option value="4">April</option>
	<option value="5">May</option>
	<option value="6">June</option>
	<option value="7">July</option>
	<option value="8">Aug</option>
	<option value="9">Sept</option>
	<option value="10">Oct</option>
	<option value="11">Nov</option>
	<option value="12">Dec</option>
	</select>
	
	<select name=ssDay>
	<option value="day">Day</option>
<%	int x=1;
	int  day = 1;
	while(x<=31){
		%>
		<option value = "<%out.print(day);%>"><%out.print(day);%></option>
<%		x++;
		day++;
	} %>
</select>


<select name=ssYear >
<option value="Year">Year</option>
<%	int x1=1;
	GregorianCalendar date = new GregorianCalendar();
	int  year = date.get(Calendar.YEAR);
	while(x1<=5){
		%>
		<option value = "<%out.print(year);%>"><%out.print(year);%></option>
<%		x1++;
		year++;
	} %>
</select>
<br>
<br>
<br>
	
	<label for=Drop-Top>Survey Exipire Date:</label>
	<select name=seMonth>
		<option value="Month">Month</option>
	<option value="1">Jan</option>
	<option value="2">Feb</option>
	<option value="3">March</option>
	<option value="4">April</option>
	<option value="5">May</option>
	<option value="6">June</option>
	<option value="7">July</option>
	<option value="8">Aug</option>
	<option value="9">Sept</option>
	<option value="10">Oct</option>
	<option value="11">Nov</option>
	<option value="12">Dec</option>
	</select>
	
	<select name=seDay>
	<option value="day">Day</option>
<%	int x2=1;
	int  day2 = 1;
	while(x2<=31){
		%>
		<option value = "<%out.print(day2);%>"><%out.print(day2);%></option>
<%		x2++;
		day2++;
	} %>
</select>

<select name=seYear>
<option value="Year">Year</option>
<%	int x3=1;
	GregorianCalendar date2 = new GregorianCalendar();
	int  year2 = date2.get(Calendar.YEAR);
	while(x3<=5){
		%>
		<option value = "<%out.print(year2);%>"><%out.print(year2);%></option>
<%		x3++;
		year2++;
	} %>
</select>
	<br>
	<br>
	<br>
	
	
	<label for=Drop-Top>Event Date:</label>
	<select name=edMonth>
		<option value="Month">Month</option>
	<option value="1">Jan</option>
	<option value="2">Feb</option>
	<option value="3">March</option>
	<option value="4">April</option>
	<option value="5">May</option>
	<option value="6">June</option>
	<option value="7">July</option>
	<option value="8">Aug</option>
	<option value="9">Sept</option>
	<option value="10">Oct</option>
	<option value="11">Nov</option>
	<option value="12">Dec</option>
	</select>
	
	<select name=edDay>
		<option value="day">Day</option>
<%	int x4=1;
	int  day3 = 1;
	while(x4<=31){
		%>
		<option value = "<%out.print(day3);%>"><%out.print(day3);%></option>
<%		x4++;
		day3++;
	} %>
</select>

<select name=edYear>
<option value="Year">Year</option>
<%	int x5=1;
	GregorianCalendar date3 = new GregorianCalendar();
	int  year3 = date3.get(Calendar.YEAR);
	while(x5<=5){
		%>
		<option value = "<%out.print(year3);%>"><%out.print(year3);%></option>
<%		x5++;
		year3++;
	} %>
</select>
	<div>
		<label for="studentAccessCode">Student Access Code:</label>
		
		<input style="width:10%" type="text" name="studentAccessCode" value="(Auto-Generated)" readonly>
	</div>
	<div>
		<label for="coordinatorCode">Coordinator Access Code:</label>
		
		<input style="width:10%" type="text" name="coordinatorCode" value="(Auto-Generated)" readonly>
	</div>
	
	<!--========================================-->
	<!--Adding Marriage Choice Radio Button		-->
	<!--           UPDATED CODE                 -->	
	<!--         Created by Josh                -->
	<!--========================================-->
	<div>
		<label>Marriages?</label>
		</br>				
		<label>Yes:</label><input type = "radio" name = "choice" value = yes checked="checked">
		<label>	No:</label><input type = "radio" name = "choice" value = no>
	</div>
		<!--    end of josh's code       -->

</div>
</div>
<%
//If form filled in and checked on server but not valid: have dupe
//Group Name, fill in with previous values with error message
} else { 



                    //String obj 'newGroupMsg' created in NewGroupServlet
                    //Display msg only if new group data submitted
                    if (session.getAttribute("newGroupMsg") != null) {
                        HttpSession ses = request.getSession();
                        String msg = (String)ses.getAttribute("newGroupMsg");
                        out.println("<h2 id='newGroupMsg'>"+msg+"</h2>");                  
                    } //end if              
%> 

	<div>
		<% 
		//If dupe Group Name, show message
		//Key: id 0=dupe group name (New Group), id -1=dupe group name (Edited Group)
		if (newGrp.getId() <= 0) { 
		%>
		<span class="inputErrorMsg">THIS GROUP NAME ALREADY USED. PLEASE RE-TRY</span><br><br>
		<% } //end if %>
		<label for="name">Group Name:</label>
		<input type="text" name="name" value="<%=newGrp.getName()%>">
	</div>
	<div>
		<label for="highschool">High School:</label>
		<input type="text" name="highschool" value="<%=newGrp.getHighschool()%>">
	</div>
	<div>
		<label for="teacher">Teacher:</label>
		<input type="text" name="teacher" value="<%=newGrp.getTeacher()%>">
	</div>
	<div>
		<label for="classPeriod">Period:</label>
		<input type="text" name="classPeriod" value="<%=newGrp.getClassPeriod()%>">
	</div>
	<div>
		<label for="surveyStartDate">Survey Start Date:</label>
		<input type="text" name="surveyStartDate" value="<%=newGrp.getSurveyStartDate()%>">
	</div>
	<div>
		<label for="surveyEndDate">Survey End Date:</label>
		<input type="text" name="surveyEndDate" value="<%=newGrp.getSurveyEndDate()%>">
	</div>	
	
	<div>
		<label for="eventDate">Event Date:</label>
		<input type="text" name="eventDate" value="<%=newGrp.getEventDate()%>">
	</div>
	<!--========================================-->
	<!--Adding Marriage Choice Radio Button		-->
	<!--           UPDATED CODE                 -->	
	<!--         Created by Josh                -->
	<!--========================================-->
	<div>
		<%
			if(newGrp.getmarriageChoice() .equals("yes")){%>
		<label>Marriages?</label>
		</br>				
		<label>Yes:</label><input type = "radio" name = "choice" value = yes checked="checked">
		<label>	No:</label><input type = "radio" name = "choice" value = no>
		<% }%>
		<%
			if(newGrp.getmarriageChoice() .equals("no")){%>	
			<label>Marriages?</label>
		</br>				
		<label>Yes:</label><input type = "radio" name = "choice" value = yes>
		<label>	No:</label><input type = "radio" name = "choice" value = no checked="checked">
		<%}%>	
		<!--    end of josh's code       -->
	</div>
	
	<div>
		
		<% 
		//If not a dupe Group Name, and new Group has been created, show message
		//Key: id 0=dupe group name (New Group), id -1=dupe group name (Editd Group) 
		if (newGrp.getId() > 0) { 
		%>
		<span class="inputErrorMsg">THIS IS THE GROUP'S STUDENT ACCESS CODE:</span><br><br>
		<% } //end if %>
		
		<label for="studentAccessCode">Student Access Code:</label>
		<input type="text" name="studentAccessCode" value="<%=newGrp.getStudentAccessCode()%>" readonly>
	</div>
		<div>
		
		<%
		//ADDED IN THE COORDINATORS PASSWORD ========JOSH
			 %>
		<span class="inputErrorMsg">THIS IS THE GROUP'S COORDINATORS PASS CODE:</span><br><br>
		<label for="coordinatorCode">Coordinator Access Code:</label>
		<input type="text" name="coordinatorCode" value="<%=newGrp.getcoordinatorCode()%>" readonly>
	</div>
	
<% } //end if %>


</fieldset>

<br>

<!--SUBMIT FORM BUTTONS-->
		<div id="formButtonsContainer">
		  <div id="formButtons">
			<% 
			//If not a dupe Group Name, and new Group has been created, show "Edit Group" button
			//Key: id 0=dupe group name (New Group), id -1=dupe group name (Edited Group)
			//Set value of javascript 'btn' variable via onclick to detect which button submitted
			//for different behavior for clear button
			if (session.getAttribute("newGrp") != null && newGrp.getId() != 0) { 
			%>
				<input type="submit" value="Edit Group" id="submit" name="editGroup" onclick="btn='submit';">
			<% } else { //if Group not created yet %>
				<input type="submit" value="Submit" id="submit" name="submit" onclick="btn='submit';">
			<% } //end if %>
			<input type="submit" value="Clear" id="clear" name="clear" onclick="btn='clear';">
		  </div>
		</div>

<br><br>

<!--END FORM-->
</form>

</div><!--END mainArea-->



</div><!--END Main Area-->


<!--FOR STICKY FOOTER-->
<div id="push"></div>


</div><!--END Content Wrapper-->


<!--FOOTER OUTSIDE WRAPPER-->
<div id="footer" class="legal">
Copyright &copy; 2009-2014 CIS of Marietta/Cobb County
</div><!--END FOOTER-->


</body>
</html>
