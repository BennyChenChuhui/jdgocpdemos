package infinispan;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

@SuppressWarnings("serial")
@WebServlet(value = "/cache")
public class CacheServlet extends HttpServlet {


	private RemoteCacheManager cacheManager;
	private RemoteCache<String, Object> cache;

	@Override
	public void init() {

		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServer().host(System.getenv("DATAGRID_APP_HOTROD_SERVICE_HOST"))
				.port(Integer.parseInt(System.getenv("DATAGRID_APP_HOTROD_SERVICE_PORT")));
		cacheManager = new RemoteCacheManager(builder.build());
		cache = cacheManager.getCache("default");

		System.out.println("Loaded Cache " + cache);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) {
		// Here the request is put in asynchronous mode
		res.setContentType("text/html");

		String cacheAction = req.getParameter("cacheAction");
		String cacheKey = req.getParameter("cacheKey");
		String cacheValue = req.getParameter("cacheValue");

		// Actual logic goes here.
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String output = "Action: " + cacheAction + "<br>";

			if ("addRecord".equals(cacheAction)) {
			    addRecord(cacheKey, cacheValue);
			    output += "cacheKey: " + cacheKey + "<br>";
			    output += "cacheValue: " + cacheValue + "<br>";
			}
			else if ("updateRecord".equals(cacheAction)) {
			    String oldCacheValue = (String) findRecord(cacheKey);
			    updateRecord(cacheKey, cacheValue);
			    output += "cacheKey: " + cacheKey + "<br>";
			    output += "Old cacheValue: " + oldCacheValue + "<br>";
			    output += "New cacheValue: " + cacheValue + "<br>";
			}
			else if ("removeRecord".equals(cacheAction)) {
			    cacheValue = (String) findRecord(cacheKey);
			    removeRecord(cacheKey);
			    output += "cacheKey: " + cacheKey + "<br>";
			    output += "cacheValue: " + cacheValue + "<br>";
			}
			else if ("findRecord".equals(cacheAction)) {
			    cacheValue = (String) findRecord(cacheKey);
			    output += "cacheKey: " + cacheKey + "<br>";
			    output += "cacheValue: " + cacheValue + "<br>";
			}
			else if ("listRecords".equals(cacheAction)) {
			    output += listRecords();
			}
			
			output += "<a href='index.jsp'>Back To Index Page</a><br>";

			out.println(output);
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected void addRecord(String key, String value) {
		cache.put(key, value);
	}
	
	protected void updateRecord(String key, String value) {
		cache.put(key, value);
	}
	
	protected void removeRecord(String key) {
		cache.remove(key);
	}
	
	protected Object findRecord(String key) {
		return cache.get(key);
	}
	
	protected String listRecords() {
		String records = "Cache Size: " + cache.size() + "<br>";
		Set<String> keySet = cache.keySet();
		if (keySet != null) {
			for (String key : keySet) {
				records += key + "=" + cache.get(key).toString() + "<br>";
			}
		}
		return records;
	}
}
