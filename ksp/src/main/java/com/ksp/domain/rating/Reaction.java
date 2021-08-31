package com.ksp.domain.rating;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.ksp.domain.common.Actionable;
import com.ksp.domain.common.Auditable;
import com.ksp.enums.ReactionEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "REACTIONS")
public class Reaction extends Auditable {
	private static final long serialVersionUID = -190045708044203719L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ID")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "USER_REACTION", nullable = false, length = 50)
	private ReactionEnum userReaction;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ACTION_ID")
	private Actionable reactionable;
}
