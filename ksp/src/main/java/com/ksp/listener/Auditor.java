package com.ksp.listener;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ksp.domain.common.Audit;
import com.ksp.domain.common.Auditable;
import com.ksp.enums.ActionType;
import com.ksp.utils.BeanUtils;
import com.ksp.utils.RequestUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Auditor {
	private static final String ID = "id";

	@Autowired
	private BeanUtils beanUtils;

	@PreUpdate
	public void preUpdate(Auditable entity)
			throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
		System.err.println(" in preUpdate for testing");
		if (!entity.getHistory().isNoHistoryRequired()) {
			entity.getHistory().setEntityId(this.getPrimaryId(entity));
			this.auditOnUpdate(entity);
		}
	}

	@PreRemove
	public void preDelete(Auditable entity)
			throws IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
		System.err.println(" in preDelete for testing");
		if (!entity.getHistory().isNoHistoryRequired()) {
			entity.getHistory().setEntityId(this.getPrimaryId(entity));
			this.auditOnDelete(entity);
		}
	}

	private void auditOnUpdate(Auditable entity)
			throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
		Map<String, FieldData> newValueMap = this.prepareNewValueMap(entity);
		Map<String, FieldData> oldValueMap = this.prepareOldValueMap(entity);
		EntityManager em = beanUtils.getBean(EntityManager.class);
		newValueMap.keySet().forEach(fieldName -> {
			if (((newValueMap.get(fieldName).getValue() != null)
					&& (oldValueMap.get(fieldName).getValue() == null))
					|| ((newValueMap.get(fieldName).getValue() == null)
							&& (oldValueMap.get(fieldName).getValue() != null))
					|| ((newValueMap.get(fieldName).getValue() != null)
							&& (oldValueMap.get(fieldName).getValue() != null)
							&& !newValueMap.get(fieldName).getValue()
									.equals(oldValueMap.get(fieldName).getValue()))) {
				Audit audit = new Audit();
				audit.setActionTaken(ActionType.UPDATE);
				this.setAuditInfo(entity, newValueMap, oldValueMap, fieldName, audit);
				em.persist(audit);
			}
		});
	}

	private void auditOnDelete(Auditable entity)
			throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
		log.debug("Inside auditOnDelete");
		Map<String, FieldData> newValueMap = new HashMap<>();
		Map<String, FieldData> oldValueMap = this.prepareOldValueMap(entity);
		EntityManager em = beanUtils.getBean(EntityManager.class);
		oldValueMap.keySet().forEach(fieldName -> {
			if (oldValueMap.get(fieldName).getValue() != null) {
				Audit audit = new Audit();
				audit.setActionTaken(ActionType.DELETE);
				this.setAuditInfo(entity, newValueMap, oldValueMap, fieldName, audit);
				em.persist(audit);
			}
		});
	}

	private void setAuditInfo(Auditable entity, Map<String, FieldData> newValueMap,
			Map<String, FieldData> oldValueMap, String fieldName, Audit audit) {
		audit.setEntityId(entity.getHistory().getEntityId());
		audit.setNewValue(
				!CollectionUtils.isEmpty(newValueMap) ? newValueMap.get(fieldName).getValue()
						: null);
		audit.setOldValue(
				!CollectionUtils.isEmpty(oldValueMap) ? oldValueMap.get(fieldName).getValue()
						: null);
		audit.setEntityName(entity.getClass().getSimpleName());
		audit.setFieldName(fieldName);
		audit.setTableName(entity.getClass().getDeclaredAnnotation(Table.class).name());
		audit.setColumnName(
				!CollectionUtils.isEmpty(oldValueMap) ? oldValueMap.get(fieldName).getColumnName()
						: newValueMap.get(fieldName).getColumnName());
		audit.setActionDate(new Date().getTime());
		audit.setActionBy(RequestUtils.loggedInUser());

	}

	public static boolean isFieldIgnorable(Field field) {
		return Set.class.isAssignableFrom(field.getType())
				|| List.class.isAssignableFrom(field.getType()) || field.getName().equals(ID);
	}

	private Map<String, FieldData> prepareNewValueMap(Auditable entity)
			throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
		Map<String, FieldData> newValueMap = new HashMap<>();
		for (Field field : Class.forName(entity.getClass().getName()).getDeclaredFields()) {
			field.setAccessible(Boolean.TRUE);
			if (!Auditor.isFieldIgnorable(field)) {
				if (this.isAuditableType(field)) {
					this.setReferenceFields(newValueMap, entity, field);
				} else {
					Auditor.FieldData fieldData = this.setColumnName(field);
					fieldData.setValue(
							field.get(entity) != null ? field.get(entity).toString() : null);
					newValueMap.put(field.getName(), fieldData);
				}
			}
		}
		return newValueMap;
	}

	private Map<String, FieldData> prepareOldValueMap(Auditable entity)
			throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
		EntityManager em = beanUtils.getBean(EntityManager.class);
		Session session = em.unwrap(Session.class);
		Auditable existingEntity = session.find(entity.getClass(), this.getPrimaryId(entity));
		Map<String, FieldData> oldValueMap = new HashMap<>();
		for (Field field : Class.forName(existingEntity.getClass().getName()).getDeclaredFields()) {
			if (!Auditor.isFieldIgnorable(field)) {
				field.setAccessible(Boolean.TRUE);
				if (this.isAuditableType(field)) {
					this.setReferenceFields(oldValueMap, existingEntity, field);
				} else {
					Auditor.FieldData fieldData = this.setColumnName(field);
					fieldData.setValue(
							field.get(existingEntity) != null ? field.get(existingEntity).toString()
									: null);
					oldValueMap.put(field.getName(), fieldData);
				}
			}
		}
		return oldValueMap;
	}

	private void setReferenceFields(Map<String, FieldData> oldValueMap, Auditable existingEntity,
			Field field)
			throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
		Auditable refrenceEntity = (Auditable) field.get(existingEntity);
		Field referenceField = Class.forName(field.getType().getTypeName()).getDeclaredField(ID);
		referenceField.setAccessible(Boolean.TRUE);
		Auditor.FieldData fieldData = this.setColumnName(field);
		fieldData.setValue(referenceField.get(refrenceEntity) != null
				? referenceField.get(refrenceEntity).toString()
				: null);
		oldValueMap.put(field.getName(), fieldData);
	}

	private Auditor.FieldData setColumnName(Field field) {
		Auditor.FieldData fieldData = new Auditor.FieldData();
		String columnName;
		Column column = field.getDeclaredAnnotation(Column.class);
		if (column != null) {
			columnName = column.name();
		} else {
			JoinColumn joinColumn = field.getDeclaredAnnotation(JoinColumn.class);
			columnName = joinColumn.name();
		}
		fieldData.setColumnName(columnName);
		return fieldData;
	}

	private String getPrimaryId(Auditable entity)
			throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
		Field field = Class.forName(entity.getClass().getName()).getDeclaredField(ID);
		field.setAccessible(Boolean.TRUE);
		return field.get(entity).toString();
	}

	private boolean isAuditableType(Field field) {
		return Auditable.class.isAssignableFrom(field.getType());
	}

	@Setter
	@Getter
	class FieldData {
		String value;
		String columnName;
	}

}