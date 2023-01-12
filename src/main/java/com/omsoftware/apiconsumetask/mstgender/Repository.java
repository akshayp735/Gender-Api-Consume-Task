package com.omsoftware.apiconsumetask.mstgender;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Gender, Long> {

    Gender findByGenderId(Long id);
}
