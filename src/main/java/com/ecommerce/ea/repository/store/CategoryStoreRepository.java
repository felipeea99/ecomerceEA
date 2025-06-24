package com.ecommerce.ea.repository.store;

import com.ecommerce.ea.entities.store.CategoryStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryStoreRepository extends JpaRepository<CategoryStore, Integer> {
    @Query("SELECT cs FROM CategoryStore cs WHERE cs.store = :store")
    List<CategoryStore> findAllCategoriesByStoreId(@Param("store") UUID store);
}
