package org.grails.mandrill

import static org.junit.Assert.*
import grails.test.mixin.*
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin

import org.grails.mandrill.HttpWrapperService;
import org.grails.mandrill.MandrillService;
import org.junit.*

@TestMixin([GrailsUnitTestMixin, ControllerUnitTestMixin])
class MandrillServiceTests  {

	def mandrillService
	def httpWrapperService
    @Before
    void setUp() {
		
		mandrillService = new MandrillService()
		httpWrapperService = new HttpWrapperService()
		mandrillService.httpWrapperService = httpWrapperService
		def application = new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
		mandrillService.grailsApplication = application
		httpWrapperService.grailsApplication = application 
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testPing() {
        assert mandrillService.ping() == "\"PONG!\""
    }
	
	@Test
	void testSend() {
		def recpts = []
		recpts.add(new MandrillRecipient(name:"Damien", email:"toto@yopmail.com"))
		recpts.add(new MandrillRecipient(name:"TATA", email:"tata@yopmail.com"))
		def message = new MandrillMessage(
										text:"this is a text message",
										subject:"this is a subject",
										from_email:"thisisatest@yopmail.com",
										to:recpts)
		message.tags.add("test")
		assert mandrillService.send(message).contains("\"status\":\"sent\"")
	}
}
