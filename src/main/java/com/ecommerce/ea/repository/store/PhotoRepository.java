package com.ecommerce.ea.repository.store;

import com.ecommerce.ea.entities.store.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Number> {
    @Query("SELECT p FROM Photo p WHERE p.product = :product")
    List<Photo> findAllPhotosByProductId(@Param("product") int product);
    @Query("SELECT p FROM Photo p WHERE p.product = :product")
    List<Photo> findByProductIDOrderByPhotosID(@Param("product") int product);
    @Query("SELECT p FROM Photo p WHERE p.product = :product AND p.index = 0")
    List<Photo> findByIndexZero(@Param("product") int product);

}
