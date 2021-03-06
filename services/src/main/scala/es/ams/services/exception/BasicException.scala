package es.ams.services.exception

object BasicException {

  final class ServiceBasicException(
      private val codError: Int = 0,
      private val message: String = "",
      private val cause: Throwable = None.orNull
  ) extends Throwable(message, cause) {
    def getCodeError(): Int = codError
  }

}
