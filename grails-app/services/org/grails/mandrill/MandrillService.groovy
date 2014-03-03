package org.grails.mandrill


class MandrillService {
	static transactional = false
	def httpWrapperService
    def grailsApplication

	def BASE_URL = "https://mandrillapp.com/api/1.0/"
    //TODO : Handle the API key and throw an error when it is absent
	def ping() {
		def path = "users/ping.json"
		def query =  [key:grailsApplication.config.mandrill.apiKey]
		return  httpWrapperService.postText(BASE_URL, path ,query)
	}
	
	
	def info() {
		def path = "users/info.json"
		def query =  [key:grailsApplication.config.mandrill.apiKey]
		return  httpWrapperService.postText(BASE_URL, path ,query)
	}
	
	
	def send(MandrillMessage message) {
		def path = "messages/send.json"
		def query =  [key:grailsApplication.config.mandrill.apiKey,message:message]
		return new SendResponse(httpWrapperService.postText(BASE_URL, path ,query))
	}
	
	def sendTemplate(MandrillMessage message, String templateName, List templateContent) {
		def path = "messages/send-template.json"
		def query =  [key:grailsApplication.config.mandrill.apiKey, template_name:templateName,
			template_content:templateContent, message:message]
		return  httpWrapperService.postText(BASE_URL, path ,query)
	}	

}
