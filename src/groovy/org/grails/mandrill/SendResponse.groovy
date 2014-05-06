package org.grails.mandrill

class SendResponse {
    String status
    String message
    String email
    String id
    String rejectReason
    boolean success

    SendResponse(data) {
        this.status = data?.status
        this.message = data?.message
        this.rejectReason = data?.reject_reason
        this.email = data?.email
        this.id = data?._id
        success = ['sent', 'queued', 'scheduled'].contains(this.status)
    }
}
