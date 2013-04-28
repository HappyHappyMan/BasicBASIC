BasicBASIC
==========

A horribly basic BASIC interpreter written in Java.

Currently supports programmatic entry and direct entry. i.e., you can either enter in
full programs of the type:

	10 PRINT x
	20 PRINT y
	30 IF (x) PRINT z

or just directly at the prompt:
	> PRINT x
 
 and it will work. 

 Keywords include LIST (lists what commands are stored in the programmatic buffer for
 execution) and RESEQUENCE (resequences your line numbers to start at 10 and increment
 in tens like BASIC code should be).

 Currently has IF, FOR/NEXT, and GOTO implemented as far as control flow goes. IF and FOR
 should only be used in programmatic entry, though currently I don't enforce that in 
 the code. 

 IF will execute the command if the conditional expression is >= 0.

 FOR initially sets the control variable equal to the first parameter, then increments
 said control variable until it is equal to the second parameter. NEXT denotes the end
 of the loop.

 An example program, demonstrating the syntax, follows:

 	10 LET x = 4
 	20 LET y = 3
 	30 LET z = 7
 	40 FOR I = y, z
 	50 IF (x - I) PRINT x
 	55 PRINT y
 	60 NEXT I
 	65 GOTO 55

 This command should output
 	x = 4.0
 	y = 3.0
 	x = 4.0
 	y = 3.0
 	x = 4.0
 	y = 3.0
 	y = 3.0
 	y = 3.0
 	y = 3.0

 


