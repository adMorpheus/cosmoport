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
        shipRepository.saveAndFlush(ship);
        return ship;
    }

    @Override
    public Ship updateShip(Long id, Ship dstShip) {
        System.out.println("processing service UPDATE ship");
        Ship originalShip = getShipById(id);
         if (dstShip.getName()!=null){originalShip.setName(dstShip.getName());}
         if (dstShip.getPlanet()!=null){originalShip.setPlanet(dstShip.getPlanet());}
         if (dstShip.getProdDate()!=null){originalShip.setProdDate(dstShip.getProdDate());}
         if (dstShip.getUsed()!=null){originalShip.setUsed(dstShip.getUsed());}
         if (dstShip.getSpeed()!=null){originalShip.setSpeed(dstShip.getSpeed());}
         if (dstShip.getCrewSize()!=null){originalShip.setCrewSize(dstShip.getCrewSize());}
            originalShip.setRating(generateRating(originalShip));

        return shipRepository.saveAndFlush(originalShip);
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
