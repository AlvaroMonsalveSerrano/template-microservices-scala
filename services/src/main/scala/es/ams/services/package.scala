package es.ams

import es.ams.services.adapter.BasicServiceAdapter.BasicService
import es.ams.services.views.BasicViews._
import zio.{ZIO, ZLayer, Has}
import zio.console.Console

package object services {

  type BasicService = Has[BasicService.Service]

  def getListEntity(): ZIO[BasicService, Throwable, List[BasicServiceResponse]] = ZIO.accessM(_.get.getListEntity())

  def doActionPost(request: BasicServiceRequest): ZIO[BasicService, Throwable, Int] =
    ZIO.accessM(_.get.doActionPost(request))

  def doActionPut(request: BasicServiceRequest): ZIO[BasicService, Throwable, BasicServiceResponse] =
    ZIO.accessM(_.get.doActionPut(request))

  def doActionDelete(id: Int): ZIO[BasicService, Throwable, Int] = ZIO.accessM(_.get.doDelete(id))

  val serviceBasicService: ZLayer[Any, Nothing, Console with BasicService] = Console.live ++ BasicService.live

}
