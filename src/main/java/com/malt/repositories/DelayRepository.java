package com.malt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malt.model.Delay;

public interface DelayRepository extends JpaRepository<Delay, Long> {

}
