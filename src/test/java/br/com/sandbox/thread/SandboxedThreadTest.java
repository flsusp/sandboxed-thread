package br.com.sandbox.thread;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class SandboxedThreadTest {

	@Test
	public void testRunningValidCodeOnSandboxedThread() throws InterruptedException {
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

	@Test
	public void testOpeningFileOnSandboxedThread() throws InterruptedException {
		SandboxedThread<TestContext> thread = new SandboxedThread<>(new SandboxedRunnable<TestContext>() {

			@Override
			public void run(SandboxedThreadInfo info, TestContext context) {
				try {
					new File("/tmp/a").createNewFile();
				} catch (IOException e) {
				}
				context.markAsExecuted();
			}
		});

		TestContext context = new TestContext();

		thread.start(context);
		thread.join();

		assertFalse(context.wasExecuted());
	}

	@Test
	public void testOverritingSecurityManagerOnSandboxedThread() throws InterruptedException {
		SandboxedThread<TestContext> thread = new SandboxedThread<>(new SandboxedRunnable<TestContext>() {

			@Override
			public void run(SandboxedThreadInfo info, TestContext context) {
				System.setSecurityManager(null);
				context.markAsExecuted();
			}
		});

		TestContext context = new TestContext();

		thread.start(context);
		thread.join();

		assertFalse(context.wasExecuted());
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
