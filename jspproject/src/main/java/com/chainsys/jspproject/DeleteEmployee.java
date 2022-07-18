package com.chainsys.jspproject;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class DeleteEmployee
 */
@WebServlet("/DeleteEmployee")
public class DeleteEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEmployee() {
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
		request.setAttribute("result", result);
		RequestDispatcher rd = request.getRequestDispatcher("/Addemp.jsp");
		rd.forward(request, response);
	}

	}


