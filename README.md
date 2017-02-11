# OpenMUC console commands
Apache Gogo console commands for accessing OpenMUC. The commands (will) allow to scan for devices and channels,
added/update/delete channels as well as read and write values.

## Commands
The following table gives an overview about the available commands. The scope of all commands is `openmuc`.
A short description can also be accessed directly from the gogo shell by typing `help <command>`.

| Command | Description | Parameters |
|:--------------|:----------------------------|:-----------------|
| drivers | Shows the ids of all running OpenMUC drivers | - |
| scanForDevices | Scans for devices with a specific driver | `<driverId> [<settings>]` |
| config | Shows the actual OpenMUC configuration | - |

Please note that parameters like settings may be driver or device specific. As the channels configuration may
be set in the `channels.xml` file, no commands for editing the configuration (e.g. adding a device) are included.
