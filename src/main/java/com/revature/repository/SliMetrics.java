package com.revature.repository;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.javalin.http.Context;

public class SliMetrics {
    public static void getSLI(Context ctx) throws FileNotFoundException, IOException{
        //Runs bash script
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File ("C:/Users/Michael/Desktop/P0-Template-Michael-main/logs"));
        pb.command("cmd.exe", "/c", "C:/Users/Michael/Desktop/P0-Template-Michael-main/logs/sli-test.sh");
        Process process = pb.start();
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String s = null;
        while ((s=input.readLine()) != null) {
            System.out.println(s);
        }
        //reads Latency.log created by script
        File latencyFile = new File("C:/Users/Michael/Desktop/P0-Template-Michael-main/logs/Latency.log");
        Scanner sc = new Scanner(latencyFile);
        double totalLatency = 0;
        while (sc.hasNext()){
            double x = Double.parseDouble(sc.nextLine());//x is just a holder variable for each new line read
            totalLatency += x;  
        }
        //reads httpRequest.log created by script
        File requestFile = new File("C:/Users/Michael/Desktop/P0-Template-Michael-main/logs/httpRequest.log");
        Scanner scan = new Scanner(requestFile);
        int totalRequest = 0;
        int requestFailure = 0;
        while (scan.hasNext()){
            if (scan.nextLine() == "500") {
                requestFailure++;
            }else{
                totalRequest++;
            }    
        }
        scan.close();
        sc.close();
        //calculates avg. latency
        double avgLatency = (double) Math.round((totalLatency*100.0/totalRequest))/100;
        //calculates success rate of http requests
        double httpSuccess = totalRequest - requestFailure;
    ctx.result("HTTP request success rate is: " + ((httpSuccess / totalRequest)*100) + "%, and the average latency for each request is: " + avgLatency + "ms.");
    } 
}


