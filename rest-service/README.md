## README for /rest-service

### _TO-DO:_

- Change magic start and end epoch times to the last 30 seconds.
- Save output in JSON format.
- Change 'avghandletime' queue ID to iterate over all queues.
- Check for unused imports in all files.
- Iterate over all queues for service level, remove Basic Queue
- SL connect handler: remove index out of bounds from catch and add proper check
- remove Constants.java (inserted constants into connectinstance.java)
  - replace constants used in connecthandler.java by passing in ConnectInstance

avg handling time todo:
- Iterate over all queues for avg_handle_time
- Obtain list of agents assigned to a queue ()
- Iterate over list of agents for avg_handle_time
- Compare agent's time to queue's and return insight if wayyy over
- Fill in insight with agent details for insight