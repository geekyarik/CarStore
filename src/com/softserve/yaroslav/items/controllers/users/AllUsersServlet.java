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
import com.softserve.yaroslav.items.dto.SearchStatementDto;
import com.softserve.yaroslav.items.services.AllUsersService;

/**
 * Servlet implementation class AllUsersServlet
 */
@WebServlet(ControllerUrl.ALL_USERS_SERVLET)
public class AllUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AllUsersService allUsersService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllUsersServlet() {
	super();
	allUsersService = IocContainer.get().getAllUserService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	getServletConfig().getServletContext().setAttribute("users", allUsersService.getUsersDto());
	getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.ALL_USERS_JSP.toString()).forward(request,
		response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	Integer currentPage = 0;
	Integer pageOffset = 0;
	SearchStatementDto ssDto;

	if (request.isRequestedSessionIdFromCookie()) {

	    if (request.getParameter("fieldName") != null && request.getParameter("regex") != null) {
		ssDto = new SearchStatementDto(request.getParameter("fieldName"), request.getParameter("regex"));
		getServletConfig().getServletContext().setAttribute("users",
			allUsersService.getUsersDtoFromSearch(ssDto));
		getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.ALL_USERS_JSP.toString());

	    } else {
		if (request.getParameter("currentPage") != null)
		    currentPage = Integer.parseInt(request.getParameter("currentPage"));

		if (request.getParameter("pageOffset") != null)
		    pageOffset = Integer.parseInt(request.getParameter("pageOffset"));

		// getServletConfig().getServletContext() == request ?
		getServletConfig().getServletContext().setAttribute("users",
			allUsersService.getUsersDto(currentPage, pageOffset)); // list or dto ?
		getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.ALL_USERS_JSP.toString());
	    }
	} else {
	    getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.LOGIN_JSP.toString());
	}
    }

}
