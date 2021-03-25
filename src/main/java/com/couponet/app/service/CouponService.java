package com.couponet.app.service;

import com.couponet.app.model.Coupon;
import com.couponet.app.repo.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    private final CouponRepo couponRepo;

    @Autowired
    public CouponService(CouponRepo couponRepo) {
        this.couponRepo = couponRepo;
    }

    public List<Coupon> findAllCoupons(){
        return couponRepo.findAll();
    }

    public Coupon findCouponById(int couponId){
        Coupon tempCoupon = null;
        Optional<Coupon> foundCoupon = couponRepo.findById(couponId);
        if (foundCoupon.isPresent()){
            tempCoupon = foundCoupon.get();
        }
        return tempCoupon;
    }

    public boolean deleteCouponById(int couponId){
        boolean control = false;
        if (couponRepo.existsById(couponId)){
            couponRepo.deleteById(couponId);
            control = true;
        }
        return control;
    }

    public Coupon createCoupon(Coupon newCoupon){
        Coupon tempCoupon = null;
        if (!couponRepo.existsById(newCoupon.getId())){
            tempCoupon = couponRepo.save(newCoupon);
        }
        return tempCoupon;
    }

    public Coupon updateCoupon(Coupon updateCoupon){
        Coupon tempCoupon = null;
        Optional<Coupon> foundCoupon = couponRepo.findById(updateCoupon.getId());
        if (foundCoupon.isPresent()){
            updateCoupon.setRegistrationTime(foundCoupon.get().getRegistrationTime());
            tempCoupon = couponRepo.save(updateCoupon);
        }
        return tempCoupon;
    }
}