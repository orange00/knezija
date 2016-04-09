package knezija.utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtilities {
	public static String getFinalURL(String url) {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url)
					.openConnection();
			con.setInstanceFollowRedirects(false);
			con.connect();
			con.getInputStream();

			if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM
					|| con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
				String redirectUrl = con.getHeaderField("Location");
				return getFinalURL(redirectUrl);
			}
			return url;
		} catch (IOException exc) {
			return url;
		}
	}
}
