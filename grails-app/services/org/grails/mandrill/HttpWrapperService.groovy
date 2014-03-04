
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
					log.info "Mandrill plugin is setting setting Proxy to : ${grailsApplication.config.mandrill.proxy.host} on port : ${grailsApplication.config.mandrill.proxy.port}"
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


				// response handler for a success response code
				response.success = { resp, reader ->
					log.debug "response status: ${resp.statusLine}"
                    log.debug 'Headers: -----------'
					resp.headers.each { h ->
                        log.debug " ${h.name} : ${h.value}"
					}

					ret = reader.getText()

                    log.debug 'Response data: -----'
                    log.debug ret
                    log.debug '--------------------'
				}

				response.failure = { resp, reader ->
                    log.debug "------ Failure ------ "
					resp.headers.each { h ->
                        log.debug " ${h.name} : ${h.value}"
					}
					ret = reader.getText()

                    log.debug 'Response data: -----'
                    log.debug ret
                    log.debug '--------------------'
				}
			}
			return ret

		} catch (groovyx.net.http.HttpResponseException ex) {
			log.error ex.getMessage()
			return null
		} catch (java.net.ConnectException ex) {
            log.error ex.getMessage()
			return null
		}
        catch (NumberFormatException nfe) {
            log.error nfe.getMessage()
            return null
        }
		catch (Exception e) {
            log.error e.getMessage()
			return null
		}
	}

}
