package es.ams.exception

object BasicException {

  final class ServiceBasicException(
      private val message: String = "",
      private val cause: Throwable = None.orNull
  ) extends Exception(message, cause)

}
