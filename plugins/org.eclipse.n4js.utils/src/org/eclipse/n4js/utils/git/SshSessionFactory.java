/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.git;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import com.google.common.base.StandardSystemProperty;
import com.google.common.io.Files;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * SSH session factory for JGit checkout.
 * <p>
 * This class requires to have the private key under {@code .ssh} in the home folder.
 */
/* default */ class SshSessionFactory extends JschConfigSessionFactory {

	@Override
	protected void configure(final Host host, final Session session) {
		// ignored
	}

	@Override
	protected JSch getJSch(final Host host, final FS fs) throws JSchException {
		return configureIdentity(super.getJSch(host, fs));
	}

	@Override
	protected JSch createDefaultJSch(final FS fs) throws JSchException {
		return configureIdentity(super.createDefaultJSch(fs));
	}

	private JSch configureIdentity(final JSch jsch) throws JSchException {

		final File sshDirectory = new File(StandardSystemProperty.USER_HOME.value() + "/.ssh");
		checkState(sshDirectory.exists(), ".ssh directory does not exist under home folder.");
		checkState(sshDirectory.canRead(), ".ssh directory content cannot be read.");
		checkState(sshDirectory.isDirectory(), sshDirectory.getAbsoluteFile() + " is not a directory.");
		final File[] keyFiles = sshDirectory.listFiles();
		checkState(null != keyFiles && keyFiles.length > 0, "No SSH key files exist.");

		jsch.removeAllIdentity();
		for (final File keyFile : keyFiles) {
			if (keyFile.isFile()) {
				final String keyUri = keyFile.toURI().toString();
				final int lastIndexOf = keyUri.lastIndexOf("/");
				if (lastIndexOf > 0) {
					final String keyFileName = keyUri.substring(lastIndexOf + 1);
					if ("id_rsa".equals(keyFileName) || "id_dsa".equals(keyFileName)) {
						if (isEncrypted(keyFile)) {
							String pw = getPassphrase(keyFile);
							jsch.addIdentity(keyFile.getAbsolutePath(), pw);
						} else {
							jsch.addIdentity(keyFile.getAbsolutePath());
						}

						break;
					}
				}
			}
		}

		return jsch;
	}

	/**
	 * Tries to get passphrase for encrypted SSH key, either from system property or console. Note that running inside
	 * Eclipse no console is available. In this case, the password is to be passed as system property. It can "securely"
	 * be requested via prompt when starting the test from Eclipse adding
	 *
	 * <pre>
	 * -Dorg.eclipse.n4js.ssh.pw.«name»=${password_prompt}
	 * </pre>
	 *
	 * to the VM arguments (with name matching the name of the key file.
	 */
	private String getPassphrase(File keyFile) {
		String name = keyFile.getName();
		String pw = System.getProperty("org.eclipse.n4js.ssh.pw." + name);
		if (pw == null) {
			if (System.console() == null) {
				String msg = "Need passphrase to decrypt ssh key \n" +
						"Add this following line to VM arguments in debug config:\n" +
						"-D" + "org.eclipse.n4js.ssh.pw." + name + "=${password_prompt}";
				System.err.println(msg);
				throw new IllegalStateException(msg);

			} else {
				pw = new String(System.console().readPassword("Enter passphrase for ssh key " + name));
			}
		}
		return pw;

	}

	private boolean isEncrypted(File keyFile) {
		try {
			String s = Files.toString(keyFile, Charset.defaultCharset());
			return s.indexOf("ENCRYPTED") >= 0;
		} catch (IOException e) {
			return false;
		}
	}
}
