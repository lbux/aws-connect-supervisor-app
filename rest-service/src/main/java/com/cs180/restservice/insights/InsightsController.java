package com.cs180.restservice.insights;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.avgHandleTime.AvgHandleTimeController;
import com.cs180.restservice.serviceLevel.ServiceLevelController;
import com.cs180.restservice.util.AgentInfo;
import com.cs180.restservice.util.ConnectInstance;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.Insights;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@RestController
public class InsightsController {
    private static final Logger logger = LoggerFactory.getLogger(InsightsController.class);

//    public static ConnectInstance instance = new ConnectInstance();

    @GetMapping("/insights")
    public Insights insights() {

        logger.info("/// TESTING LOGGER OUTPUT ///");

        ConnectHandler handler = new ConnectHandler();

//        handler.sendRequestPopulateQueues(instance);

        logger.info("/// QUEUE STORE OUTPUT ///");
        logger.info(handler.instance.getQueues().toString());

        ArrayList<Insight> insightList = new ArrayList<>();

        // INSIGHT #1: queue's service level over 40%
        ServiceLevelController.getServiceLevelQueueInsights(handler)
                .map(Insights::insights)
                .ifPresent(insightList::addAll);

        // INSIGHT #2: an agent's avg handling time is way over their queue level over 40%
        AvgHandleTimeController.getAvgHandleTimeQueueInsights(handler)
                .map(Insights::insights)
                .ifPresent(insightList::addAll);

//        Set<String> agentIds = handler.sendRequestAgentsInQueue("19dfef86-2020-46d3-b881-976564077825");
//        System.out.println("Agents in basic queue:");
//
//        for (String agentId : agentIds) {
//            System.out.println("Agent ID: " + agentId);
//            AgentInfo agentInfo = handler.sendRequestAgentInfo(agentId);
//            System.out.println("Agent Info: " + agentInfo);
//        }

        Insights insights = new Insights(insightList);

        return insights;
    }
}
