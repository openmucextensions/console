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
