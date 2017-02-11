package org.openmucextensions.console;

import java.util.Date;
import java.util.List;

import org.apache.felix.service.command.Descriptor;
import org.openmuc.framework.config.ChannelConfig;
import org.openmuc.framework.config.ChannelScanInfo;
import org.openmuc.framework.config.ConfigService;
import org.openmuc.framework.config.DeviceConfig;
import org.openmuc.framework.config.DeviceScanInfo;
import org.openmuc.framework.config.DriverConfig;
import org.openmuc.framework.config.RootConfig;
import org.openmuc.framework.data.BooleanValue;
import org.openmuc.framework.data.ByteArrayValue;
import org.openmuc.framework.data.ByteValue;
import org.openmuc.framework.data.DoubleValue;
import org.openmuc.framework.data.FloatValue;
import org.openmuc.framework.data.IntValue;
import org.openmuc.framework.data.LongValue;
import org.openmuc.framework.data.Record;
import org.openmuc.framework.data.ShortValue;
import org.openmuc.framework.data.StringValue;
import org.openmuc.framework.dataaccess.Channel;
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
		
		printLine();
		for (String driver : drivers) {
			System.out.println(driver);
		}
		
		System.out.println(drivers.size() + " driver(s) found");
	}
	
	@Descriptor("scans for devices with a specific driver")
	public void scanForDevices(@Descriptor("the driver id") String driverId, @Descriptor("the driver-specific settings for device scan") String settings) {
		
		try {
			System.out.println("Scanning for devices...");
			List<DeviceScanInfo> infos = configService.scanForDevices(driverId, settings);
			
			printLine();
			for (DeviceScanInfo deviceScanInfo : infos) {
				System.out.println("id: " + deviceScanInfo.getId() + " address: " + deviceScanInfo.getDeviceAddress() + " description: " + deviceScanInfo.getDescription());
			}
			
			System.out.println(infos.size() + " device(s) found");
			
		} catch (Exception e) {
			System.out.println("Error while scanning for devices: " + e.getMessage());
		}
		
	}
	
	@Descriptor("scans for devices with a specific driver")
	public void scanForDevices(@Descriptor("the driver id") String driverId) {
		scanForDevices(driverId, null);
	}
	
	@Descriptor("scans for channels of a specific device")
	public void scanForChannels(@Descriptor("the device id") String deviceId, @Descriptor("the driver-specific settings for channel scan") String settings) {
		
		try {
			List<ChannelScanInfo> infos = configService.scanForChannels(deviceId, settings);
			
			printLine();
			for (ChannelScanInfo channelScanInfo : infos) {
				System.out.println("address: " + channelScanInfo.getChannelAddress() + ", description: " + channelScanInfo.getDescription() + ", meta: " + channelScanInfo.getMetaData());
			}
			
			System.out.println(infos.size() + " channel(s) found");
		} catch (Exception e) {
			System.out.println("Error while scanning for channels: " + e.getMessage());
		}
		
	}
	
	@Descriptor("scans for channels of a specific device")
	public void scanForChannels(@Descriptor("the device id") String deviceId) {
		scanForChannels(deviceId, null);
	}
	
	@Descriptor("shows the actual OpenMUC configuration")
	public void config() {
		
		RootConfig rootConfig = configService.getConfig();
		
		printLine();
		
		if(rootConfig.getDrivers().size() > 0) {
			for (DriverConfig driverConfig : rootConfig.getDrivers()) {
				
				System.out.println("driver: " + driverConfig.getId());
				System.out.println("");
				
				for (DeviceConfig deviceConfig : driverConfig.getDevices()) {
					System.out.println("device: " + deviceConfig.getId() + " (" + deviceConfig.getDescription() + ")");
					System.out.println("");
					
					for (ChannelConfig channelConfig : deviceConfig.getChannels()) {
						System.out.println("channel: " + channelConfig.getId() + " (" + channelConfig.getDescription() + "), address: " + channelConfig.getChannelAddress());
					}
				}
				
				printLine();
			}
		} else {
			System.out.println("The actual OpenMUC configuration is empty");
		}
			
	}
	
	@Descriptor("shows a list of all configured channel ids")
	public void channels() {
		
		List<String> channels = dataAccessService.getAllIds();
		
		printLine();
		for (String channel : channels) {
			System.out.println(channel);
		}
		
		System.out.println(channels.size() + " channel(s) found");
	}
	
	@Descriptor("reads the value of the specified channel")
	public void read(@Descriptor("channel id to read") String channelId) {
		
		Channel channel = dataAccessService.getChannel(channelId);
		
		if(channel != null) {
			Record record = channel.read();
			
			printLine();
			if(record != null) System.out.println("timestamp: " + new Date(record.getTimestamp()).toString() + ", value: " + record.getValue());
		} else {
			System.out.println("Channel " + channelId + " not found");
		}
	}
	
	@Descriptor("writes the value of the specified channel")
	public void write(@Descriptor("the channel id") String channelId, @Descriptor("the value to write") String value) {
		
		Channel channel = dataAccessService.getChannel(channelId);
		
		if(channel != null) {
			
			switch (channel.getValueType()) {
			case INTEGER:
				Integer intVal = Integer.parseInt(value);
				channel.write(new IntValue(intVal.intValue()));
				break;
				
			case BOOLEAN:
				Boolean boolVal = Boolean.parseBoolean(value);
				channel.write(new BooleanValue(boolVal.booleanValue()));
				break;
				
			case BYTE:
				Byte byteVal = Byte.parseByte(value);
				channel.write(new ByteValue(byteVal));
				break;
				
			case BYTE_ARRAY:
				channel.write(new ByteArrayValue(value.getBytes()));
				break;
				
			case DOUBLE:
				Double doubleVal = Double.parseDouble(value);
				channel.write(new DoubleValue(doubleVal.doubleValue()));
				break;
				
			case FLOAT:
				Float floatVal = Float.parseFloat(value);
				channel.write(new FloatValue(floatVal.floatValue()));
				break;
				
			case LONG:
				Long longVal = Long.parseLong(value);
				channel.write(new LongValue(longVal.longValue()));
				break;
				
			case SHORT:
				Short shortVal = Short.parseShort(value);
				channel.write(new ShortValue(shortVal.shortValue()));
				break;

			default:
				channel.write(new StringValue(value));
				break;
			}
			
			read(channelId);
			
		} else {
			System.out.println("Channel " + channelId + " not found");
		}
		
	}
	
	private void printLine() {
		System.out.println("--------------------");
	}
	
}
