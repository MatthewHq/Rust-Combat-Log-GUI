# Rust-Combat-Log-GUI
For game "Rust" by Facepunch

[Mediafire Download](https://www.mediafire.com/file/96u6rqhw6x0nj74/RustCombatGUI-1.0.jar/file)


### -- Premise --
During play there is a command accessible through the console: "combatlog" which outputs the latest player vs player interaction metrics. These are advantageous to check in the middle of playtime but require an interruption to game input to open console. 

Luckily this command also outputs this data to an output_log.txt file which one could monitor on another screen with a tail cmd on either linux or powershell.

This program takes this a step further by parsing the log file from much more data that is output and clutters the desired data. Initially tried to use java.nio to check for file changes however the output_log.txt is constantly being changed in an unregistered manner towards the OS (Only tested windows). The final solution was to poll and compare the file hash every second, it is a <100kb file size so there is no performance issue whatsoever.

- First version completed in 1 day in a hurry.

# Usage
- Add directory containing output_log.txt to text field or to settings.txt file. If to text field, hit enter on text field and then hit run and make sure settings.txt has only "directory" before lauching. If launched with anything else, it will auto run on that text in the settings.txt interpreted as path.
- id.txt just place "target# name" to display custom names instead of random target id's provided in the combatlog data.

