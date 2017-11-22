package com.my.user.ws;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.my.common.utils.RequestUtils;

/**
 * 模拟linux tail -f前向滚动日志。不支持后向滚动日志。
 * @author gche
 */
@Component("websocket")
public class MyWebSocketHandler extends TextWebSocketHandler {
	// key is sessionId
	private ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();
	// key is file name, value are sessionId set
	private ConcurrentHashMap<String, HashSet<String>> FILE_LISTENERS = new ConcurrentHashMap<String, HashSet<String>>();
	private Map<String, Long> posMap = new HashMap<String, Long>();

	private String path;

	private ExecutorService executor = new ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(100));

	public void setPath(String path) {
		this.path = path;
	}

	
	/**
	 * {@inheritDoc}
	 * 不支持前端发消息给服务端（不支持后向查询日志）。
	 * 如果要支持后向查询日志，建议不要走socket，直接请求。否则容易和前后滚动日志出现混淆
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//System.out.println("receive message from client(not supported):" + message);
		//session.sendMessage(new TextMessage("欢迎：" + message.getPayload()));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Map<String, String> paramMap = this.getParamMap(session);
		String fileName = paramMap.get("file");
		if (fileName == null || "".equals(fileName)) {
			session.sendMessage(
					new TextMessage("file name is required,eg:http://host:port/my-user/ws.do?file=xxx.log"));
			session.close(CloseStatus.BAD_DATA);
			return;
		}
		if (FILE_LISTENERS.get(fileName) == null) {
			FILE_LISTENERS.put(fileName, new HashSet<String>());
		}
		FILE_LISTENERS.get(fileName).add(session.getId());
		CLIENTS.put(session.getId(), session);
		posMap.put(session.getId(), 0L);
		//
		session.sendMessage(new TextMessage("欢迎：<br/>"));
		//send latest MAX_SIZE bytes when session first request
		executor.submit(new Task(fileName));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		CLIENTS.remove(session.getId());
		String fileName = this.getParamMap(session).get("file");
		Set<String> listeners = FILE_LISTENERS.get(fileName);
		if (listeners != null && listeners.contains(session.getId())) {
			listeners.remove(session.getId());
			if (listeners.isEmpty()) {
				FILE_LISTENERS.remove(fileName);
			}
		}
		posMap.remove(session.getId());
	}

	private Map<String, String> getParamMap(WebSocketSession session) {
		URI uri = session.getUri();
		String queryString = uri.getQuery();
		return RequestUtils.parseQueryString(queryString);
	}

	public void sumitTask(String name) {
		this.executor.submit(new Task(name));
	}

	class Task implements Runnable {
		private String name;

		public Task(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			Set<String> listeners = FILE_LISTENERS.get(name);
			if (listeners != null && !listeners.isEmpty()) {
				for (String id : listeners) {
					WebSocketSession session = CLIENTS.get(id);
					if (session != null && session.isOpen()) {
						try {
							Map<String, Object> contentMap = FileUtils.read(path + File.separator + name,
									posMap.get(session.getId()));
							if(contentMap.isEmpty()) {
								continue;
							}
							posMap.put(session.getId(), (Long) contentMap.get("pos"));
							session.sendMessage(new TextMessage(contentMap.get("content").toString()));
						} catch (IOException e) {
							e.printStackTrace();
							// ignore here
						}
					}
				}
			}
		}
	}
}
