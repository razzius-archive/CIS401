package orchestration;

import java.util.List;
import java.util.ArrayList;

//
// Analytics Cluster
//
// The Analytics Cluster collects and organizes data from the
// network platform. After being instantiated by the StateManager,
// the Analytics class henceforth

// The state manager will send information about the current state of the machines to the analytics component.
// The state manager can send raw data.
// What if we have a logger print out logging statements, then run the analytics component on the output?
// We can log to two separate files.
// 
// Platform Data to be gathered:
// Time required to accept a request before data input begins
// Time required to reject a request
// Percentage of accepted requests which were met (should be 100%)
// 
// Algorithm Data to be gathered:
// Percentage of requests accepted
// Percent utilization of Remote Host Machines
// Percent utilization of Virtual Machines
// Metric of evenness of load distribution

class Analytics {
    List<String> events;

    public Analytics() {
        System.out.println("Starting analytics");
        events = new ArrayList<String>();
    }
}
