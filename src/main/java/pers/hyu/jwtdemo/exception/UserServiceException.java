package pers.hyu.jwtdemo.exception;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = -4220814337903178674L;

    public UserServiceException(String message) {
        super(message);
    }
}
