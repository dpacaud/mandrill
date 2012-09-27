Mandrill Grails Plugin
=============

This is a Grails plugin for the mandrill (http://www.mandrill.com) REST API


Supported Methods
-------
It curently handles :

* [Ping](https://mandrillapp.com/api/docs/users.html#method=ping)
* [Info](https://mandrillapp.com/api/docs/users.html#method=info)
* [Send](https://mandrillapp.com/api/docs/messages.html#method=send)


Installation
-------

Upon install, this plugin adds a section to your Config.groovy file

    mandrill {
              apiKey = ""
              // insert proxy values if needed
              //proxy {
              //      host = ""
              // The port Value has to be an integer ;)
              //      port = ""
              //}
              }

You need to fill in the apiKey parameter with the REST api KEY that you get with your [mandrill account](http://www.mandrill.com) 

Usage
-------

Inject MandrillService into your services or controllers :

    def mandrillService

### Ping

To call the ping method just type :

    def ret = mandrillService.ping()

ret should contain "PONG!"

### Info

To call the ping method just type :
    
    def ret =  mandrillService.info()

ret should contain a JSON array with the infos associated to your mandrill API Key


### Send

To send a mail :
       
    def recpts = []
    recpts.add(new MandrillRecipient(name:"foo", email:"foo@bar.com"))
    recpts.add(new MandrillRecipient(name:"bar", email:"bar@foo.com"))
    def message = new MandrillMessage(
                                      text:"this is a text message",
                                      subject:"this is a subject",
                                      from_email:"thisisatest@yopmail.com",
                                      to:recpts) 
    message.tags.add("test")
    def ret = mandrillService.send(message)

ret should contain a JSON array with success information or error information
