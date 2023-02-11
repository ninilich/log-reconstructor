# Into
We have microservices, which outputs some log describing the boundaries of an HTTP request
with the following format:
> [start-timestamp] [end-timestamp] [trace] [service-name] [caller-span]->[span]

The application needs to read these logs from standard input (one log entry per line), reconstruct logs as 
a JSON-tree and to write it to standard output (one JSON per line). Input lines can be
slightly out of order, but they should be processed anyway.

Other specific details could be not publicly announced.

# Short description of the solution
1. All log lines are transformed into case classes; incorrect log lines are ignored. 
2. A queue is used to manage "pending" entries.
3. "Buffer" is used for temporary storage of log lines; "Buffer" is a Map (trace -> Set of log lines).
4. Finished traces are transformed into a Tree (also a case class). If the transformation was successful, 
the Tree is sent to the output in JSON format.