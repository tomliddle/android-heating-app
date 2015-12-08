package com.tomliddle.heating;

import android.content.Context;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Sets up port forwarding to the thermometer machine.
 */
public class SshPortForward {

	private JSch jsch=new JSch();
	private Session session = null;

	private static final String PASSWORD = "password";
	private static final String HOSTNAME = "hostname";
	private static final String USERNAME = "username";
	private static final String SSH_PORT = 22;

	private static final String HTTP_LOCAL_PORT = 8080;
	private static final String HTTP_REMOTE_PORT = 8080;
	private static final String PUBLIC_KEY_IDENTITY = "rpi";

	/**
	 * Opens a session and connects to the specified machine.
	 * @param context - resources required are R.raw.id_rsa and R.raw.id_rsa_pub
	 */
	public SshPortForward(Context context) {
		InputStream privateStream = null;
		InputStream publicStream = null;

		try {
			session = jsch.getSession(USERNAME, HOSTNAME, SSH_PORT);
			session.setTimeout(30000);
			session.setPortForwardingL(HTTP_LOCAL_PORT, "localhost", HTTP_REMOTE_PORT);
			session.setConfig("StrictHostKeyChecking","no");

			// These resources need to be added.
			// TODO these should be stored in the Android Keystore
			privateStream = context.getResources().openRawResource(R.raw.id_rsa);
			publicStream = context.getResources().openRawResource(R.raw.id_rsa_pub);

			byte[] privateKey = IOUtils.toByteArray(privateStream);
			byte[] publicKey = IOUtils.toByteArray(publicStream);
			jsch.addIdentity(PUBLIC_KEY_IDENTITY, privateKey, publicKey, PASSWORD.getBytes(Charset.defaultCharset()));
			session.connect();

		}
		catch (Exception e) {}
		finally {
			IOUtils.closeQuietly(publicStream);
			IOUtils.closeQuietly(privateStream);
		}
	}

	public void ensureConnected() {
		if (session != null && !session.isConnected()) {
			try {
				session.connect();
			}
			catch (Exception e) {}
		}
	}

	public void disconnect() {
		if (session != null) {
			session.disconnect();
			session = null;
		}
	}

}
