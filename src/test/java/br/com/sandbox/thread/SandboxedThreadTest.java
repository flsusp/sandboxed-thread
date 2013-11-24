package br.com.sandbox.thread;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SandboxedThreadTest {

	@Test
	public void testCreatingAndRunningValidCodeOnSandboxedThread() throws InterruptedException {
		SandboxedThread<TestContext> thread = new SandboxedThread<>(new SandboxedRunnable<TestContext>() {

			@Override
			public void run(SandboxedThreadInfo info, TestContext context) {
				context.markAsExecuted();
			}
		});

		TestContext context = new TestContext();

		thread.start(context);
		thread.join();

		assertTrue(context.wasExecuted());
	}
}

class TestContext {

	private boolean executed = false;

	public void markAsExecuted() {
		this.executed = true;
	}

	public boolean wasExecuted() {
		return executed;
	}
}
