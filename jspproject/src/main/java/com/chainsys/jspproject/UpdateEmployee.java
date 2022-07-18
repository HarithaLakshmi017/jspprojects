package com.chainsys.jspproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.jspproject.commonutil.ExceptionManager;
import com.chainsys.jspproject.commonutil.InvalidInputDataException;
import com.chainsys.jspproject.commonutil.Validator;
import com.chainsys.jspproject.dao.EmployeeDao;
import com.chainsys.jspproject.pojo.Employee;

/**
 * Servlet implementation class UpdateEmployee
 */
@WebServlet("/UpdateEmployee")
public class UpdateEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			String source = "Update Employee";
			String message = "<h1>Error while " + source + "</h1>";
			PrintWriter out = response.getWriter();
			Employee newemp = new Employee();
			int result = 0;
			String errorPage = null;
			try {
				String id = request.getParameter("id");
				try {
					Validator.checkStringForParse(id);
				} catch (InvalidInputDataException e) {
					message += " Error in Employee id input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return; // It terminates the Code execution beyond this point
				}
				int empId = Integer.parseInt(id);
				try {
					Validator.CheckNumberForGreaterThanZero(empId);
				} catch (InvalidInputDataException e) {
					message += " Error in Employee id input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				newemp.setEmp_Id(empId);
	//--------------------------------
				String fname = request.getParameter("fname");
				try {
					Validator.checkStringOnly(fname);
				} catch (InvalidInputDataException e) {
					message += " Error in First Name input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				try {
					Validator.checklengthOfString(fname);
				} catch (InvalidInputDataException err) {
					out.println("Error first name:" + err.getMessage());
				}
				newemp.setFirst_name(fname);
	//-----------------------------------
				String lname = request.getParameter("lname");
				try {
					Validator.checkStringOnly(lname);
				} catch (InvalidInputDataException e) {
					message += " Error in First Name input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				try {
					Validator.checklengthOfString(lname);
				} catch (InvalidInputDataException err) {
					out.println("Error in Last name:" + err.getMessage());
				}
				newemp.setLast_name(lname);
	//----------------------------------			
				String email = request.getParameter("email");
				try {
					Validator.checkMail(email);
				} catch (InvalidInputDataException e) {
					out.println("Error in Email:" + e.getMessage());
				}
				newemp.setEmail(email);
	//--------------------------------------			
				SimpleDateFormat hire_dateFormate = new SimpleDateFormat("dd/MM/yyyy");
				String emp_HireDate = request.getParameter("date");
				// Date hire_date=hire_dateFormate.parse(emp_HireDate);

				try {
					Validator.checkDateFormat(emp_HireDate);
				} catch (InvalidInputDataException e) {
					out.println("Error in Hire date:" + e.getMessage());
				}
				Date newDate = null;
				try {
					newDate = hire_dateFormate.parse(emp_HireDate);
				} catch (ParseException e) {
					out.println("Error in Hire date:" + e.getMessage());
				}

				newemp.setHire_date(newDate);
	//----------------------------------------
				String jobId = request.getParameter("jobid");
				try {
					Validator.checkjob(jobId);
				} catch (InvalidInputDataException err) {
					out.println("Error in Job id:" + err.getMessage());
				}
				newemp.setJob_id(jobId);
	//---------------------------------------			
				String sal = request.getParameter("salary");
				try {
					Validator.checkStringForParseFloat(sal);
				} catch (InvalidInputDataException err) {
					out.println("Error in salary:" + err.getMessage());
				}
				float salParse = Float.parseFloat(sal);
				try {
					Validator.checkSalLimit(salParse);
				} catch (InvalidInputDataException err) {
					out.println("Error in salary:" + err.getMessage());
				}
				newemp.setSalary(salParse);
	//----------------------------------------------	
				result = EmployeeDao.updateEmployee(newemp);
				out.println(result + " Updated Successfully");

	      } catch (Exception e) {
				out.println("Error in some input data:" + e.getMessage());
			}
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

}
