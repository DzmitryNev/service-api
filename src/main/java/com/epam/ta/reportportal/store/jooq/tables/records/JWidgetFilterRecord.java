/*
 * This file is generated by jOOQ.
*/
package com.epam.ta.reportportal.store.jooq.tables.records;

import com.epam.ta.reportportal.store.jooq.tables.JWidgetFilter;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;

/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.10.5" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JWidgetFilterRecord extends UpdatableRecordImpl<JWidgetFilterRecord> implements Record2<Integer, Long> {

	private static final long serialVersionUID = 1773382587;

	/**
	 * Setter for <code>public.widget_filter.widget_id</code>.
	 */
	public void setWidgetId(Integer value) {
		set(0, value);
	}

	/**
	 * Getter for <code>public.widget_filter.widget_id</code>.
	 */
	public Integer getWidgetId() {
		return (Integer) get(0);
	}

	/**
	 * Setter for <code>public.widget_filter.filter_id</code>.
	 */
	public void setFilterId(Long value) {
		set(1, value);
	}

	/**
	 * Getter for <code>public.widget_filter.filter_id</code>.
	 */
	public Long getFilterId() {
		return (Long) get(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record2<Integer, Long> key() {
		return (Record2) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Long> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Long> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return JWidgetFilter.WIDGET_FILTER.WIDGET_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field2() {
		return JWidgetFilter.WIDGET_FILTER.FILTER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer component1() {
		return getWidgetId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long component2() {
		return getFilterId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getWidgetId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value2() {
		return getFilterId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JWidgetFilterRecord value1(Integer value) {
		setWidgetId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JWidgetFilterRecord value2(Long value) {
		setFilterId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JWidgetFilterRecord values(Integer value1, Long value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached JWidgetFilterRecord
	 */
	public JWidgetFilterRecord() {
		super(JWidgetFilter.WIDGET_FILTER);
	}

	/**
	 * Create a detached, initialised JWidgetFilterRecord
	 */
	public JWidgetFilterRecord(Integer widgetId, Long filterId) {
		super(JWidgetFilter.WIDGET_FILTER);

		set(0, widgetId);
		set(1, filterId);
	}
}