package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ShipService {
    List<Ship> getAllShips();

    List<Ship> getAllShips(Pageable pageable);
    Ship addShip(Ship ship);
    Ship updateShip(Ship ship);
    void deleteShip(long id);
    Ship getShipById(Long id);
}