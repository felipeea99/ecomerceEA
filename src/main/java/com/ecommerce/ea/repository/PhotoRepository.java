package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Number> {
    @Query("SELECT p FROM Photo p WHERE p.product = :product")
    List<Photo> findAllPhotosByProductId(@Param("product") int product);
    @Query("SELECT p FROM Photo p WHERE p.product = :product")
    List<Photo> findByProductIDOrderByPhotosID(@Param("product") int product);
    @Query("SELECT p FROM Photo p WHERE p.product = :product AND p.index = 0")
    List<Photo> findByIndexZero(@Param("product") int product);

}
