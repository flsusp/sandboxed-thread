package br.com.sandbox.thread;

public class SandboxedThread<C> extends Thread {

	private final SandboxedRunnable<C> runnable;

	public SandboxedThread(SandboxedRunnable<C> runnable) {
		this.runnable = runnable;
	}

	public void start(C context) {
		runnable.run(new SandboxedThreadInfo(Thread.currentThread().getName()), context);
	}
}
