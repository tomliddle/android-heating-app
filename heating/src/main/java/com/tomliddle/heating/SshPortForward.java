package com.tomliddle.heating;

import android.content.Context;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by tom on 26/12/2014.
 */
public class SshPortForward {


	private JSch jsch=new JSch();
	private Session session = null;

	public SshPortForward(Context context) {
		InputStream privateStream = null;
		InputStream publicStream = null;

		try {
			session = jsch.getSession("tom", "tomliddle.asuscomm.com", 40);
			session.setTimeout(30000)
			//session.setServerAliveInterval(30000)
			session.setPortForwardingL(8080, "localhost", 8080);
			session.setConfig("StrictHostKeyChecking","no");

			privateStream = context.getResources().openRawResource(R.raw.id_rsa);
			publicStream = context.getResources().openRawResource(R.raw.id_rsa_pub);

			byte[] privateKey = IOUtils.toByteArray(privateStream);
			byte[] publicKey = IOUtils.toByteArray(publicStream);
			jsch.addIdentity("rpi2", privateKey, publicKey, "Atwood1234".getBytes(Charset.defaultCharset()));
			session.connect();

		}
		catch (Exception e) {
			int i = 0;
		}
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
		}
	}




}
