package com.couponet.app.service;

import com.couponet.app.model.Assigment;
import com.couponet.app.repo.AssigmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssigmentService {
    private final AssigmentRepo assigmentRepo;

    @Autowired
    public AssigmentService(AssigmentRepo assigmentRepo) {
        this.assigmentRepo = assigmentRepo;
    }

    public List<Assigment> findAllAssigments(){
        return assigmentRepo.findAll();
    }

    public Assigment findAssigmentById(int assigmentId){
        Assigment tempAssigment = null;
        Optional<Assigment> foundAssigment = assigmentRepo.findById(assigmentId);
        if (foundAssigment.isPresent()){
            tempAssigment = foundAssigment.get();
        }
        return tempAssigment;
    }

    public boolean deleteAssigmentById(int assigmentId){
        boolean control = false;
        if (assigmentRepo.existsById(assigmentId)){
            assigmentRepo.deleteById(assigmentId);
            control = true;
        }
        return control;
    }

    public Assigment createAssigment(Assigment newAssigment){
        Assigment tempAssigment = null;
        if (!assigmentRepo.existsById(newAssigment.getId())){
            tempAssigment = assigmentRepo.save(newAssigment);
        }
        return tempAssigment;
    }

    public Assigment updateAssigment(Assigment updateAssigment){
        Assigment tempAssigment = null;
        Optional<Assigment> foundAssigment = assigmentRepo.findById(updateAssigment.getId());
        if (foundAssigment.isPresent()){
            updateAssigment.setRegistrationDateTime(foundAssigment.get().getRegistrationDateTime());
            tempAssigment = assigmentRepo.save(updateAssigment);
        }
        return tempAssigment;
    }
}
