Name: Daniel Girotto
Student ID: 0783831
Email: dgirotto@uoguelph.ca

NOTE: In order to run the GUI you must invoke the command "export LD_LIBRARY_PATH=." manually.

My parse.y function strips ANY extension off a file, and .java is appended to it afterwards after
parsing is completed. 

To invoke my parser you must type "./yadc <somefile.txt>"

In the above case, "somefile.txt" would be parsed/validated (and if it passes the validation test),
".txt" would be removed from the end of the file and "somefile.txt" would be "somefile.java".

Make sure to omit the "<" character when invoking my parser from the command line. I've made it specifically
accept a command line argument so I can store its name in order to create its subsequent .java file. Since 
somefile.txt.java is not legal, this was necessary for me to do in my circumstances.

Simply type make after entering "export LD_LIBRARY_PATH=." and the GUI will be invoked.

