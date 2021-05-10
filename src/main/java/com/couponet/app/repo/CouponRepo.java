package com.couponet.app.repo;

import com.couponet.app.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    List<Coupon> findCouponsByOwnerUser_Id(int ownerUser_id);
}