package com.spotifyinfo.interceptors;

import com.spotifyinfo.utils.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(LoggingFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);

        logRequest(contentCachingRequestWrapper, request);
        logResponse(contentCachingResponseWrapper, contentCachingRequestWrapper);

        contentCachingResponseWrapper.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper contentCachingRequestWrapper, HttpServletRequest request) {
        String requestBody = StringUtil.getStringValue(contentCachingRequestWrapper.getContentAsByteArray(), request.getCharacterEncoding());

        StringBuilder requestStringBuilder = new StringBuilder();
        requestStringBuilder.append("[REQUEST] ");
        requestStringBuilder.append(contentCachingRequestWrapper.getMethod()).append(" ");
        requestStringBuilder.append(contentCachingRequestWrapper.getRequestURL());

        if (StringUtils.isNotBlank(contentCachingRequestWrapper.getQueryString())) {
            requestStringBuilder.append("/").append(contentCachingRequestWrapper.getQueryString());
        }

        if (StringUtils.isNotBlank(requestBody)) {
            requestStringBuilder.append(" -> ").append(requestBody);
        }

        logger.info(requestStringBuilder.toString());
    }

    private void logResponse(ContentCachingResponseWrapper contentCachingResponseWrapper, ContentCachingRequestWrapper requestWrapper) {
        StringBuilder responseStringBuilder = new StringBuilder();
        responseStringBuilder
                .append("[RESPONSE] ")
                .append(requestWrapper.getMethod()).append(" ")
                .append(requestWrapper.getRequestURL());

        String responseBody = StringUtil.getStringValue(contentCachingResponseWrapper.getContentAsByteArray(), contentCachingResponseWrapper.getCharacterEncoding());

        if (StringUtils.isNotBlank(responseBody)) {
            responseStringBuilder
                    .append(" - > ")
                    .append(responseBody);
        }

        logger.info(responseStringBuilder.toString());
    }
}
