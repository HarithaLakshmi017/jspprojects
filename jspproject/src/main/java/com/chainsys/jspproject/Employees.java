package com.chainsys.jspproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class Employees
 */
@WebServlet("/Employees")
public class Employees extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Employees() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 List<Employee> allEmployee = EmployeeDao.getAllEmployee();
			request.setAttribute("emplist", allEmployee);
			RequestDispatcher rd = request.getRequestDispatcher("/viewEmployee.jsp");
			rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("click").equals("ADD_EMP")) {
			String source = "AddNewEmployee";
			String message = "<h1>Error while " + source + "</h1>";
			PrintWriter out = response.getWriter();
			Employee newemp = null;
			int result = 0;
			int empId = 0;
			String testname = null;
			String errorPage = null;
			try {
				newemp = new Employee();
				String id = request.getParameter("id");
				try {
					Validator.checkStringForParse(id);
				} catch (InvalidInputDataException e) {
					message += " Error in Employee id input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return; // It terminates the Code execution beyond this point
				}
				empId = Integer.parseInt(id);
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
				testname = fname;
				try {
					Validator.checkStringOnly(testname);
				} catch (InvalidInputDataException e) {
					message += " Error in First Name input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				try {
					Validator.checklengthOfString(fname);
				} catch (InvalidInputDataException e) {
					message += " Error in First Name input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				newemp.setFirst_name(fname);
//-----------------------------------
				String lname = request.getParameter("lname");
				testname = fname;
				try {
					Validator.checkStringOnly(testname);
				} catch (InvalidInputDataException e) {
					message += " Error in Last Name input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				try {
					Validator.checklengthOfString(lname);
				} catch (InvalidInputDataException e) {
					message += " Error in Last Name input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				newemp.setLast_name(lname);
//----------------------------------			
				String email = request.getParameter("email");
				try {
					Validator.checkMail(email);
				} catch (InvalidInputDataException e) {
					message += " Error in email input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				newemp.setEmail(email);
//--------------------------------------			
				SimpleDateFormat hire_dateFormate = new SimpleDateFormat("dd/MM/yyyy");
				String emp_HireDate = request.getParameter("hdate");
				// Date hire_date=hire_dateFormate.parse(emp_HireDate);

				try {
					Validator.checkDateFormat(emp_HireDate);
				} catch (InvalidInputDataException e) {
					message += " Error in Hire Date input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				Date newDate = null;
				try {
					newDate = hire_dateFormate.parse(emp_HireDate);
				} catch (ParseException e) {
					message += " Error in Hire Date input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				try {
					Validator.CheckNofutureDate(newDate);
				} catch (InvalidInputDataException e) {
					message += " Error in Hire Date input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}

				newemp.setHire_date(newDate);
//----------------------------------------
				String jobId = request.getParameter("jobid");
				try {
					Validator.checkjob(jobId);
				} catch (InvalidInputDataException e) {
					message += " Error in Job Id input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				newemp.setJob_id(jobId);
//---------------------------------------			
				String sal = request.getParameter("salary");
				try {
					Validator.checkStringForParseFloat(sal);
				} catch (InvalidInputDataException e) {
					message += " Error in Salary input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				float salParse = Float.parseFloat(sal);
				try {
					Validator.checkSalLimit(salParse);
				} catch (InvalidInputDataException e) {
					message += " Error in Salary input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				newemp.setSalary(salParse);
//----------------------------------------------		
				try {
					result = EmployeeDao.insertEmployee(newemp);
					out.println("<div> Add New Employee: " + result + "</div>");
				} catch (Exception e) {
					message += " Error while inserting record </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}
				try {
					out.close();
				} catch (Exception e) {
					message += "Message: " + e.getMessage();
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
					return;
				}

			} catch (Exception e) {
				message += "Message: " + e.getMessage();
				errorPage = ExceptionManager.handleException(e, source, message);
				out.print(errorPage);
				return;
			}

		} else if (request.getParameter("click").equals("UPDATE_EMP")) {
			doPut(request, response);
		} else if (request.getParameter("click").equals("DELETE_EMP")) {
			doDelete(request, response);
		}
		else if (request.getParameter("click").equals("View")) {
			doDelete(request, response);
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		{
			String source = "Update Employee";
			String message = "<h1>Error while " + source + "</h1>";
			PrintWriter out = response.getWriter();
			Employee newemp = null;
			int result = 0;
			int empId = 0;
			String errorPage = null;
			try {
				newemp = new Employee();
				String id = request.getParameter("id");
				try {
					Validator.checkStringForParse(id);
				} catch (InvalidInputDataException e) {
					message += " Error in Employee id input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
				}
				empId = Integer.parseInt(id);
				try {
					Validator.CheckNumberForGreaterThanZero(empId);
				} catch (InvalidInputDataException e) {
					message += " Error in Employee id input </p>";
					errorPage = ExceptionManager.handleException(e, source, message);
					out.print(errorPage);
				}
				newemp.setEmp_Id(empId);
				result = EmployeeDao.deleteEmployee(empId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.println("<div> Delete Employee: " + result + "</div>");
		}

	}
}
