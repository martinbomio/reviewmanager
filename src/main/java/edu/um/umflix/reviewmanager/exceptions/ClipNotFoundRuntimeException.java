package edu.um.umflix.reviewmanager.exceptions;

/**
 * Exception thrown if a clip isn't found in the database. It is a RuntimeException
 * because this exception will be thrown only if the databse connection is lost.
 */
public class ClipNotFoundRuntimeException extends RuntimeException {
}
