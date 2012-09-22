
package org.grails.mandrill

import grails.converters.JSON
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class HttpWrapperService {
	static transactional = false
    def grailsApplication

	def postText(String baseUrl, String path, query, method = Method.POST) {
		try {
			
			def ret = null
			def http = new HTTPBuilder(baseUrl)

            /*************************************
             * Handling Proxy configuration
             *************************************/

            if(grailsApplication.config.mandrill.proxy?.host && grailsApplication.config.mandrill.proxy?.port){
                if(grailsApplication.config.mandrill.proxy?.port?.isInteger()){
					log.info "setting Proxy to : ${grailsApplication.config.mandrill.proxy.host} on port : ${grailsApplication.config.mandrill.proxy.port}"
                    http.setProxy(grailsApplication.config.mandrill.proxy.host,Integer.parseInt(grailsApplication.config.mandrill.proxy.port),null)
                }
                else {
                    throw new NumberFormatException("mandrill Proxy PORT must be an integer, please correct the config file")
                }
            }

			// perform a POST request, expecting TEXT response
			http.request(method, ContentType.TEXT) {

				uri.path = path
				body = new JSON(query)
				
				headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'
				println uri
				// response handler for a success response code
				response.success = { resp, reader ->
					println "response status: ${resp.statusLine}"
					println 'Headers: -----------'
					resp.headers.each { h ->
						println " ${h.name} : ${h.value}"
					}

					ret = reader.getText()

					println 'Response data: -----'
					println ret
					println '--------------------'
				}
				
				response.failure = { resp, reader ->
					println "------ Failure ------ "
					resp.headers.each { h ->
						println " ${h.name} : ${h.value}"
					}
					ret = reader.getText()
					
					println 'Response data: -----'
					println ret
					println '--------------------'
				}
			}
			return ret

		} catch (groovyx.net.http.HttpResponseException ex) {
			println "toto"
			println ex
			return null
		} catch (java.net.ConnectException ex) {
		println "tata"
			println ex
			return null
		}
        catch (NumberFormatException nfe) {
            log.error nfe.getMessage()
            return null
        }
		catch (Exception e) {
			println "titi"
			println e
			return null
		}
	}

}
