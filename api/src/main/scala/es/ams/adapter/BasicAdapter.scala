package es.ams.adapter

import cats.effect.IO

object BasicAdapter {

  import es.ams.views.BasicViews._

  def getListEntity(): IO[List[BasicResponse]] = IO(
    List(
      BasicResponse(name = "name1_1", value = "value1_2"),
      BasicResponse(name = "name2_1", value = "value2_2"),
      BasicResponse(name = "name3_1", value = "value3_2")
    )
  )

  def getResult(param1: String, param2: String): List[BasicResponse] =
    List(
      BasicResponse(name = "1", value = param1),
      BasicResponse(name = "2", value = param2)
    )

  def doActionPost(param1: String, param2: String): IO[List[BasicResponse]] = IO(
    getResult(param1, param2)
  )

  def doActionPut(param1: String, param2: String): IO[List[BasicResponse]] = IO(
    getResult(param1, param2)
  )

}
