package com.example.restservice.inrostructure.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.regex.Pattern;

//@Component
//public class RateLimiterFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }

//    private final int maxRequestsPerTimePeriod = 50; // 50 requests per time period
//    private final int timePeriodInMs = 30000; // 30 seconds
//    private final int bandTimeInMs = 300000; // band for 5 minutes
//    private final String whiteListIPRegex = "^([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])$";
//    private IpStatistic ipStats;
//    private ServletContext servletContext;
//
//    @Override
//    public void init(FilterConfig fc) {
////        String maxReqs = fc.getInitParameter("maxRequestsPerTimePeriod");
////        String timePeriod = fc.getInitParameter("timePeriodInMs");
////        String bandTime = fc.getInitParameter("bandTimeInMs");
////        String whiteList = fc.getInitParameter("whiteListIPRegex");
//
////        if (maxReqs != null) {
////        maxRequestsPerTimePeriod = 100;
////        }
////        if (timePeriod != null) {
////        timePeriodInMs = 2000;
////        }
////        if (bandTime != null) {
////        bandTimeInMs = 100;
////        }
////        if (whiteList != null) {
////        whiteListIPRegex = "";
////        }
//
//        servletContext = fc.getServletContext();
//        servletContext.log("SimpleLimiterFilter Setup:");
//        servletContext.log(" maxRequestsPerTimePeriod: " + maxRequestsPerTimePeriod);
//        servletContext.log("           timePeriodInMs: " + timePeriodInMs);
//        servletContext.log("             bandTimeInMs: " + bandTimeInMs);
//        ipStats = new IpStatistic(maxRequestsPerTimePeriod, timePeriodInMs, bandTimeInMs);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
//            throws IOException, ServletException {
//        String ipAddress = request.getRemoteAddr();
//        Date d = new Date();
//        if ((ipStats.shouldRateLimit(ipAddress, d.getTime())) && (!inWhiteList(whiteListIPRegex, ipAddress))) {
//            HttpServletResponse servletResponse = (HttpServletResponse) response;
//            servletResponse.setStatus(406);
//            PrintWriter out = servletResponse.getWriter();
//            out.write("Rate Limit Exceeded");
//            servletContext.log("Blocked IP: " + ipAddress);
//        }
//        fc.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//        ipStats = null;
//        servletContext = null;
//    }
//
//    private boolean inWhiteList(String pattern, String ipAddress) {
//        return Pattern.matches(pattern, ipAddress);
//    }
//}