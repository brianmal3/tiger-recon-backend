package com.recon.backend.filters;


import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recon.backend.models.CustomResponse;
import com.recon.backend.services.UserService;
import com.recon.backend.utils.E;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
@Profile("prod")
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String xx = E.COFFEE + E.COFFEE;
    String mm = " :: " + E.AMP + E.AMP + E.AMP + E.AMP;

    final String tag = "\uD83D\uDD11\uD83D\uDD11 AuthenticationFilter ";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    @Value("${spring.profiles.active}")
    private String profile;
    final UserService userService;

    public AuthenticationFilter(UserService userService) {
        this.userService = userService;
        LOGGER.info(xx + " AuthenticationFilter constructed");
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest,
                                    @NotNull HttpServletResponse httpServletResponse,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String url = httpServletRequest.getRequestURL().toString();
        LOGGER.info(tag + " authenticating \uD83C\uDF4E  {}", url);
        if (profile.equalsIgnoreCase("test")) {
            doFilter(httpServletRequest, httpServletResponse, filterChain);
            return;
        }

        if (url.contains("uploadFile")) {   //this is my local machine
            LOGGER.info(E.ANGRY + E.ANGRY + "this request is not subject to authentication: " + E.HAND2 + "{}", url);
            doFilter(httpServletRequest, httpServletResponse, filterChain);
            return;
        }
        //allow getCountries
        if (httpServletRequest.getRequestURI().contains("getCountries")
                || httpServletRequest.getRequestURI().contains("addCountry")) {
            LOGGER.info("{} contextPath: {}" + E.AMP + " requestURI: {}", mm, httpServletRequest.getContextPath(), httpServletRequest.getRequestURI());
            LOGGER.info("{} allowing addCountry and getCountries without authentication, is this OK?", mm);

            doFilter(httpServletRequest, httpServletResponse, filterChain);
            return;
        }
        //allow registerAssociation, downloadExampleUsersFile, downloadExampleVehiclesFile
        if (httpServletRequest.getRequestURI().contains("downloadExampleUsersFile")
                || httpServletRequest.getRequestURI().contains("registerAssociation")
                || httpServletRequest.getRequestURI().contains("downloadExampleVehiclesFile")) {
            LOGGER.info("{} contextPath: {}" + E.AMP + " requestURI: {}", mm, httpServletRequest.getContextPath(), httpServletRequest.getRequestURI());
            LOGGER.info("{} allowing downloadExampleVehiclesFile and downloadExampleUsersFile without authentication, is this OK?", mm);

            doFilter(httpServletRequest, httpServletResponse, filterChain);
            return;
        }
        //allow api-docs
        if (httpServletRequest.getRequestURI().contains("api-docs")) {
            LOGGER.info("{} contextPath: {}" + E.AMP + " requestURI: {}\n\n", mm, httpServletRequest.getContextPath(), httpServletRequest.getRequestURI());
            LOGGER.info("{} allowing swagger openapi call", mm);

            doFilter(httpServletRequest, httpServletResponse, filterChain);
            return;
        }
        //allow localhost
        if (url.contains("localhost") || url.contains("192.168.86.242")) {
            LOGGER.info("{} contextPath: {}" + E.AMP + " requestURI: {}\n\n", mm, httpServletRequest.getContextPath(), httpServletRequest.getRequestURI());
            LOGGER.info("{} no ticket, but allowing the devs in! {}", mm, url);

            doFilter(httpServletRequest, httpServletResponse, filterChain);
            return;
        }

        String m = httpServletRequest.getHeader("Authorization");
        if (m == null) {
            sendError(httpServletResponse, "Authentication token missing");
            return;
        }
        String token = m.substring(7);
        try {
            ApiFuture<FirebaseToken> future = FirebaseAuth.getInstance().verifyIdTokenAsync(token, true);
            FirebaseToken mToken = future.get();

            if (mToken != null) {
                String userId = mToken.getUid();
                var user = userService.findUserByFirebaseUid(userId);
                if (user != null) {
                    LOGGER.info("\uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21  user unknown, possible new registration");
                    doFilter(httpServletRequest, httpServletResponse, filterChain);
                } else {
                    sendError(httpServletResponse, reds +"request has been forbidden, user invalid");
                }

            } else {
                LOGGER.info("\uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 request has been forbidden, token invalid");
                sendError(httpServletResponse, reds+"request has been forbidden, token invalid");
            }

        } catch (Exception e) {
            String msg = "an Exception happened: \uD83C\uDF4E " + e.getMessage();
            LOGGER.info(E.RED_DOT + E.RED_DOT + E.RED_DOT + E.RED_DOT + E.RED_DOT + E.RED_DOT + E.RED_DOT
                    + " {}\n failed url: {}" + E.RED_DOT, msg, httpServletRequest.getRequestURL().toString());
            sendError(httpServletResponse, e.getMessage());
        }

    }

    private static void sendError(HttpServletResponse httpServletResponse, String message) throws IOException {
        var resp = new CustomResponse();
        resp.setStatus(403);
        resp.setMessage(message);

        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().write(G.toJson(resp));
    }

    private void doFilter(@NotNull HttpServletRequest httpServletRequest,
                          @NotNull HttpServletResponse httpServletResponse,
                          FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(httpServletRequest, httpServletResponse);


        LOGGER.info("\uD83D\uDD37\uD83D\uDD37\uD83D\uDD37\uD83D\uDD37{} \uD83D\uDD37 Status Code: {}  \uD83D\uDD37 \uD83D\uDD37 \uD83D\uDD37 ", httpServletRequest.getRequestURI(), httpServletResponse.getStatus());

    }

    static final String reds = E.RED_DOT + E.RED_DOT + E.RED_DOT + E.RED_DOT + E.RED_DOT + " Bad Response Status:";

    private void print(@NotNull HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURL().toString();
        LOGGER.info(E.ANGRY + E.ANGRY + E.ANGRY + E.BELL + "Authenticating this url: " + E.BELL + " " + url);

        System.out.println("\uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 request header parameters ...");
        Enumeration<String> parms = httpServletRequest.getParameterNames();
        while (parms.hasMoreElements()) {
            String m = parms.nextElement();
            LOGGER.info("\uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 parameterName: " + m);

        }
        LOGGER.info("\uD83D\uDE21 \uD83D\uDE21 headers ...");
        Enumeration<String> names = httpServletRequest.getHeaderNames();
        while (names.hasMoreElements()) {
            String m = names.nextElement();
            LOGGER.info("\uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 headerName: " + m);
        }
        LOGGER.info("\uD83D\uDC9A \uD83D\uDC9A \uD83D\uDC9A Authorization: "
                + httpServletRequest.getHeader("Authorization") + " \uD83D\uDC9A \uD83D\uDC9A");
    }

}

