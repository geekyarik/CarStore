package com.softserve.yaroslav.items.controllers.users;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softserve.yaroslav.container.IocContainer;
import com.softserve.yaroslav.items.controllers.ControllerUrl;
import com.softserve.yaroslav.items.controllers.ViewUrls;
import com.softserve.yaroslav.items.dto.LoginDto;
import com.softserve.yaroslav.items.services.UserCarsService;
import com.softserve.yaroslav.items.services.UserService;

/**
 * Servlet implementation class UserCarsServlet
 */
@WebServlet(ControllerUrl.USER_CARS_SERVLET)
public class UserCarsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserCarsService userCarsService;
    private UserService userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserCarsServlet() {
	super();
	userCarsService = IocContainer.get().getUserCarsService();
	userService = IocContainer.get().getUserService();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	Integer currentPage = 0;
	Integer pageOffset = 0;

	LoginDto loginDto = (LoginDto) request.getSession().getAttribute("loginDto");

	if (request.getParameter("currentPage") != null)
	    currentPage = Integer.parseInt(request.getParameter("currentPage"));

	if (request.getParameter("pageOffset") != null)
	    pageOffset = Integer.parseInt(request.getParameter("pageOffset"));

	getServletConfig().getServletContext().setAttribute("usercars",
		userCarsService.getUserCarsDto(userService.getUserDto(loginDto), currentPage, pageOffset));
	getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.USER_CARS_JSP.toString()).forward(request,
		response);
    }

}
