package org.grails.mandrill


class MandrillMessage {

	String text
	String html
	String subject
	String from_email
	String from_name
	List attachments
	List to
	boolean track_opens=false
	boolean track_clicks=false
	boolean auto_text=false
	boolean url_strip_qs=false
	List tags = []
//	 String[] google_analytics_domains = new String[0]
//	 String[] google_analytics_campaign = new String[0]
//	// List<MergeVar> global_merge_vars
	Map headers
	// Map<String, String> headers
	
}
