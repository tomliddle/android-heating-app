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


	JSch jsch=new JSch();

	public SshPortForward(Context context) {

		try {
			final Session session = jsch.getSession("tom", "tomliddle.asuscomm.com", 40);
			int assinged_port = session.setPortForwardingL(8080, "localhost", 8080);
			session.setConfig("StrictHostKeyChecking","no");


			InputStream privateStream = context.getResources().openRawResource(R.raw.id_rsa);
			InputStream publicStream = context.getResources().openRawResource(R.raw.id_rsa_pub);

			byte[] privateKey = IOUtils.toByteArray(privateStream);
			byte[] publicKey = IOUtils.toByteArray(publicStream);
			jsch.addIdentity("rpi2", privateKey, publicKey, "Atwood1234".getBytes(Charset.defaultCharset()));
			session.connect();

		}
		catch (Exception e) {
			int i = 0;
		}
	}




}
