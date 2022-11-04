package ru.bortexel.stats.requests;

public class RequestException extends RuntimeException {
    private final int status;
    private String method;

    public RequestException(int status) {
        this(status, null);
    }

    public RequestException(int status, String method) {
        this.status = status;
        this.method = method;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        if (this.method == null) return "Got unsuccessful HTTP status code: " + this.getStatus();
        return "Got unsuccessful HTTP status code " + this.getStatus() + " while executing " + this.getMethod() + " request";
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
