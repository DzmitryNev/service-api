/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.epam.ta.reportportal.store.commons;

import com.epam.ta.reportportal.store.database.entity.enums.ProjectRoleEnum;
import com.epam.ta.reportportal.store.database.entity.enums.StatusEnum;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

/**
 * Several validation checks
 *
 * @author Andrei Varabyeu
 */
public class Preconditions {

	private Preconditions() {

	}

	/**
	 * grabbed from {@link UUID#fromString(String)}
	 */
	public static final Predicate<String> IS_UUID = uuid -> uuid.split("-").length == 5;

	public static final Predicate<Collection<?>> NOT_EMPTY_COLLECTION = t -> null != t && !t.isEmpty();

	public static final Predicate<Optional<?>> IS_PRESENT = Optional::isPresent;

	public static Predicate<FinishExecutionRQ> finishSameTimeOrLater(final Date startTime) {
		return input -> input.getEndTime().getTime() >= startTime.getTime();
	}

	/**
	 * Start time of item to be creates is later than provided start time
	 *
	 * @param startTime
	 * @return
	 */
	public static Predicate<StartTestItemRQ> startSameTimeOrLater(final Date startTime) {
		return input -> input.getStartTime() != null && input.getStartTime().getTime() >= startTime.getTime();
	}

	public static Predicate<StatusEnum> statusIn(final StatusEnum... statuses) {
		return input -> ArrayUtils.contains(statuses, input);
	}

	/**
	 * Checks whether iterable contains elements matchers provided predicate
	 *
	 * @param filter
	 * @return
	 */
	public static <T> Predicate<Iterable<T>> contains(final Predicate<T> filter) {
		return iterable -> StreamSupport.stream(iterable.spliterator(), false).anyMatch(filter);
	}

	/**
	 * Checks whether map contains provided key
	 *
	 * @param key
	 * @return
	 */
	public static <K> Predicate<Map<K, ?>> containsKey(final K key) {
		return map -> null != map && map.containsKey(key);
	}

	/**
	 * Check whether user (principal) has enough role level
	 *
	 * @param principalRole
	 * @return
	 */
	public static Predicate<ProjectRoleEnum> isLevelEnough(final ProjectRoleEnum principalRole) {
		return principalRole::sameOrHigherThan;
	}
}