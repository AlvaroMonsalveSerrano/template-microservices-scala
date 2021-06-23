package es.ams.api.views

import io.circe.{Decoder, Encoder}

object BasicViews {

  case class BasicResponse(name: String, value: String)
  object BasicResponse {
    implicit val parameterRequestDecoder: Decoder[BasicResponse] =
      Decoder.forProduct2("name", "value")(BasicResponse.apply)

    implicit val parameterRequestEncoder: Encoder[BasicResponse] =
      Encoder.forProduct2("name", "value")(data => (data.name, data.value))
  }

}
