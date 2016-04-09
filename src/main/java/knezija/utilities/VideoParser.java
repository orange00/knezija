package knezija.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoParser {

	public static String getDataYoutube(String url) {
		return extractYTId(url);
	}

	private static String extractYTId(String ytUrl) {
		String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
	    Pattern compiledPattern = Pattern.compile(pattern);
	    Matcher matcher = compiledPattern.matcher(ytUrl);
	    if(matcher.find()){
	        return matcher.group();
	    } else {
	        return "error";  
	    }
	}

	public static boolean isVideoUrl(String urlname) {
		String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
	    Pattern compiledPattern = Pattern.compile(pattern);
	    Matcher matcher = compiledPattern.matcher(urlname);
		return matcher.find();
	}

}