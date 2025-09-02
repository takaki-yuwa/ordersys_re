package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/jsp/*", "/servlet/*" })
public class SessionFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest req = (HttpServletRequest) request;

			HttpSession session = req.getSession(false);
			String OrderStartURI = req.getContextPath() + "/OrderStart";

			boolean SessionIn = (session != null && session.getAttribute("sessionNumber") != null);
			boolean StertRequest = req.getRequestURI().equals(OrderStartURI) || req.getRequestURI().endsWith("/OrderSystem");
			boolean isStaticResource = req.getRequestURI().matches(".*(\\.css|\\.js|\\.png|\\.jpg|\\.gif)$");

			if (SessionIn || StertRequest || isStaticResource) {
				chain.doFilter(request, response); // 通過
			} else {
				request.getRequestDispatcher("/ExceptionError.jsp").forward(request, response);
			}
		} catch (Exception e) {
			System.err.println("SessionFilterで予期しない例外が発生しました");
			e.printStackTrace();

			// エラーページにリダイレクトするなどの対応（必要に応じて）
			if (response instanceof HttpServletResponse) {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"内部エラーが発生しました");
			}
		}
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}