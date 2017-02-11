# OpenMUC Console Commands
Apache Felix Gogo console commands for accessing OpenMUC. The commands allow to scan for devices and channels as well as read and write values.

## Usage
This bundle extends the Felix Gogo shell with commands that allow to access OpenMUC functionality. Details about using the shell
can be found in the [Apache Felix documentation](https://felix.apache.org/documentation/subprojects/apache-felix-framework/apache-felix-framework-usage-documentation.html). A list of all available commands in the shell can be shown by typing `help`. For further information about a specific command type `help` followed by the command name, e.g. `help read`.

## Commands
The following table gives an overview about the available commands. The scope of all commands is `openmuc`.
A short description can also be accessed directly from the gogo shell by typing `help <command>`.

| Command | Description | Parameters |
|:--------------|:----------------------------|:-----------------|
| channels | Shows a list of all configured channel ids | - |
| config | Shows the actual OpenMUC configuration | - |
| drivers | Shows the ids of all running OpenMUC drivers | - |
| read | Reads the value of the specified channel | `<channelId>` |
| scanForChannels | Scans for channels of a specific device | `<deviceAddress> [<settings>]` |
| scanForDevices | Scans for devices with a specific driver | `<driverId> [<settings>]` |
| write | Writes the value of the specified channel | `<channelId> <value>` |

Please note that parameters like settings may be driver or device specific. As the channels configuration may
be set in the `channels.xml` file, no commands for editing the configuration (e.g. adding a device) are included.

## How to implement own Gogo commands
A good tutorial for implementing own Gogo commands can be found [at this website](http://coders-kitchen.com/2012/04/06/tutorial-an-own-gogo-shell-command/). If OSGi's [declarative services](http://blog.vogella.com/2016/06/21/getting-started-with-osgi-declarative-services/) should be used, the neccessary properties `osgi.command.scope` and `osgi.command.function` must be set in the component description XML file like in the following example:

```xml
<property name="osgi.command.scope" type="String" value="openmuc"/>
<property name="osgi.command.function">
channels
config
drivers
read
scanForChannels
scanForDevices
write
</property>
```

The property `osgi.command.function` declares the provided commands of the service component as an array of strings. The implementation function names *must* match the declared function names. As an example, the full component definition of this project can be found [here](https://github.com/openmucextensions/console/blob/master/src/main/resources/OSGI-INF/components.xml). Don't forget to provide the class that implements the commands as a service in the component definition, although no service interface has been implemented.

To provide information for the `help` command of Gogo, the Gogo runtime package provides the annotation `@Descriptor`, that can be used for both, methods and parameters. The following example shows the usage of the annotation:

```java
@Descriptor("scans for devices with a specific driver")
public void scanForDevices(@Descriptor("the driver id") String driverId) {
	// method implementation
}
```
