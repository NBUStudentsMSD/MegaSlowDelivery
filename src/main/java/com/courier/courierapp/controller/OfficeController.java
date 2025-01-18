package com.courier.courierapp.controller;

import com.courier.courierapp.model.Office;
import com.courier.courierapp.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    //update ex.office name
    @PutMapping("/{id}/name")
    public ResponseEntity<Office> updateOfficeName(
            @PathVariable Long id,
            @RequestBody Map<String, String> updateData) {
        if (!updateData.containsKey("name")) {
            return ResponseEntity.badRequest().body(null);
        }
        Office updatedOffice = officeService.updateOfficeName(id, updateData.get("name"));
        if (updatedOffice != null) {
            return ResponseEntity.ok(updatedOffice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //update ex.office address
    @PutMapping("/{id}/address")
    public ResponseEntity<Office> updateOfficeAddress(@PathVariable Long id, @RequestBody Map<String, String> updateData) {
        if (!updateData.containsKey("address")) {
            return ResponseEntity.badRequest().body(null);
        }
        Office updatedOffice = officeService.updateOfficeAddress(id, updateData.get("address"));
        if (updatedOffice != null) {
            return ResponseEntity.ok(updatedOffice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //delete ex.office
    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
    }
}
