import java.util.ArrayList;
import java.util.List;

class Main {
  public static void main(String[] args) {
    Process process1 = new Process(0,1,  2, -1, -1, -1);
    Process process2 = new Process(1,3,  3, -1, -1, -1);
    Process process3 = new Process(2,4,  4, -1, -1, -1);
    Process process4 = new Process(3,6,  5, -1, -1, -1);
    Process process5 = new Process(4,9,  4, -1, -1, -1);

    Scheduling scheduling = new Scheduling();
    List<Process> processes = new ArrayList<>();
    processes.add(process1);
    processes.add(process2);
    processes.add(process3);
    processes.add(process4);
    processes.add(process5);

    scheduling.FCFS(processes);
    System.out.println();
    scheduling.RoundRobin(processes, 2);
    System.out.println();
    scheduling.SJFNonPreemptive(processes);
  }
}
