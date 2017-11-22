package com.my.user.ws;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.SmartLifecycle;

public class FileScanTimer implements SmartLifecycle, InitializingBean {

	private ExecutorService executor = Executors.newFixedThreadPool(1);

	public FileScanTimer(String path) {
		this.path = path;
	}

	@Autowired
	@Qualifier("websocket")
	private MyWebSocketHandler socketHandler;

	private boolean running = false;
	private String path;
	private volatile boolean shouldRun = true;

	@Override
	public void start() {
		executor.submit(new Runnable() {
			@Override
			public void run() {
				Path p = Paths.get(path);
				try {
					WatchService watcher = p.getFileSystem().newWatchService();
					p.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
					WatchKey key;
					while (shouldRun) {
						try {
							key = watcher.take();
							List<WatchEvent<?>> events = key.pollEvents();
							for (WatchEvent<?> event : events) {
								if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
									socketHandler.sumitTask(event.context().toString());
								}
							}
							key.reset();
						} catch (InterruptedException e) {
							// ignore interruption, go on
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		this.running = true;
	}

	@Override
	public void stop() {
		running = false;
		executor.shutdown();
		shouldRun = false;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public void stop(Runnable callback) {
		this.stop();
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.socketHandler.setPath(this.path);
	}

}