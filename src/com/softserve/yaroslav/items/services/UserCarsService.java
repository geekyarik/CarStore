package com.softserve.yaroslav.items.services;

import com.softserve.yaroslav.container.IocContainer;
import com.softserve.yaroslav.items.dao.CarDao;
import com.softserve.yaroslav.items.dao.UserDao;
import com.softserve.yaroslav.items.dto.CarDto;
import com.softserve.yaroslav.items.dto.UserCarsDto;
import com.softserve.yaroslav.items.dto.UserDto;
import com.softserve.yaroslav.items.entity.Car;
import com.softserve.yaroslav.items.entity.User;

public class UserCarsService {

    private UserDao userDao;
    private CarDao carDao;

    /**
     * @param userDao
     * @param carDao
     */
    public UserCarsService() {
	this.userDao = IocContainer.get().getUserDao();
	this.carDao = IocContainer.get().getCarDao();
    }

    public UserCarsService(UserDao userDao, CarDao carDao) {
	this.userDao = userDao;
	this.carDao = carDao;
    }

    public UserCarsDto getUserCarsDto(UserDto userDto, int currentPage, int pageOffset) {
	try {
	    User user = userDao.getUserEntityByLogin(userDto.getLogin());
	    UserCarsDto userCarsDto = new UserCarsDto(userDto.getLogin());
	    int fromIndex;
	    int toIndex;

	    if (currentPage != 0) {
		userCarsDto.setCurrentPage(currentPage);
	    }
	    if (pageOffset != 0) {
		userCarsDto.setPageOffset(pageOffset);
	    }
	    fromIndex = (userCarsDto.getCurrentPage() - 1) * userCarsDto.getPageOffset();
	    toIndex = userCarsDto.getCurrentPage() * userCarsDto.getPageOffset();

	    for (Car car : carDao.getUserEntityByIdUser(user.getId()).subList(fromIndex, toIndex)) {
		CarDto carDto = new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getGearBox(),
			car.getDetails(), car.getIdUser(), car.getEngineCapacity(), car.getYear(), car.getMileage(),
			car.getPrice());
		userCarsDto.addCarDto(carDto);
	    }
	    return userCarsDto;
	} catch (Exception ex) {
	    return null;
	}
    }
}
