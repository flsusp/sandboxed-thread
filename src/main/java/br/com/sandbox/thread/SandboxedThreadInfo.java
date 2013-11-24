package br.com.sandbox.thread;

public class SandboxedThreadInfo {

	private final String threadName;

	public SandboxedThreadInfo(String name) {
		this.threadName = name;
	}

	public String getThreadName() {
		return threadName;
	}
}
