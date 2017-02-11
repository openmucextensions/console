package org.openmucextensions.console;

import java.util.List;

import org.apache.felix.service.command.Descriptor;
import org.openmuc.framework.config.ConfigService;
import org.openmuc.framework.dataaccess.DataAccessService;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleCommands {
	
	private static Logger logger = LoggerFactory.getLogger(ConsoleCommands.class);
	
	// OpenMUC services
	private ConfigService configService = null;
	private DataAccessService dataAccessService = null;
	
	protected void activate(ComponentContext context) {
		logger.info("Activating OpenMUC console commands");
	}
	
	protected void deactivate(ComponentContext context) {
		logger.info("Deactivating OpenMUC console commands");
	}
	
	protected void setConfigService(ConfigService service) {
		this.configService = service;
	}
	
	protected void unsetConfigService(ConfigService service) {
		this.configService = null;
	}
	
	protected void setDataAccessService(DataAccessService service) {
		this.dataAccessService = service;
	}
	
	protected void unsetDataAccessService(DataAccessService service) {
		this.dataAccessService = null;
	}
	
	@Descriptor("shows the ids of all running drivers")
	public void drivers() {
		
		List<String> drivers = configService.getIdsOfRunningDrivers();
		
		for (String driver : drivers) {
			System.out.println(driver);
		}
		
		System.out.println(drivers.size() + " driver(s) found");
	}
	
}
