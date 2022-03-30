package net.socle.repo;

import net.socle.model.Scheme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemeRepo extends JpaRepository<Scheme,Long> {

    Page<Scheme> findAllByIsActiveAndIsDeleted(Boolean aTrue, Boolean aFalse, Pageable pageable);



    Page<Scheme> findAllByIsActiveAndIsDeletedAndSearchKeyContainingIgnoreCase(Boolean aTrue, Boolean aFalse, Pageable pageable, String toLowerCase);
}
