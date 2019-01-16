package com.softserve.yaroslav.container;

import com.softserve.yaroslav.items.dao.CarDao;
import com.softserve.yaroslav.items.dao.UserDao;
import com.softserve.yaroslav.items.services.AllCarsService;
import com.softserve.yaroslav.items.services.AllUsersService;
import com.softserve.yaroslav.items.services.CarService;
import com.softserve.yaroslav.items.services.UserCarsService;
import com.softserve.yaroslav.items.services.UserService;

public final class IocContainer {
    private static volatile IocContainer instance = null;
    //
    private UserDao userDao;
    private CarDao carDao;
    //
    private UserService userService;
    private CarService carService;
    private UserCarsService userCarsService;
    private AllCarsService allCarsService;
    private AllUsersService allUsersService;

    private IocContainer() {
	initDaos();
	initServices();
    }

    private void initDaos() {
	userDao = new UserDao();
	carDao = new CarDao();
    }

    private void initServices() {
	userService = new UserService(userDao);
	carService = new CarService(carDao);
	userCarsService = new UserCarsService(userDao, carDao);
	allCarsService = new AllCarsService(carDao);
	allUsersService = new AllUsersService(userDao);
    }

    public static IocContainer get() {
	if (instance == null) {
	    synchronized (IocContainer.class) {
		if (instance == null) {
		    instance = new IocContainer();
		}
	    }
	}
	return instance;
    }

    public UserDao getUserDao() {
	return userDao;
    }

    public CarDao getCarDao() {
	return carDao;
    }

    public UserService getUserService() {
	return userService;
    }

    public CarService getCarService() {
	return carService;
    }

    public UserCarsService getUserCarsService() {
	return userCarsService;
    }

    public AllCarsService getAllCarsService() {
	return allCarsService;
    }

    public AllUsersService getAllUserService() {
	return allUsersService;
    }
}
