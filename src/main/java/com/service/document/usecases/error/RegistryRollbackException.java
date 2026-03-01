package com.service.document.usecases.error;

public class RegistryRollbackException extends RuntimeException {
    public RegistryRollbackException(String message) {
        super(message);
    }
}
