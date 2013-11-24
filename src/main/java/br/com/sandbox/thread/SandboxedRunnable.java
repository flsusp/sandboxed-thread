package br.com.sandbox.thread;

public interface SandboxedRunnable <C> {

	void run(SandboxedThreadInfo info, C context);
}
