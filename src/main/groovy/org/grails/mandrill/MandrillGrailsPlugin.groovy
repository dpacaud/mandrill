package org.grails.mandrill

import grails.plugins.Plugin

class MandrillGrailsPlugin extends Plugin {
	def grailsVersion = "3.0.0 > *"
	def title = "Grails Mandrill Plugin"
	def description = 'A simple wrapper for the Mandrill REST API (http://www.mandrillapp.com)'
	def documentation = "https://github.com/dpacaud/mandrill"
	def license = "APACHE"
	def developers = [[name: "Damien Pacaud", email: "damien.pacaud@gmail.com"]]
	def issueManagement = [url: "https://github.com/dpacaud/mandrill/issues"]
	def scm = [url: "https://github.com/dpacaud/mandrill"]
	def profiles = ['web']
}
