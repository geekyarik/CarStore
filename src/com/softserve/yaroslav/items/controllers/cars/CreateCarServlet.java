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
import com.softserve.yaroslav.items.dto.CarDto;
import com.softserve.yaroslav.items.dto.LoginDto;
import com.softserve.yaroslav.items.dto.UserDto;
import com.softserve.yaroslav.items.services.CarService;
import com.softserve.yaroslav.items.services.UserService;

/**
 * Servlet implementation class CreateCarServlet
 */
@WebServlet(ControllerUrl.CAR_CREATE_SERVLET)
public class CreateCarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CarService carService;
    private UserService userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCarServlet() {
	carService = IocContainer.get().getCarService();
	userService = IocContainer.get().getUserService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	if (request.isRequestedSessionIdFromCookie()) {
	    CarDto carDto = null;
	    UserDto userDto = null;

	    String brand = null;
	    String model = null;
	    String gearbox = null;
	    String details = null;
	    Integer engineCapacity = null;
	    Integer year = null;
	    Integer mileage = null;
	    Integer price = null;

	    if (request.getParameter("brand") != null) {
		brand = request.getParameter("brand");
	    }

	    if (request.getParameter("model") != null) {
		model = request.getParameter("model");
	    }

	    if (request.getParameter("gearbox") != null) {
		gearbox = request.getParameter("gearbox");
	    }

	    if (request.getParameter("details") != null) {
		details = request.getParameter("details");
	    }

	    if (request.getParameter("engineCapacity") != null) {
		engineCapacity = Integer.parseInt(request.getParameter("engineCapacity"));
	    }

	    if (request.getParameter("year") != null) {
		year = Integer.parseInt(request.getParameter("year"));
	    }

	    if (request.getParameter("mileage") != null) {
		mileage = Integer.parseInt(request.getParameter("mileage"));
	    }

	    if (request.getParameter("price") != null) {
		price = Integer.parseInt(request.getParameter("price"));
	    }

	    userDto = userService.getUserDto((LoginDto) request.getAttribute("loginDto"));
	    carDto = new CarDto(0l, brand, model, gearbox, details, userDto.getId(), engineCapacity, year, mileage,
		    price);
	    carService.setCarDto(carDto, carDto.getIdUser());
	} else {
	    getServletConfig().getServletContext().getRequestDispatcher(ViewUrls.LOGIN_JSP.toString());
	}
    }

}
