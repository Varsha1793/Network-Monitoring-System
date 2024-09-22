package com.verizon.netpulse.performance_backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verizon.netpulse.performance_backend.model.NetworkMetrics;
import com.verizon.netpulse.performance_backend.repository.NetworkMetricRepository;

@Service
public class NetworkMetricsService  {
    
    @Autowired
    private NetworkMetricRepository networkMetricRepository;

    public void measureAndSavingMetrics() throws IOException
    {
            // Measure latency
            // InetAddress inetAddress = InetAddress.getByName("google.com");
            // long startTimeLatency = System.currentTimeMillis();
            // boolean reachable = inetAddress.isReachable(5000);
            // long endTime = System.currentTimeMillis();
            // float latencyMs = reachable ? (endTime - startTimeLatency) : -1;

            URL url1 = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Set the timeout to 5 seconds
            connection.setReadTimeout(5000);

            long startTime = System.currentTimeMillis();
            connection.connect();
            int responseCode = connection.getResponseCode();
            long endTime = System.currentTimeMillis();
            float latencyMs =0;
            if (responseCode == 200) {
                latencyMs = endTime - startTime;
                System.out.println("Latency: " + latencyMs + " ms");
            } else {
                System.out.println("Failed to connect, response code: " + responseCode);
            }

            //Calculating Throughput
            double throughputMbps = 0.0;
            // Start time
            Instant startTimeBandwidth = Instant.now();

            // Download the file
            URL url2 = new URL("http://proof.ovh.net/files/100Mb.dat");
            InputStream inputStream = url2.openStream();
            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            int totalBytesRead = 0;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                totalBytesRead += bytesRead;
            }
            inputStream.close();

            // End time
            Instant endTimeBandwidth = Instant.now();

            // Calculate time taken in seconds
            double timeTakenSeconds = Duration.between(startTimeBandwidth, endTimeBandwidth).toMillis() / 1000.0;

            System.out.println("Total Bytes Read: " + totalBytesRead);
            System.out.println("Time Taken (seconds): " + timeTakenSeconds);
            // Calculate throughput in Mbps
            double dataSizeMegabits = (totalBytesRead * 8) / (1024.0 * 1024.0); // Convert bytes to Megabits
            throughputMbps = dataSizeMegabits / timeTakenSeconds;

            //Saving into the data base

            NetworkMetrics networkMetrics = new NetworkMetrics();
            networkMetrics.setLatency(latencyMs);
            networkMetrics.setThroughput((float) throughputMbps);
            networkMetrics.setUserId(101);
            networkMetricRepository.save(networkMetrics);
    }


    // public List<NetworkMetrics> getMetricsByUserId(Integer userId) {
    //     return networkMetricRepository.findByUserId(userId);
    // }

    public List<NetworkMetrics> getMetricsByUserId(Integer userId) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        return networkMetricRepository.findByUserIdAndCreatedAtAfter(userId, oneHourAgo);
    }
    
}
