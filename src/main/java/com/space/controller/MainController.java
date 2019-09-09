package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class MainController {
    @Autowired
    ShipService shipService;

    @GetMapping("/ships")
//    @RequestMapping(value = "/ships", method = RequestMethod.GET)
    public List<Ship> getAllShips(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "planet", required = false) String planet,
                                  @RequestParam(value = "shipType", required = false) ShipType shipType,
                                  @RequestParam(value = "after", required = false) Long after,
                                  @RequestParam(value = "before", required = false) Long before,
                                  @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                  @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                  @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                  @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                  @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                  @RequestParam(value = "minRating", required = false) Double minRating,
                                  @RequestParam(value = "maxRating", required = false) Double maxRating,
                                  @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        System.out.println("Processing GET all ships");

        try {
            return shipService.getAllShips(pageable);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/ships/count")
    public int getShipsCount(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "planet", required = false) String planet,
                             @RequestParam(value = "shipType", required = false) ShipType shipType,
                             @RequestParam(value = "after", required = false) Long after,
                             @RequestParam(value = "before", required = false) Long before,
                             @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                             @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                             @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                             @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                             @RequestParam(value = "minRating", required = false) Double minRating,
                             @RequestParam(value = "maxRating", required = false) Double maxRating,
                             @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order) {
        System.out.println("Processing Get COUNT");
        return shipService.getAllShips().size();
    }

    @PostMapping("/ships")
    public Ship addShip(@RequestBody Ship ship) {
        System.out.println("processing CREATE ship");
        try {
            shipService.addShip(ship);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ship;
    }

    @GetMapping("/ships/{id}")
    public Ship getShipById(@PathVariable("id") Long id) {
        System.out.println("getting ship BY ID");
        return shipService.getShipById(id);
    }

    @PostMapping("/ships/{id}")
    public Ship updateShip(@PathVariable("id") Long id) {
        try {
            System.out.println("processing UPDATE ship");
            Ship ship = shipService.getShipById(id);
            return shipService.updateShip(ship);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @DeleteMapping("/ships/{id}")
    public void deleteShip(@PathVariable("id") Long id) {
        System.out.println("processing DELETE ship");
        shipService.deleteShip(id);
    }
}
