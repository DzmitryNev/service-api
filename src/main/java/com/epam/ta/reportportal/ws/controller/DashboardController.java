/*
 * Copyright 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.ws.controller;

import com.epam.ta.reportportal.auth.ReportPortalUser;
import com.epam.ta.reportportal.core.dashboard.DeleteDashboardHandler;
import com.epam.ta.reportportal.core.dashboard.ICreateDashboardHandler;
import com.epam.ta.reportportal.core.dashboard.IGetDashboardHandler;
import com.epam.ta.reportportal.core.dashboard.IUpdateDashboardHandler;
import com.epam.ta.reportportal.entity.dashboard.Dashboard;
import com.epam.ta.reportportal.ws.converter.converters.DashboardConverter;
import com.epam.ta.reportportal.ws.model.EntryCreatedRS;
import com.epam.ta.reportportal.ws.model.OperationCompletionRS;
import com.epam.ta.reportportal.ws.model.SharedEntity;
import com.epam.ta.reportportal.ws.model.dashboard.AddWidgetRq;
import com.epam.ta.reportportal.ws.model.dashboard.CreateDashboardRQ;
import com.epam.ta.reportportal.ws.model.dashboard.DashboardResource;
import com.epam.ta.reportportal.ws.model.dashboard.UpdateDashboardRQ;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.auth.permissions.Permissions.ASSIGNED_TO_PROJECT;
import static com.epam.ta.reportportal.util.ProjectExtractor.extractProjectDetails;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Pavel Bortnik
 */
@RestController
@PreAuthorize(ASSIGNED_TO_PROJECT)
@RequestMapping("/{projectName}/dashboard")
public class DashboardController {

	private final ICreateDashboardHandler createDashboardHandler;
	private final IUpdateDashboardHandler updateDashboardHandler;
	private final IGetDashboardHandler getDashboardHandler;
	private final DeleteDashboardHandler deleteDashboardHandler;

	@Autowired
	public DashboardController(ICreateDashboardHandler createDashboardHandler, IUpdateDashboardHandler updateDashboardHandler,
			IGetDashboardHandler getDashboardHandler, DeleteDashboardHandler deleteDashboardHandler) {
		this.createDashboardHandler = createDashboardHandler;
		this.updateDashboardHandler = updateDashboardHandler;
		this.getDashboardHandler = getDashboardHandler;
		this.deleteDashboardHandler = deleteDashboardHandler;
	}

	@Transactional
	@PostMapping
	@ResponseStatus(CREATED)
	@ApiOperation("Create dashboard for specified project")
	public EntryCreatedRS createDashboard(@PathVariable String projectName, @RequestBody @Validated CreateDashboardRQ createRQ,
			@AuthenticationPrincipal ReportPortalUser user) {
		return createDashboardHandler.createDashboard(extractProjectDetails(user, projectName), createRQ, user);
	}

	@Transactional
	@DeleteMapping(value = "/{dashboardId}")
	@ResponseStatus(OK)
	@ApiOperation("Remove dashboard for specified project")
	public OperationCompletionRS deleteDashboard(@PathVariable String projectName, @PathVariable Long dashboardId,
			@AuthenticationPrincipal ReportPortalUser user) {
		return deleteDashboardHandler.deleteDashboard(dashboardId, extractProjectDetails(user, projectName), user);
	}

	@Transactional(readOnly = true)
	@GetMapping
	@ResponseStatus(OK)
	@ApiOperation("Get all dashboard resources for specified project")
	public Iterable<DashboardResource> getAllDashboards(@PathVariable String projectName, @AuthenticationPrincipal ReportPortalUser user) {
		List<Dashboard> allDashboards = getDashboardHandler.getAllDashboards(extractProjectDetails(user, projectName), user);
		return allDashboards.stream().map(DashboardConverter.TO_RESOURCE).collect(Collectors.toList());
	}

	@Transactional
	@PutMapping("/{dashboardId}/add")
	@ResponseStatus(CREATED)
	@ApiOperation("Add widget to specified dashboard")
	public OperationCompletionRS addWidget(@PathVariable String projectName, @PathVariable Long dashboardId,
			@RequestBody @Validated AddWidgetRq addWidgetRq, @AuthenticationPrincipal ReportPortalUser user) {
		return updateDashboardHandler.addWidget(dashboardId, extractProjectDetails(user, projectName), addWidgetRq, user);
	}

	@Transactional
	@DeleteMapping("/{dashboardId}/{widgetId}")
	@ResponseStatus(OK)
	@ApiOperation("Remove widget from specified dashboard")
	public OperationCompletionRS removeWidget(@PathVariable String projectName, @PathVariable Long dashboardId, @PathVariable Long widgetId,
			@AuthenticationPrincipal ReportPortalUser user) {
		return updateDashboardHandler.removeWidget(widgetId, dashboardId, extractProjectDetails(user, projectName), user);
	}

	@Transactional
	@PutMapping(value = "/{dashboardId}")
	@ResponseStatus(OK)
	@ApiOperation("Update specified dashboard for specified project")
	public OperationCompletionRS updateDashboard(@PathVariable String projectName, @PathVariable Long dashboardId,
			@RequestBody @Validated UpdateDashboardRQ updateRQ, @AuthenticationPrincipal ReportPortalUser user) {
		return updateDashboardHandler.updateDashboard(extractProjectDetails(user, projectName), updateRQ, dashboardId, user);
	}

	@Transactional
	@GetMapping(value = "/{dashboardId}")
	@ResponseStatus(OK)
	@ApiOperation("Get specified dashboard by ID for specified project")
	public DashboardResource getDashboard(@PathVariable String projectName, @PathVariable Long dashboardId,
			@AuthenticationPrincipal ReportPortalUser user) {
		Dashboard dashboard = getDashboardHandler.getDashboard(dashboardId, extractProjectDetails(user, projectName), user);
		return DashboardConverter.TO_RESOURCE.apply(dashboard);
	}

	@GetMapping(value = "/shared")
	@ResponseStatus(OK)
	@ApiOperation("Get names of shared dashboards from specified project")
	public Iterable<SharedEntity> getSharedDashboardsNames(@PathVariable String projectName, Principal principal, Pageable pageable) {
		return null;
	}

}
