
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

// Implements Filter class
public class ImageFilter implements Filter {
	public void init(FilterConfig config) throws ServletException {
		// Get init parameter
		String testParam = config.getInitParameter("deployment_type");

		// Print the init parameter
		System.out.println("Test Param: " + testParam);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws java.io.IOException, ServletException {

		HtmlResponseWrapper capturingResponseWrapper = new HtmlResponseWrapper(
                (HttpServletResponse) response);

        filterChain.doFilter(request, capturingResponseWrapper);

        

            String content = capturingResponseWrapper.getCaptureAsString();

            // replace stuff here
            String replacedContent = content.replaceAll(
                    "/content/media_upload\\?getfile=",
                    "http://istar-static.s3.amazonaws.com/");

           System.out.println(replacedContent);
            
            response.getWriter().write(replacedContent);
        

	}

	public void destroy() {
		/*
		 * Called before the Filter instance is removed from service by the web
		 * container
		 */
	}
}