package com.courier.courierapp.service;

import com.courier.courierapp.model.Office;
import com.courier.courierapp.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    //get all offices
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    //get an office by ID
    public Office getOfficeById(Long id) {
        return officeRepository.findById(id).orElse(null);
    }

    //create a new office
    public Office createOffice(Office office) {
        return officeRepository.save(office);
    }

    //update an office
    public Office updateOffice(Long id, Office updatedOffice) {
        return officeRepository.findById(id).map(office -> {
            office.setName(updatedOffice.getName());
            office.setAddress(updatedOffice.getAddress());
            return officeRepository.save(office);
        }).orElse(null);
    }


    //update office name
    public Office updateOfficeName(Long id, String name) {
        return officeRepository.findById(id).map(office -> {
            office.setName(name); // Update name
            return officeRepository.save(office); // Save updated office
        }).orElse(null); // Return null if office not found
    }

    //update office address
    public Office updateOfficeAddress(Long id, String address) {
        return officeRepository.findById(id).map(office -> {
            office.setAddress(address); // Update address
            return officeRepository.save(office); // Save updated office
        }).orElse(null); // Return null if office not found
    }





    //delete an office
    public void deleteOffice(Long id) {
        officeRepository.deleteById(id);
    }

}
