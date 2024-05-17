## README for /rest-service

### _Notes:_

- Moved ConnectInstance init into Handler for cohesion. Even though each endpoint inits new Handler (therefore new
  ConnectInstance and new listQueues calls), this should ok since these endpoints will not be used (only main
  /insights)

### _TO-DO:_

- explore later: combine all metricdatav2 requests into one

service level todo:

- Change magic start and end epoch times to the last 30 seconds.
- write real time sl function (don't replace existing)
    - check if no calls and no answers means SL is 0 and gives insight (which it shouldn't)

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