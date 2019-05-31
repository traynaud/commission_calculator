package com.malt.model.condition;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.malt.model.enums.Parameter;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a terminal named condition, associated to a value
 *
 * @author Tanguy
 * @version 1.0
 * @since 30 May 2019
 *
 */
@Entity
@Table
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public abstract class ValueCondition extends Condition {

	@Enumerated(EnumType.STRING)
	Parameter name;
}
