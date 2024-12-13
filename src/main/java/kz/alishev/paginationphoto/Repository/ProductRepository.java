package kz.alishev.paginationphoto.Repository;

import kz.alishev.paginationphoto.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}