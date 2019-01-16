package com.softserve.yaroslav.items.controllers.cars;

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
import com.softserve.yaroslav.items.services.AllCarsService;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet(ControllerUrl.ALL_CARS_SERVLET)
public class AllCarsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AllCarsService allCarsService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllCarsServlet() {
	super();
	allCarsService = IocContainer.get().getAllCarsService();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	// request.setAttribute("pagination",
	// allCarsService.getAllCarsDto().getDEFAULT_PAGE_OFFSET());
	// request.setAttribute("page",
	// allCarsService.getAllCarsDto().getDEFAULT_CURRENT_PAGE());
	getServletConfig().getServletContext().setAttribute("cars", allCarsService.getCarsDto());
	getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.ALL_CARS_JSP.toString()).forward(request,
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
		getServletConfig().getServletContext().setAttribute("cars", allCarsService.getCarsDtoFromSearch(ssDto));
		getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.ALL_CARS_JSP.toString());

	    } else {
		if (request.getParameter("currentPage") != null)
		    currentPage = Integer.parseInt(request.getParameter("currentPage"));

		if (request.getParameter("pageOffset") != null)
		    pageOffset = Integer.parseInt(request.getParameter("pageOffset"));

		// getServletConfig().getServletContext() == request ?
		getServletConfig().getServletContext().setAttribute("cars",
			allCarsService.getCarsDto(currentPage, pageOffset)); // list or dto ?
		getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.ALL_CARS_JSP.toString());
	    }
	} else {
	    getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.LOGIN_JSP.toString());
	}
    }

}
