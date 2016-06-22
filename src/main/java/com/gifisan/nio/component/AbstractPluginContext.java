package com.gifisan.nio.component;

import java.util.concurrent.atomic.AtomicInteger;

import com.gifisan.nio.server.NIOContext;

public abstract class AbstractPluginContext extends InitializeableImpl implements PluginContext {

	private int				pluginIndex	= 0;
	private static AtomicInteger	_index		= new AtomicInteger();

	protected AbstractPluginContext() {
		this.pluginIndex = _index.getAndIncrement();
	}

	public int getPluginIndex() {
		return pluginIndex;
	}

	// FIXME you wen ti
	public void prepare(NIOContext context, Configuration config) throws Exception {
		this.initialize(context, config);
	}

	// FIXME you wen ti
	public void unload(NIOContext context, Configuration config) throws Exception {
		this.destroy(context, config);
	}

}
