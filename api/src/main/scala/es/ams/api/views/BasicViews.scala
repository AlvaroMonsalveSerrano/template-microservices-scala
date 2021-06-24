package es.ams.api.views

import io.circe.{Decoder, Encoder}

object BasicViews {

  case class BasicResponse(name: String, value: String)
  object BasicResponse {
    implicit val basicRespondeDecoder: Decoder[BasicResponse] =
      Decoder.forProduct2("name", "value")(BasicResponse.apply)

    implicit val basicRespondeEncoder: Encoder[BasicResponse] =
      Encoder.forProduct2("name", "value")(data => (data.name, data.value))
  }

  case class ErrorResponse(codError: Int, message: String)
  object ErrorResponse {
    implicit val errorResponseDecoder: Decoder[ErrorResponse] =
      Decoder.forProduct2("codError", "message")(ErrorResponse.apply)

    implicit val errorResponseEncoder: Encoder[ErrorResponse] =
      Encoder.forProduct2("codError", "message")(data => (data.codError, data.message))
  }
}
