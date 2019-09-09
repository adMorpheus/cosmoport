package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {
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
        return shipRepository.saveAndFlush(ship);
    }

    @Override
    public Ship updateShip(Ship ship) {
        return shipRepository.saveAndFlush(ship);
    }

    @Override
    public void deleteShip(long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public Ship getShipById(Long id) {
        return shipRepository.findById(id).get();
    }

    private Double generateRating(Ship ship) {
        double k = ship.getUsed() ? 0.5 : 1.0;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(ship.getProdDate());
        int year = calendar.get(Calendar.YEAR);
        Double currentRating = ((80 * ship.getSpeed() * k) / (3019 - year + 1));
        currentRating = Math.round(currentRating * 100.0) / 100.0;
        return currentRating;
    }
}
