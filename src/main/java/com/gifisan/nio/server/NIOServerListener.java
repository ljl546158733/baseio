package com.gifisan.nio.server;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.gifisan.nio.AbstractLifeCycleListener;
import com.gifisan.nio.LifeCycle;
import com.gifisan.nio.LifeCycleListener;
import com.gifisan.nio.common.Logger;
import com.gifisan.nio.common.LoggerFactory;
import com.gifisan.nio.server.configuration.ServerConfiguration;

public class NIOServerListener extends AbstractLifeCycleListener implements LifeCycleListener {

	private Logger		logger		= LoggerFactory.getLogger(NIOServerListener.class);
	private long		staredTime	= 0;

	public int lifeCycleListenerSortIndex() {
		return 999;
	}

	public void lifeCycleStarting(LifeCycle lifeCycle) {
		staredTime = System.currentTimeMillis();
	}

	public void lifeCycleStarted(LifeCycle lifeCycle) {
		NIOAcceptor acceptor = (NIOAcceptor) lifeCycle;
		NIOContext context = acceptor.getContext();
		ServerConfiguration configuration = context.getServerConfiguration();
		logger.info("   [NIOServer] 服务启动完成  @127.0.0.1:" + configuration.getSERVER_PORT() + " 花费 "
				+ (System.currentTimeMillis() - staredTime) + " 毫秒");
	}

	public void lifeCycleFailure(LifeCycle lifeCycle, Exception exception) {
		// NIOConnector connector = (NIOConnector) lifeCycle;
		logger.error(exception.getMessage(), exception);
		;
	}

	public void lifeCycleStopped(LifeCycle lifeCycle) {
		logger.info("   [NIOServer] 服务停止成功");
	}

	public void lifeCycleStopping(LifeCycle lifeCycle) {
		NIOAcceptor acceptor = (NIOAcceptor) lifeCycle;
		NIOContext context = acceptor.getContext();
		
		if (context == null) {
			logger.info("   [NIOServer] 服务启动失败，正在停止...");
			return;
		}
		
		if (context.getTCPIOService() == null) {
			logger.info("   [NIOServer] 服务启动失败，正在停止...");
			return;
		}
		
		ServerConfiguration configuration = context.getServerConfiguration();
		
		if (configuration.isSERVER_UDP_BOOT() && context.getUDPIOService() == null) {
			logger.info("   [NIOServer] 服务启动失败，正在停止...");
			return;
		}
		
		BigDecimal time = new BigDecimal(System.currentTimeMillis() - staredTime);
		BigDecimal anHour = new BigDecimal(60 * 60 * 1000);
		BigDecimal hour = time.divide(anHour, 3, RoundingMode.HALF_UP);
		String[] params = { String.valueOf(configuration.getSERVER_PORT()), String.valueOf(hour) };
		logger.info("   [NIOServer] 服务运行时间  @127.0.0.1:{} 共 {} 小时", params);
		logger.info("   [NIOServer] 开始停止服务，请稍等");
	}

}
