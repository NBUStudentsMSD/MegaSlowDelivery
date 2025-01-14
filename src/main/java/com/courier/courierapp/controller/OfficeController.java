package com.courier.courierapp.controller;

import com.courier.courierapp.model.Office;
import com.courier.courierapp.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    // Get all offices
    @GetMapping
    public List<Office> getAllOffices() {
        return officeService.getAllOffices();
    }

    // Get an office by ID
    @GetMapping("/{id}")
    public Office getOfficeById(@PathVariable Long id) {
        return officeService.getOfficeById(id);
    }

    // Create a new office
    @PostMapping
    public Office createOffice(@RequestBody Office office) {
        return officeService.createOffice(office);
    }

    //update ex.office
    @PutMapping("/{id}")
    public Office updateOffice(@PathVariable Long id, @RequestBody Office office) {
        return officeService.updateOffice(id, office);
    }

    //delete ex.office
    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
    }
}
