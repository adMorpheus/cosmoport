package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", method = RequestMethod.GET)
public class MainController {
    @Autowired
    ShipService shipService;

    @GetMapping("/ships")
    public List<Ship> doSomthing(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "4") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);


        return shipService.getAllShips(pageable);
    }

    @GetMapping("/ships/count")
    public int doSomthingAlse() {
        return 40;
    }
}
