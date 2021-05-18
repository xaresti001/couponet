package com.couponet.app.service;

import com.couponet.app.model.Assigment;
import com.couponet.app.model.Coupon;
import com.couponet.app.model.User;
import com.couponet.app.repo.AssigmentRepo;
import com.couponet.app.repo.CouponRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
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

    public boolean deleteAssigmentById(int assigmentId){
        boolean control = false;
        Optional<Assigment> foundAssigment = assigmentRepo.findById(assigmentId);
        if (foundAssigment.isPresent()){
            // Update Coupon Stock
            Coupon tempCoupon = foundAssigment.get().getCoupon();
            tempCoupon.setStock(tempCoupon.getStock()+1);
            couponRepo.save(tempCoupon);

            // Delete Assigment
            assigmentRepo.deleteById(assigmentId);
            control = true;
        }
        return control;
    }

    // Check Coupon Stock
    // Check User's Assigments (Check Max Coupons Per User)
    public Assigment createAssigment(Assigment newAssigment){
        Assigment tempAssigment = null;
        if (assigmentRepo.findAssigmentsByClientId(newAssigment.getClientId()).size()<newAssigment.getCoupon().getMaxPerUser() && newAssigment.getCoupon().getStock()>0){
            // Create Assigment
            tempAssigment = assigmentRepo.save(newAssigment);

            // Update Coupon Stock
            Coupon tempCoupon = newAssigment.getCoupon();
            tempCoupon.setStock(tempCoupon.getStock()-1);
            couponRepo.save(tempCoupon);
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

    public List<Assigment> findAssigmentsByOwnerId(int ownerId){
        return assigmentRepo.findAssigmentsByCoupon_OwnerUser_Id(ownerId);
    }

    public List<Assigment> findAssigmentsByClientId(int userId){
        return assigmentRepo.findAssigmentsByClientId(userId);
    }

}