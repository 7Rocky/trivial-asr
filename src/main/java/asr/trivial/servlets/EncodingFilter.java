package asr.trivial.servlets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter(urlPatterns="/*")
public class EncodingFilter implements Filter {

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    chain.doFilter(request, response);
  }

  public void init(FilterConfig filterConfig) throws ServletException { }

  public void destroy() { }

}
