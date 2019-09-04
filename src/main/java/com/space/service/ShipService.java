package com.space.service;

import com.space.model.Ship;

import java.util.List;

public interface ShipService {
    List<Ship> getAllShips();
    Ship addShip(Ship ship);
    Ship editShip(Ship ship);
    void deleteShip(Long id);
    Ship getShipById(Long id);
    List<Ship> getSelectedShips();
    int countSelectedShips(List<Ship> selected);
}