## README for /rest-service

### _Notes:_

- Moved ConnectInstance init into Handler for cohesion. Even though each endpoint inits new Handler (therefore new
  ConnectInstance and new listQueues calls), this should ok since these endpoints will not be used (only main
  /insights)

### _TO-DO:_

- End of day report
- Historical insights
- Service for best agents to recommend under action
- maintaining the same insight (same insight id) even after refresh until resolved
    - could provide insight in how long it takes for certain types of insights to get resolved?
- identifying which agents to reassign to a queue
    - already have problematic queue id
    - iter through other queues -> getCurrentUserData to get agent ids and their status & num chats/voice they can take
    - start with no logic for agent selection, just pick first n agents?
- explore later: combine all metricdatav2 requests into one

service level todo:
- 

avg handling time todo:

- Compare agent's time to queue's and return insight if wayyy over
- write proper insight, reason, and action

### _Example Insight List (called by and sent to Frontend):_

```json
{
  "id": "IL-1",
  "timestamp": "2024-05-14 12:31:11 AM PDT",
  "insights": [
    {
      "id": 2,
      "value": 28.5714285714286,
      "insight": "Service Level 15 for Basic Queue has dropped below 40%",
      "reason": "Basic Queue (ID: 19dfef86-2020-46d3-b881-976564077825) Service Level 15 (percentage of contacts answered within past 15 seconds) has dropped below 40%. Low answer rate indicates low efficiency and could lead to increased customer dissatisfaction, increased abandon rates. Current agents may also experience difficulties with increased contact volume.",
      "action": "To improve SL 15, consider optimizing staffing level by assigning more available agents to Basic queue."
    },
    {
      "id": 3,
      "value": 28.5714285714286,
      "insight": "Service Level 15 for Basic Queue has dropped below 40%",
      "reason": "Basic Queue (ID: 19dfef86-2020-46d3-b881-976564077825) Service Level 15 (percentage of contacts answered within past 15 seconds) has dropped below 40%. Low answer rate indicates low efficiency and could lead to increased customer dissatisfaction, increased abandon rates. Current agents may also experience difficulties with increased contact volume.",
      "action": "To improve SL 15, consider optimizing staffing level by assigning more available agents to Basic queue."
    }
  ]
}
```
