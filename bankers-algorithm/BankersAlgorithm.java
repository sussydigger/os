class BankersAlgorithm {
  private int numProcesses;
  private int numResources;
  private int[] available;
  private int[][] maximum;
  private int[][] allocation;
  private int[][] need;
  private boolean[] finished;
  private String[] processes;

  public BankersAlgorithm(String[] processes, int[] available, int[][] maximum, int[][] allocation) {
    this.processes = processes;
    this.numProcesses = processes.length;
    this.numResources = available.length;
    this.available = available.clone();
    this.maximum = deepCopy(maximum);
    this.allocation = deepCopy(allocation);

    // Calculate need matrix
    this.need = new int[numProcesses][numResources];
    for (int i = 0; i < numProcesses; i++) {
      for (int j = 0; j < numResources; j++) {
        need[i][j] = maximum[i][j] - allocation[i][j];
      }
    }
    this.finished = new boolean[numProcesses];
  }

  public boolean isSafe() {
    int[] work = available.clone();
    boolean[] finish = new boolean[numProcesses];
    int count = 0;
    int[] safeSequence = new int[numProcesses];

    while (count < numProcesses) {
      boolean found = false;
      for (int i = 0; i < numProcesses; i++) {
        if (!finish[i]) {
          int j;
          for (j = 0; j < numResources; j++) {
            if (need[i][j] > work[j]) {
              break;
            }
          }
          if (j == numResources) {
            for (int k = 0; k < numResources; k++) {
              work[k] += allocation[i][k];
            }
            safeSequence[count++] = i;
            finish[i] = true;
            found = true;
          }
        }
      }
      if (!found) {
        break;
      }
    }

    if (count < numProcesses) {
      return false;
    }

    return true;
  }

  public boolean requestResources(int processIndex, int[] request) {
    for (int i = 0; i < numResources; i++) {
      if (request[i] > need[processIndex][i] || request[i] > available[i]) {
        return false;
      }
    }

    for (int i = 0; i < numResources; i++) {
      available[i] -= request[i];
      allocation[processIndex][i] += request[i];
      need[processIndex][i] -= request[i];
    }

    if (!isSafe()) {
      for (int i = 0; i < numResources; i++) {
        available[i] += request[i];
        allocation[processIndex][i] -= request[i];
        need[processIndex][i] += request[i];
      }
      return false;
    }

    return true;
  }

  public boolean releaseResources(int processIndex, int[] release) {
    for (int i = 0; i < numResources; i++) {
      if (release[i] > allocation[processIndex][i]) {
        return false;
      }
    }

    for (int i = 0; i < numResources; i++) {
      available[i] += release[i];
      allocation[processIndex][i] -= release[i];
      need[processIndex][i] += release[i];
    }

    return true;
  }

  private int[][] deepCopy(int[][] matrix) {
    int[][] copy = new int[matrix.length][];
    for (int i = 0; i < matrix.length; i++) {
      copy[i] = matrix[i].clone();
    }
    return copy;
  }

  public void printState() {
    System.out.println("Processes:");
    for (int i = 0; i < numProcesses; i++) {
      System.out.print(processes[i] + "\t");
    }
    System.out.println();

    System.out.println("Available:");
    for (int i = 0; i < numResources; i++) {
      System.out.print(available[i] + "\t");
    }
    System.out.println();

    System.out.println("Maximum:");
    for (int i = 0; i < numProcesses; i++) {
      for (int j = 0; j < numResources; j++) {
        System.out.print(maximum[i][j] + "\t");
      }
      System.out.println();
    }

    System.out.println("Allocation:");
    for (int i = 0; i < numProcesses; i++) {
      for (int j = 0; j < numResources; j++) {
        System.out.print(allocation[i][j] + "\t");
      }
      System.out.println();
    }

    System.out.println("Need:");
    for (int i = 0; i < numProcesses; i++) {
      for (int j = 0; j < numResources; j++) {
        System.out.print(need[i][j] + "\t");
      }
      System.out.println();
    }
  }
}

class Main {
  public static void main(String[] args) {
    String[] processes = { "P0", "P1", "P2", "P3", "P4" };
    int[] available = { 3, 3, 2 };
    int[][] maximum = {
        { 7, 5, 3 },
        { 3, 2, 2 },
        { 9, 0, 2 },
        { 2, 2, 2 },
        { 4, 3, 3 }
    };
    int[][] allocation = {
        { 0, 1, 0 },
        { 2, 0, 0 },
        { 3, 0, 2 },
        { 2, 1, 1 },
        { 0, 0, 2 }
    };

    BankersAlgorithm ba = new BankersAlgorithm(processes, available, maximum, allocation);
    ba.printState();
    System.out.println(ba.isSafe());
    System.out.println(ba.requestResources(1, new int[] { 1, 0, 2 }));
    ba.printState();
    System.out.println(ba.isSafe());
    System.out.println(ba.releaseResources(1, new int[] { 1, 0, 2 }));
    ba.printState();
    System.out.println(ba.isSafe());
  }
}
