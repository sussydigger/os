import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class Scheduling {
  public void FCFS(List<Process> processes) {
    Queue<Process> priorityQueue = new PriorityQueue<>((a, b) -> a.arrivalTime - b.arrivalTime);
    priorityQueue.addAll(processes);

    int clock = 0;
    List<Process> firstComeFirstServerProcess = new ArrayList<>();
    while (!priorityQueue.isEmpty()) {
      firstComeFirstServerProcess.add(priorityQueue.poll());
    }

    for (int i = 0; i < firstComeFirstServerProcess.size(); i++) {
      if (clock < firstComeFirstServerProcess.get(i).arrivalTime) {
        clock += firstComeFirstServerProcess.get(i).arrivalTime;
      }
      clock += firstComeFirstServerProcess.get(i).burstTime;
      firstComeFirstServerProcess.get(i).completionTime = clock;
    }

    for (Process process : firstComeFirstServerProcess) {
      process.updateAfterCalculatingCompletionTime();
    }

    System.out.println("First Come First Serve Scheduling");
    System.out.printf("poc\tat\tbt\tct\ttat\twt\n");
    for (Process process : firstComeFirstServerProcess) {
      System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n", process.pid, process.arrivalTime, process.burstTime,
          process.completionTime, process.turnAroundTime, process.waitingTime);
    }

    int averageTurnAroundTime = 0;
    int averageWaitingTime = 0;
    for (Process process : firstComeFirstServerProcess) {
      averageWaitingTime += process.waitingTime;
      averageTurnAroundTime += process.turnAroundTime;
    }
    averageTurnAroundTime /= firstComeFirstServerProcess.size();
    averageWaitingTime /= firstComeFirstServerProcess.size();
    System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
    System.out.println("Average Waiting Time: " + averageWaitingTime);
  }

  public void RoundRobin(List<Process> processes, int timeQuantum) {
    Queue<Process> priorityQueue = new PriorityQueue<>((a, b) -> a.arrivalTime - b.arrivalTime);
    priorityQueue.addAll(processes);

    int clock = 0;
    List<Process> roundRobinProcess = new ArrayList<>();
    while (!priorityQueue.isEmpty()) {
      roundRobinProcess.add(priorityQueue.poll());
    }

    clock += roundRobinProcess.get(0).arrivalTime;
    while (!roundRobinProcess.isEmpty()) {
      Process process = roundRobinProcess.remove(0);
      if (process.clockCycleRemaining > timeQuantum) {
        clock += timeQuantum;
        process.clockCycleRemaining -= timeQuantum;
        roundRobinProcess.add(process);
      } else {
        clock += process.clockCycleRemaining;
        process.completionTime = clock;
        process.clockCycleRemaining = 0;
      }
    }

    for (Process process : processes) {
      process.updateAfterCalculatingCompletionTime();
    }

    System.out.println("Round Robin Scheduling");
    System.out.printf("poc\tat\tbt\tct\ttat\twt\n");
    for (Process process : processes) {
      System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n", process.pid, process.arrivalTime, process.burstTime,
          process.completionTime, process.turnAroundTime, process.waitingTime);
    }

    int averageTurnAroundTime = 0;
    int averageWaitingTime = 0;
    for (Process process : processes) {
      averageWaitingTime += process.waitingTime;
      averageTurnAroundTime += process.turnAroundTime;
    }
    averageTurnAroundTime /= processes.size();
    averageWaitingTime /= processes.size();
    System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
    System.out.println("Average Waiting Time: " + averageWaitingTime);
  }

  public void SJFNonPreemptive(List<Process> processes) {
    Queue<Process> priorityQueue = new PriorityQueue<>((a, b) -> a.arrivalTime - b.arrivalTime);
    priorityQueue.addAll(processes);

    int clock = 0;
    List<Process> sjfNonPreemptiveProcess = new ArrayList<>();
    while (!priorityQueue.isEmpty()) {
        sjfNonPreemptiveProcess.add(priorityQueue.poll());
    }

    List<Process> completedProcesses = new ArrayList<>();
    while (completedProcesses.size() < sjfNonPreemptiveProcess.size()) {
        Process nextProcess = null;

        for (Process process : sjfNonPreemptiveProcess) {
            if (!completedProcesses.contains(process) && process.arrivalTime <= clock) {
                if (nextProcess == null || process.burstTime < nextProcess.burstTime) {
                    nextProcess = process;
                }
            }
        }

        if (nextProcess == null) {
            clock++;
            continue;
        }

        clock = Math.max(clock, nextProcess.arrivalTime) + nextProcess.burstTime;
        nextProcess.completionTime = clock;
        completedProcesses.add(nextProcess);
    }

    for (Process process : processes) {
        process.updateAfterCalculatingCompletionTime();
    }

    System.out.println("Shortest Job First Non-Preemptive Scheduling");
    System.out.printf("poc\tat\tbt\tct\ttat\twt\n");
    for (Process process : processes) {
        System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n", process.pid, process.arrivalTime, process.burstTime,
                process.completionTime, process.turnAroundTime, process.waitingTime);
    }

    int averageTurnAroundTime = 0;
    int averageWaitingTime = 0;
    for (Process process : processes) {
        averageWaitingTime += process.waitingTime;
        averageTurnAroundTime += process.turnAroundTime;
    }
    averageTurnAroundTime /= processes.size();
    averageWaitingTime /= processes.size();
    System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
    System.out.println("Average Waiting Time: " + averageWaitingTime);
  }
}
