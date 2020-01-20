package com.boot.system.timer;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling  // 开启对定时任务的支持
public class TestTimer {

	@Scheduled(cron = "*/50 * * * * ?")
	public void show() {
		System.out.println("50s启动一次定时任务");
	}
}
