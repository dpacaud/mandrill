package org.grails.mandrill

import grails.test.mixin.TestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import spock.lang.Specification

@TestMixin(ControllerUnitTestMixin)
class MandrillServiceTests extends Specification {

	def mandrillService = new MandrillService()

	void setup() {
		mandrillService.httpWrapperService = new HttpWrapperService(grailsApplication: grailsApplication)
		mandrillService.grailsApplication = grailsApplication
		mandrillService.init()
	}

	void 'ping'() {
		expect:
		mandrillService.ping() == '"PONG!"'
	}

	void 'send'() {
		when:
		def recpts = [new MandrillRecipient(name: "Damien", email: "toto@yopmail.com"),
		              new MandrillRecipient(name: "TATA", email: "tata@yopmail.com")]
		def message = new MandrillMessage(
				  text: "this is a text message",
				  subject: "this is a subject",
				  from_email: "thisisatest@yopmail.com",
				  to: recpts)
		message.tags << "test"
		List<SendResponse> responses = mandrillService.send(message)

		then:
		responses.every { SendResponse response -> response.status == 'sent' }
	}
}
