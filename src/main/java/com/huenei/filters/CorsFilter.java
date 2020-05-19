package com.huenei.filters;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorsFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(CorsFilter.class);

  @Override
  public void destroy() {
    LOGGER.info("destroy filter. release our resources here if any");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "false");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, "
        + "X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, x-api-key");
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    LOGGER.info("Init filter");
  }

}