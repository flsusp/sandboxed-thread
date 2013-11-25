package br.com.sandbox.thread;

import java.security.AccessControlException;
import java.security.Permission;

public class SandboxedThread<C> extends Thread {

	private final SandboxedRunnable<C> runnable;
	private boolean allowSettingSecurityManager;
	private C context;
	private SecurityManager oldSecurityManager;

	public SandboxedThread(SandboxedRunnable<C> runnable) {
		this.runnable = runnable;
	}

	public void start(C context) {
		this.context = context;
		this.oldSecurityManager = System.getSecurityManager();

		super.start();
	}

	public final void run() {
		final SandboxedThreadInfo threadInfo = new SandboxedThreadInfo(Thread.currentThread().getName());

		System.setSecurityManager(new SecurityManager() {

			@Override
			public void checkPermission(Permission perm) {
				if (RuntimePermission.class.isInstance(perm)) {
					if ("setSecurityManager".equals(perm.getName()) && allowSettingSecurityManager) {
						return;
					}
				}
				throw new AccessControlException("sandboxed access denied", perm);
			}
		});
		allowSettingSecurityManager = false;
		
		try {
			runnable.run(threadInfo, context);
		} finally {
			allowSettingSecurityManager = true;
			System.setSecurityManager(oldSecurityManager);
		}
	}
}
