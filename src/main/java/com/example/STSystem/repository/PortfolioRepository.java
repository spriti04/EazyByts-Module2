package com.example.STSystem.repository;

import com.example.STSystem.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByPersonId(Long personId);
    Portfolio findByPersonIdAndSymbol(Long personId, String symbol);
}
