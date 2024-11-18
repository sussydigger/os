class Process {
  public int pid;
  public int arrivalTime;
  public int burstTime;
  public int completionTime;
  public int turnAroundTime;
  public int waitingTime;
  public int clockCycleRemaining;

  public Process(int pid, int arrivalTime, int burstTime, int completionTime, int turnAroundTime,
      int waitingTime) {
    this.pid = pid;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.completionTime = completionTime;
    this.turnAroundTime = turnAroundTime;
    this.waitingTime = waitingTime;
    this.clockCycleRemaining = burstTime;
  }

  public void updateAfterCalculatingCompletionTime() {
    turnAroundTime = completionTime - arrivalTime;
    waitingTime = turnAroundTime - burstTime;
  }
}
