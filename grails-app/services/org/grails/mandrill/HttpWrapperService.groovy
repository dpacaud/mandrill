package org.grails.mandrill

import grails.converters.JSON
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Slf4j
class HttpWrapperService {

	static transactional = false

	def grailsApplication

	String postText(String baseUrl, String path, query, method = Method.POST) {
		try {
			def proxyConf = grailsApplication.config.mandrill.proxy
			String ret
			def http = new HTTPBuilder(baseUrl)
			// Handling Proxy configuration

			if (proxyConf?.host && proxyConf?.port) {
				if (!proxyConf.port?.isInteger()) {
					throw new NumberFormatException("mandrill Proxy PORT must be an integer, please correct the config file")
				}

				log.info "Mandrill plugin is setting setting Proxy to : {} on port : {}", proxyConf.host, proxyConf.port
				http.setProxy(proxyConf.host, proxyConf.port as int, null)
			}

			// perform a POST request, expecting TEXT response
			http.request(method, ContentType.TEXT) {

				uri.path = path
				body = new JSON(query)

				// response handler for a success response code
				response.success = { resp, reader ->
					if (log.debugEnabled) {
						log.debug "response status: {}", resp.statusLine
						log.debug 'Headers: -----------'
						resp.headers.each { h -> log.debug " {} : {}", h.name, h.value }
					}

					ret = reader.text

					if (log.debugEnabled) {
						log.debug 'Response data: -----'
						log.debug ret
						log.debug '--------------------'
					}
				}

				response.failure = { resp, reader ->
					if (log.debugEnabled) {
						log.debug "------ Failure ------ "
						resp.headers.each { h -> log.debug " {} : {}", h.name, h.value }
						ret = reader.text
					}

					if (log.debugEnabled) {
						log.debug 'Response data: -----'
						log.debug ret
						log.debug '--------------------'
					}
				}
			}
			return ret
		}
		catch (e) {
			log.error e.message, e
		}
	}
}
