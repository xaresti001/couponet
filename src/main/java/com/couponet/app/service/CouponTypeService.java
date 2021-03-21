package com.couponet.app.service;

import com.couponet.app.model.CouponType;
import com.couponet.app.repo.CouponTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponTypeService {
    private final CouponTypeRepo couponTypeRepo;

    @Autowired
    public CouponTypeService(CouponTypeRepo couponTypeRepo) {
        this.couponTypeRepo = couponTypeRepo;
    }

    public List<CouponType> findAllCouponTypes(){
        return couponTypeRepo.findAll();
    }

    public CouponType findCouponTypeById(int couponTypeId){
        CouponType tempCouponType = null;
        Optional<CouponType> foundCouponType = couponTypeRepo.findById(couponTypeId);
        if (foundCouponType.isPresent()){
            tempCouponType = foundCouponType.get();
        }
        return tempCouponType;
    }

    public boolean deleteCouponTypeById(int couponTypeId){
        boolean control = false;
        if (!couponTypeRepo.existsById(couponTypeId)){
            couponTypeRepo.deleteById(couponTypeId);
            control = true;
        }
        return control;
    }

    public CouponType createCouponType(CouponType newCouponType){
        CouponType tempCouponType = null;
        if(couponTypeRepo.existsById(newCouponType.getId())){
            tempCouponType = couponTypeRepo.save(newCouponType);
        }
        return tempCouponType;
    }

    public CouponType updateCouponType(CouponType updateCouponType){
        CouponType tempCouponType = null;
        Optional<CouponType> foundCouponType = couponTypeRepo.findById(updateCouponType.getId());
        if (foundCouponType.isPresent()){
            tempCouponType = couponTypeRepo.save(foundCouponType.get());
        }
        return tempCouponType;
    }
}
