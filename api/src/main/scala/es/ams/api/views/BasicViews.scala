package es.ams.api.views

import io.circe.{Decoder, Encoder}

protected[api] object BasicViews {

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

  case class CreateResponse(id: Int)
  object CreateResponse {

    implicit val createResponseDecoder: Decoder[CreateResponse] =
      Decoder.forProduct1("id")(CreateResponse.apply)

    implicit val createResponseEncoder: Encoder[CreateResponse] =
      Encoder.forProduct1("id")(data => (data.id))
  }
}
