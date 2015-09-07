package org.grails.mandrill

import javax.annotation.PostConstruct

import grails.converters.JSON

class MandrillService {

	static transactional = false

	def grailsApplication
	def httpWrapperService

	static final String BASE_URL = "https://mandrillapp.com/api/1.0/"

	//TODO : Handle the API key and throw an error when it is absent
	String ping() {
		postText "users/ping.json"
	}

	String info() {
		postText "users/info.json"
	}

	List<SendResponse> send(MandrillMessage message) {
		JSON.parse(postText("messages/send.json", [message: message])).collect { new SendResponse(it) }
	}

	List<SendResponse> sendTemplate(MandrillMessage message, String templateName, List templateContent) {
		def query =  [template_name: templateName, template_content: templateContent, message: message]
		JSON.parse(postText("messages/send-template.json", query)).collect { new SendResponse(it) }
	}

	private String postText(String path, Map query = [:]) {
		httpWrapperService.postText(BASE_URL, path, query + [key: mandrillConfig.apiKey])
	}

	@PostConstruct
	private void init() {
		if (!mandrillConfig.apiKey && System.getenv('MANDRILL_APIKEY')) {
			mandrillConfig.apiKey = System.getenv('MANDRILL_APIKEY')
		}
	}

	private getMandrillConfig() {
		grailsApplication.config.mandrill
	}
}
