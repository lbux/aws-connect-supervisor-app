
README for /rest-service

**TO-DO**:
- change magic start and end epoch times to last 30 seconds
- move all objects and records to /util
- create InsightController (with /insights endpoint) that calls all other controllers
  - check and save output JSON format
  - move queue store from connect handler to insight controller
- change avghandletime queue id from basic to iter over all queues
- make QueueStore a hashmap instead of arraylist