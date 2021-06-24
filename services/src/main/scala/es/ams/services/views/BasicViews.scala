package es.ams.services.views

object BasicViews {

  case class BasicResponse(name: String, value: String) {
    def isValid(): Boolean = true
  }

  case class BasicRequest(id: Option[Int] = None, name: String, value: String)

}
