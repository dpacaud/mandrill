package org.grails.mandrill

class SendResponse {
    String status
    String email
    String id
    boolean success

    SendResponse(data) {
        data = data.first()
        this.status = data?.status
        this.email = data?.email
        this.id = data?._id
        success = ['sent', 'queued', 'scheduled'].contains(this.status)
    }
}
