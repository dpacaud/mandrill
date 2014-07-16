Mandrill Grails Plugin
=============

This is a Grails plugin for the mandrill (http://www.mandrill.com) REST API


Supported Methods
-------
It curently handles :

* [Ping](https://mandrillapp.com/api/docs/users.html#method=ping)
* [Info](https://mandrillapp.com/api/docs/users.html#method=info)
* [Send](https://mandrillapp.com/api/docs/messages.html#method=send)
* [SendTemplate](https://mandrillapp.com/api/docs/messages.JSON.html#method=send-template)


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

To send a text mail :

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

send will return a SendResponse Object with success or error information

To send an HTML mail :

    def recpts = []
    recpts.add(new MandrillRecipient(name:"foo", email:"foo@bar.com"))
    recpts.add(new MandrillRecipient(name:"bar", email:"bar@foo.com"))
    def message = new MandrillMessage(
                                      html:"<html><body>this is an<b>html</b> message</body></html>",
                                      subject:"this is a subject",
                                      from_email:"thisisatest@yopmail.com",
                                      to:recpts)
    message.tags.add("test")
    def ret = mandrillService.send(message)

send will return SendResponse Object with success or error information

### SendTemplate

To send a mail using template :

    def recpts = []
    recpts.add(new MandrillRecipient(name:"foo", email:"foo@bar.com"))
    recpts.add(new MandrillRecipient(name:"bar", email:"bar@foo.com"))
    def contents = []
    contents.add([name:"test name", content:"test content"])
    def message = new MandrillMessage(
                                      text:"this is a text message",
                                      subject:"this is a subject",
                                      from_email:"thisisatest@yopmail.com",
                                      to:recpts)
    message.tags.add("test")
    def ret = mandrillService.sendTemplate(message, "templateName", contens )

### SendResponse
Per the [Mandrill API](https://mandrillapp.com/api/docs/messages.JSON.html#example-response-send) send calls will respond with:

    Example Response JSON
    [
      {
          "email": "recipient.email@example.com",
          "status": "sent",
          "reject_reason": "hard-bounce",
          "_id": "abc123abc123abc123abc123abc123"
      }
    ]

    Example Error Response JSON
    [
      {
          "status": "error",
          "code": 12,
          "name": "Unknown_Subaccount",
          "message": "No subaccount exists with the id 'customer-123'"
      }
    ]

These fields will be accessible via a SendResponse object:

    def ret = mandrillService.sendTemplate(message, "templateName", contens )
    if (ret.success) {
        // do successful things
    } else {
      log.error("Error sending email: status: $ret.status rejectReason: $ret.rejectReason, message: $ret.message")
    }
Note: success is derived from status being either 'sent', 'queued', or 'scheduled'


### Contributors

Thanks to [James Schindler](https://github.com/Jimbo1167), [gaganc](https://github.com/gaganc) and [cympak2](https://github.com/cympak2) for their contributions