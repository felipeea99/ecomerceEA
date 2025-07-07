package com.ecommerce.prorider.repository.store;

import com.ecommerce.prorider.entities.store.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

}
