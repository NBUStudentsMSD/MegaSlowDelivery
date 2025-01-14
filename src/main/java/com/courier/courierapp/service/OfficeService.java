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

    //delete an office
    public void deleteOffice(Long id) {
        officeRepository.deleteById(id);
    }

}
