package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class MainController {
    @Autowired
    ShipService shipService;

    @GetMapping("/ships")
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
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        System.out.println("Processing GET all ships");

        return shipService.getAllShips(Specification.where(filterByName(name)
                .and(filterByPlanet(planet))
                .and(filterByShipType(shipType))
                .and(filterByDate(after, before))
                .and(filterByInUsed(isUsed))
                .and(filterBySpeed(minSpeed, maxSpeed))
                .and(filterByCrewSize(minCrewSize, maxCrewSize))
                .and(filterByRating(minRating, maxRating))), pageable);
    }

    @GetMapping("/ships/count")
    public int getShipsCount(@RequestParam(value = "name", required = false) String name,
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
                             @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order) {
        System.out.println("Processing Get COUNT");
        return shipService.getAllShips(Specification.where(filterByName(name)
                .and(filterByPlanet(planet))
                .and(filterByShipType(shipType))
                .and(filterByDate(after, before))
                .and(filterByInUsed(isUsed))
                .and(filterBySpeed(minSpeed, maxSpeed))
                .and(filterByCrewSize(minCrewSize, maxCrewSize))
                .and(filterByRating(minRating, maxRating)))).size();
    }

    @PostMapping("/ships")
    public Ship addShip(@RequestBody Ship ship) {
        System.out.println("processing CREATE ship");
        System.out.println(ship.toString());
        shipService.addShip(ship);
        return ship;
    }

    @GetMapping("/ships/{id}")
    public Ship getShipById(@PathVariable("id") Long id) {
        return shipService.getShipById(id);
    }

    @PostMapping("/ships/{id}")
    public Ship updateShip(@PathVariable("id") Long id, @RequestBody Ship ship) {
        return shipService.updateShip(id, ship);
    }

    @DeleteMapping("/ships/{id}")
    public void deleteShip(@PathVariable("id") Long id) {
        System.out.println("processing DELETE ship");
        shipService.deleteShip(id);
    }

    private Specification<Ship> filterByName(String name) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (name != null) {
                    return criteriaBuilder.like(root.get("name"), "%" + name + "%");
                }
                return null;
            }
        };
    }

    private Specification<Ship> filterByPlanet(String planet) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (planet != null) {
                    return criteriaBuilder.like(root.get("planet"), "%" + planet + "%");
                }
                return null;
            }
        };
    }

    private Specification<Ship> filterByShipType(ShipType shipType) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (shipType != null) return criteriaBuilder.equal(root.get("shipType"), shipType);
                return null;
            }
        };
    }

    private Specification<Ship> filterByDate(Long after, Long before) {

        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (after == null && before == null) {
                    return null;
                }
                if (after == null) {
                    Date beforeDate = new Date(before);
                    return criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"), beforeDate);
                }
                if (before == null) {
                    Date afterDate = new Date(after);
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"), afterDate);
                }
                Date beforeDate = new Date(before);
                Date afterDate = new Date(after);
                return criteriaBuilder.between(root.get("prodDate"), afterDate, beforeDate);
            }
        };
    }

    private Specification<Ship> filterByInUsed(Boolean inUsed) {
        if (inUsed == null) return null;
        if (inUsed) return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isTrue(root.get("isUsed"));
            }
        };
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isFalse(root.get("isUsed"));
            }
        };
    }

    private Specification<Ship> filterBySpeed(Double min, Double max) {

        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (min == null && max == null) {
                    return null;
                }
                if (min == null) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("speed"), max);
                }
                if (max == null) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), min);
                }
                return criteriaBuilder.between(root.get("speed"), min, max);
            }
        };
    }

    private Specification<Ship> filterByCrewSize(Integer min, Integer max) {

        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (min == null && max == null) {
                return null;
            }
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), max);
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), min);
            }
            return criteriaBuilder.between(root.get("crewSize"), min, max);
        };
    }

    private Specification<Ship> filterByRating(Double min, Double max) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (min == null && max == null) {
                return null;
            }
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("rating"), max);
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), min);
            }
            return criteriaBuilder.between(root.get("rating"), min, max);
        };
    }

}
