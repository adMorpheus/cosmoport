package com.space.service;

import com.space.model.Ship;

import java.util.List;
import java.util.Optional;

public interface ShipService {
    List<Ship> getAllShips();
    Ship addShip(Ship ship);
    Ship updateShip(Ship ship);
    void deleteShip(long id);
    Optional<Ship> getShipById(long id);
}