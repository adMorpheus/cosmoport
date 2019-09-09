package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService{
    @Autowired
    private ShipRepository shipRepository;


    @Override
    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    public List<Ship> getAllShips(Pageable pageable) {
       return shipRepository.findAll(pageable).getContent();
    }

    @Override
    public Ship addShip(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public Ship updateShip(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public void deleteShip(long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public Optional<Ship> getShipById(long id) {
        return shipRepository.findById(id);
    }
}
