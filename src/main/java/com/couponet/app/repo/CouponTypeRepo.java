package com.couponet.app.repo;

import com.couponet.app.model.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponTypeRepo extends JpaRepository<CouponType, Integer> {
}
