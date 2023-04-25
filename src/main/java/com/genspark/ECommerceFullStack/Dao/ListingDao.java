package com.genspark.ECommerceFullStack.Dao;

import com.genspark.ECommerceFullStack.Entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingDao extends JpaRepository<Listing, Integer> {
}
