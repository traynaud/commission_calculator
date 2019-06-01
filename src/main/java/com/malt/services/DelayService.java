package com.malt.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malt.model.Delay;
import com.malt.repositories.DelayRepository;

/**
 * This {@link Service} manage the everything related to {@link Delay}s
 *
 * @author Tanguy
 * @version 1.0
 * @since 01 June 2019
 *
 */
@Service
public class DelayService {

	@Autowired
	private DelayRepository delayRepository;

	/**
	 * Save a delay in Database
	 *
	 * @param delay {@link Delay}
	 * @return {@link Delay}
	 */
	@Transactional
	public Delay persist(final Delay delay) {
		return delayRepository.save(delay);
	}

	@Transactional(readOnly = true)
	public Optional<Delay> getById(final Long id) {
		return delayRepository.findById(id);
	}

	@Transactional
	public Delay createAndPersist(final int years, final int months, final int days, final int hours, final int minutes,
			final int seconds) {
		Delay delay = new Delay();
		delay.setYears(years);
		delay.setMonths(months);
		delay.setDays(days);
		delay.setHours(hours);
		delay.setMinutes(minutes);
		delay.setSeconds(seconds);
		delay = delayRepository.save(delay);
		return delay;
	}
}
