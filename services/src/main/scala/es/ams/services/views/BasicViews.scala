package es.ams.services.views

object BasicViews {

  case class BasicServiceResponse(name: String = "", value: String = "") {
    def isValid(): Boolean = true
  }

  case class BasicServiceRequest(id: Option[Int] = None, name: String, value: String)

}
