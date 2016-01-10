package scheduleserver.bins.wsresponse;

import org.springframework.http.HttpStatus;

public class WSResponse {
    private HttpStatus status;
    private String text;

    public WSResponse() {

    }

    public WSResponse(HttpStatus status, String text) {
        this.status = status;
        this.text = text;
    }

    public void setStatus(HttpStatus status) {

        this.status = status;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HttpStatus getStatus() {

        return status;
    }

    public String getText() {
        return text;
    }
}
