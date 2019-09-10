package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ShipServiceImpl implements ShipService {
    @Autowired
    private ShipRepository shipRepository;


    @Override
    public List<Ship> getAllShips(Specification<Ship> spec) {
        return shipRepository.findAll(spec);
    }

    public List<Ship> getAllShips(Specification<Ship> spec, Pageable pageable) {
        return shipRepository.findAll(spec, pageable).getContent();
    }

    @Override
    public Ship addShip(Ship ship) {
        if (!isNameValid(ship.getName()) ||
                !isPlanetValid(ship.getPlanet()) ||
                ship.getShipType() == null ||
                !isSpeedValid(ship.getSpeed()) ||
                !isCrewSizeValid(ship.getCrewSize()) ||
                !isProdDateValid(ship.getProdDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        System.out.println("ship.getUsed() = " + ship.getUsed());
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        System.out.println("ship.getUsed() = " + ship.getUsed());
        ship.setRating(generateRating(ship));
        shipRepository.saveAndFlush(ship);
        return ship;
    }

    @Override
    public Ship updateShip(Long id, Ship dstShip) {
        if (!isIdValid(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (!isIdExist(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Ship originalShip = getShipById(id);

        if (dstShip.getName() == null &&
                dstShip.getPlanet() == null &&
                dstShip.getShipType() == null &&
                dstShip.getProdDate() == null &&
                dstShip.getSpeed() == null &&
                dstShip.getCrewSize() == null) return originalShip;


        if (dstShip.getName() != null) originalShip.setName(dstShip.getName());

        if (dstShip.getPlanet() != null) originalShip.setPlanet(dstShip.getPlanet());

        if (dstShip.getShipType() != null) originalShip.setShipType(dstShip.getShipType());

        if (dstShip.getProdDate() != null) originalShip.setProdDate(dstShip.getProdDate());

        if (dstShip.getUsed() != null) originalShip.setUsed(dstShip.getUsed());

        if (dstShip.getSpeed() != null) originalShip.setSpeed(dstShip.getSpeed());

        if (dstShip.getCrewSize() != null) originalShip.setCrewSize(dstShip.getCrewSize());

            originalShip.setRating(generateRating(originalShip));


        if (!isParametrsInRange(originalShip)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return shipRepository.saveAndFlush(originalShip);

    }

    @Override
    public void deleteShip(long id) {
        if (!isIdValid(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!isIdExist(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        shipRepository.deleteById(id);
    }

    @Override
    public Ship getShipById(Long id) {
        if (!isIdValid(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!isIdExist(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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

    private boolean isProdDateValid(Date date) {
        if (date == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        if (year < 2800 || year > 3019) return false;
        return true;
    }

    private boolean isIdValid(Long id) {
        return id > 0;
    }

    private boolean isIdExist(Long id) {
        if (shipRepository.findById(id).equals(Optional.empty())) return false;
        return shipRepository.existsById(id);
    }

    private boolean isNameValid(String name) {
        return name != null && name != "" && name.length() <= 50;
    }

    private boolean isPlanetValid(String planet) {
        return planet != null && planet != "" && planet.length() <= 50;
    }

    private boolean isSpeedValid(Double speed) {
        return speed != null && speed >= 0.01 && speed <= 0.99;
    }

    private boolean isCrewSizeValid(Integer crewSize) {
        return crewSize != null && crewSize >= 1 && crewSize <= 9999;
    }

    private boolean isParametrsInRange(Ship ship) {
        return ship.getName().length() > 0 &&
                ship.getName().length() <= 50 &&
                ship.getPlanet().length() > 0 &&
                ship.getPlanet().length() <= 50 &&
                isProdDateValid(ship.getProdDate()) &&
                ship.getSpeed() >= 0.1 &&
                ship.getSpeed() <= 0.99 &&
                ship.getCrewSize() > 0 &&
                ship.getCrewSize() <= 9999;
    }

}
