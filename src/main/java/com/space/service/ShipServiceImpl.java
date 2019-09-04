package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService{
    @Autowired
    private ShipRepository shipRepository;


    @Override
    public List<Ship> getAllShips() {

        return null;
    }

    @Override
    public Ship addShip(Ship ship) {
        return null;
    }

    @Override
    public Ship editShip(Ship ship) {
        return null;
    }

    @Override
    public void deleteShip(Long id) {

    }

    @Override
    public Ship getShipById(Long id) {
        return null;
    }

    @Override
    public List<Ship> getSelectedShips() {
        return null;
    }

    @Override
    public int countSelectedShips(List<Ship> selected) {
        return 0;
    }
}
