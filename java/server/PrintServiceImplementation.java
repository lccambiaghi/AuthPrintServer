package server;

import common.PrintService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

class PrintServiceImplementation extends UnicastRemoteObject implements PrintService {

    private boolean running;
    private PrintThread printThread;
    private int jobNumber;
    private LinkedList<Job> jobQueue;
    private Map<String, String> parameters;

    PrintServiceImplementation() throws RemoteException {
        super();
        parameters = new HashMap<>();
        parameters.put("Param1", "Default");
        parameters.put("Param2", "Default");
        parameters.put("Param3", "Default");
        jobQueue = new LinkedList<>();
    }

    public void print(String filename, String printer) throws RemoteException, InterruptedException {
        if (running) {
            jobNumber++;
            jobQueue.addLast(new Job(jobNumber, filename));
        } else
            System.out.println("The server is not running");
    }

    public String queue() throws RemoteException {
        if (running) {
            String queue = "";
            for (Job job : jobQueue) {
                queue = queue.concat("server.Job number " + job.getJobNumber() + ": " + job.getFilename() + "\n");
            }
            if (queue.length() > 0)
                return queue;
            else
                return "The queue is empty";
        } else
            return "The server is not running";
    }

    public void topQueue(int jobNumber) throws RemoteException {
        if (running) {
            for (Iterator<Job> jobIterator = jobQueue.iterator(); jobIterator.hasNext(); )
                if (jobIterator.next().getJobNumber() == jobNumber) {
                    jobQueue.addFirst((Job) jobIterator);
                    jobIterator.remove();
                    System.out.println("server.Job number " + jobNumber + " moved to the top of the queue.");
                    break;
                }
        } else
            System.out.println("The server is not running");

    }

    public void start() throws RemoteException {
        if (!running) {
            printThread = new PrintThread();
            printThread.start();
            running = true;
            System.out.println("Print server has started");
        } else
            System.out.println("Print server is either running or in standby");
    }

    public void stop() throws RemoteException, InterruptedException {
        if (running) {
            printThread.interrupt();
            running = false;
            System.out.println("Print server has stopped");
        } else
            System.out.println("Print server is not running");
    }

    public void restart() throws RemoteException, InterruptedException {
        if (running) {
            printThread.interrupt();
            jobQueue = new LinkedList<>();
            jobNumber = 0;
            printThread = new PrintThread();
            printThread.start();
        } else
            System.out.println("Print server is not running");
    }

    public String status() throws RemoteException {
        if (running)
            return "The print server is running";
        else
            return "The print server is not running";
    }

    public String readConfig(String parameter) throws RemoteException {
        if (parameters.containsKey(parameter))
            return parameters.get(parameter);
        else
            return "Parameter name not found";
    }

    public void setConfig(String parameter, String value) throws RemoteException {
        if (parameters.containsKey(parameter))
            parameters.put(parameter, value);
        else
            System.out.println("Parameter name not found");
    }

    private class PrintThread extends Thread {
        public void run() {
            while (true) {
                if (Thread.interrupted()) {
                    break;
                } else {
                    if (!jobQueue.isEmpty())
                        for (Iterator<Job> jobIterator = jobQueue.iterator(); jobIterator.hasNext(); ) {
                            Job job = jobIterator.next();
                            System.out.println("Job number " + job.getJobNumber() + ": printing " + job.getFilename());
                            try {
                                Thread.sleep(3000); // 3 seconds
                                jobIterator.remove();
                                System.out.println("Job number" + job.getJobNumber() + " finished");
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt(); //if it gets interrupted while printing
                            }
                        }
                }
            }
        }
    }
}

class Job {
    private int jobNumber;
    private String filename;

    Job(int jobNumber, String filename) {
        this.jobNumber = jobNumber;
        this.filename = filename;
    }

    int getJobNumber() {
        return jobNumber;
    }

    String getFilename() {
        return filename;
    }
}
