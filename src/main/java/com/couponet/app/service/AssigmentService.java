package com.couponet.app.service;

import com.couponet.app.model.Assigment;
import com.couponet.app.model.Coupon;
import com.couponet.app.model.User;
import com.couponet.app.repo.AssigmentRepo;
import com.couponet.app.repo.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssigmentService {
    private final AssigmentRepo assigmentRepo;
    private final CouponRepo couponRepo;

    @Autowired
    public AssigmentService(AssigmentRepo assigmentRepo, CouponRepo couponRepo) {
        this.assigmentRepo = assigmentRepo;
        this.couponRepo = couponRepo;
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

    public List<Assigment> findAssigmentsByUserId(int userId){
        return assigmentRepo.findAssigmentsByUser_Id(userId);
    }

    public boolean deleteAssigmentById(int assigmentId){
        boolean control = false;
        if (assigmentRepo.existsById(assigmentId)){
            assigmentRepo.deleteById(assigmentId);
            control = true;
        }
        return control;
    }

/*    public Assigment createAssigment(Assigment newAssigment){
        Assigment tempAssigment = null;
        if (!assigmentRepo.existsById(newAssigment.getId())){
            tempAssigment = assigmentRepo.save(newAssigment);
        }
        return tempAssigment;
    }*/

    // Check Coupon Stock
    // Check User's Assigments (Check Max Coupons Per User)
    public Assigment createAssigment(User user, Coupon coupon){
        Assigment tempAssigment = null;
        if (assigmentRepo.findAssigmentsByUser_Id(user.getId()).size()<coupon.getMaxPerUser() && coupon.getStock()>0){
            // Create Assigment
            tempAssigment = assigmentRepo.save(new Assigment(user, coupon));

            // Update Coupon Stock
            coupon.setStock(coupon.getStock()-1);
            couponRepo.save(coupon);
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