AutoLoader README:
-------------------
This is a Java based application that monitors a directory for new files and then signals Record
Loader to load those files into your MarkLogic server. The usage from the command prompt is as follows:
java AutoLoader {polling directory} {completed directory} {path to RecordLoader properties file}

The Polling directory should be the absolute path to the directory being monitored for new files to be loaded.

The completed directory should be the absolute path to the directory to which files will be moved after they have been detected. IMPORTANT NOTE: This is the directory to which you MUST point RecordLoader via the INPUT_PATH property in your RecordLoader properties file.

The final parameter is the absolute path to the RecordLoader properties file that will be used to configure RecordLoader.

Questions, comments, suggestions can be emailed to clark.richey@marklogic.com

Enjoy!
