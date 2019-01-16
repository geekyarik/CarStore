package com.softserve.yaroslav.items.services;

import com.softserve.yaroslav.container.IocContainer;
import com.softserve.yaroslav.items.dao.CarDao;
import com.softserve.yaroslav.items.dto.AllCarsDto;
import com.softserve.yaroslav.items.dto.CarDto;
import com.softserve.yaroslav.items.dto.SearchStatementDto;
import com.softserve.yaroslav.items.entity.Car;

public class AllCarsService {

    private CarDao carDao;

    /**
     * @param carDao
     */
    public AllCarsService() {
	this.carDao = IocContainer.get().getCarDao();
    }

    public AllCarsService(CarDao carDao) {
	this.carDao = carDao;
    }

    /*
     * If pageOffset == 0 then DEFAULT(10) used
     * 
     */
    public AllCarsDto getCarsDto(int currentPage, int pageOffset) {
	AllCarsDto allCarsDto = new AllCarsDto();
	allCarsDto.setCurrentPage(currentPage);
	int fromIndex;
	int toIndex;

	if (currentPage != 0) {
	    allCarsDto.setCurrentPage(currentPage);
	}
	if (pageOffset != 0) {
	    allCarsDto.setPageOffset(pageOffset);
	}
	fromIndex = (allCarsDto.getCurrentPage() - 1) * allCarsDto.getPageOffset();
	toIndex = allCarsDto.getCurrentPage() * allCarsDto.getPageOffset();

	for (Car car : carDao.getAll().subList(fromIndex, toIndex)) {
	    CarDto carDto = new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getGearBox(), car.getDetails(),
		    car.getIdUser(), car.getEngineCapacity(), car.getYear(), car.getMileage(), car.getPrice());
	    allCarsDto.addCarDto(carDto);
	}
	return allCarsDto;
    }

    public AllCarsDto getCarsDto() {
	AllCarsDto allCarsDto = new AllCarsDto();
	int fromIndex = (allCarsDto.getCurrentPage() - 1) * allCarsDto.getPageOffset();
	int toIndex = allCarsDto.getCurrentPage() * allCarsDto.getPageOffset();

	for (Car car : carDao.getAll().subList(fromIndex, toIndex)) {
	    CarDto carDto = new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getGearBox(), car.getDetails(),
		    car.getIdUser(), car.getEngineCapacity(), car.getYear(), car.getMileage(), car.getPrice());
	    allCarsDto.addCarDto(carDto);
	}
	return allCarsDto;
    }

    public AllCarsDto getAllCarsDto() {
	AllCarsDto allCarsDto = new AllCarsDto();
	for (Car car : carDao.getAll()) {
	    CarDto carDto = new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getGearBox(), car.getDetails(),
		    car.getIdUser(), car.getEngineCapacity(), car.getYear(), car.getMileage(), car.getPrice());
	    allCarsDto.addCarDto(carDto);
	}
	return allCarsDto;
    }

    public AllCarsDto getCarsDtoFromSearch(SearchStatementDto ssDto) {
	AllCarsDto allCarsDto = new AllCarsDto();

	for (Car car : carDao.getByFieldName(ssDto.getFieldName(), ssDto.getRegex())) {
	    CarDto carDto = new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getGearBox(), car.getDetails(),
		    car.getIdUser(), car.getEngineCapacity(), car.getYear(), car.getMileage(), car.getPrice());
	    allCarsDto.addCarDto(carDto);
	}
	return allCarsDto;
    }
}
