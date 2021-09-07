package com.amt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amt.dao.ShipDao;
import com.amt.dto.AddShipDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.ShipNotFoundException;
import com.amt.model.User;

@Service
public class ShipService {
/*
	private ShipDao shipDao;
	
	@Autowired
	public ShipService(ShipDao shipDao) {
		this.shipDao = shipDao;
	}
	
	public Ship addShip(AddShipDTO addShipDto) throws BadParameterException {
		if (addShipDto.getName().trim().equals("")) {
			throw new BadParameterException("You cannot have a blank name for a ship");
		}
		
		if (addShipDto.getAge() < 0) {
			throw new BadParameterException("You cannot have a negative age for a ship");
		}
		
		Ship ship = shipDao.addShip(addShipDto);
		
		return ship;
	}
	
	public List<Ship> getAllShips() throws ShipNotFoundException {
		List<Ship> ships = shipDao.getAllShips();
		
		if (ships.size() == 0) {
			throw new ShipNotFoundException("No ships were found in the system");
		}
		
		return ships;
	}

	public Ship getShipById(String stringId) throws BadParameterException {
		
		try {
			int id = Integer.parseInt(stringId);
			
			Ship ship = shipDao.getShipById(id);
			
			return ship;
		} catch (NumberFormatException e) {
			throw new BadParameterException("Id must be an int");
		}
		
		
	}
	*/
}
