package com.sam.pd1.account.filter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
	
	//number of requests
    @Value("${rate-limit.requests}")
    private int requests;
    
    //reset window
    @Value("${rate-limit.duration-minutes}")
    private int durationMinutes;
    
    //ConcurrentHashMap - for thread safe access
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
    	
    	// ✅ TOKEN BUCKET - refills tokens continuously, allows bursts
        // Example: 10 tokens, refills gradually every minute
        // Bandwidth limit = Bandwidth.builder()
        //         .capacity(requests)
        //         .refillGreedy(requests, Duration.ofMinutes(durationMinutes))
        //         .build();

        // ✅ LEAKY BUCKET - refills all tokens at once after duration, no bursts
        // Example: 10 tokens, all refilled after 1 minute
        // Bandwidth limit = Bandwidth.builder()
        //         .capacity(requests)
        //         .refillIntervally(requests, Duration.ofMinutes(durationMinutes))
        //         .build();

        // ✅ SLIDING WINDOW - smooth rate limiting, no boundary bursts
        // Example: 10 tokens, refills 1 token every 6 seconds (60s/10)
        // Bandwidth limit = Bandwidth.builder()
        //         .capacity(requests)
        //         .refillGreedy(1, Duration.ofSeconds(60 / requests))
        //         .build();

        // ✅ CURRENTLY ACTIVE - Token Bucket
    	
        Bandwidth limit = Bandwidth.builder()
                .capacity(requests)
                .refillGreedy(requests, Duration.ofMinutes(durationMinutes))
                .build();
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ipAddress = request.getRemoteAddr();
        //based on ip address - ratelimitter is implemented
       
        Bucket bucket = buckets.computeIfAbsent(ipAddress, k -> createNewBucket());
        
        //from one IP we cannot send more than n requests for the specific duration in minutes $durationMinutes (configured in application.yml file)
        if (bucket.tryConsume(1)) {
            response.addHeader("X-Rate-Limit-Remaining",
                    String.valueOf(bucket.getAvailableTokens()));
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(
                "{\"status\":429,\"error\":\"Too Many Requests\"," +
                "\"message\":\"Rate limit exceeded. Try again in "
                + durationMinutes + " minute(s).\"}"
            );
        }
    }
}