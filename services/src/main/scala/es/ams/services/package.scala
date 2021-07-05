package es.ams

import es.ams.services.adapter.BasicServiceAdapter.BasicService
import es.ams.services.adapter.BusinessServiceAdapter.BusinessService
import es.ams.services.views.BasicViews._

import es.ams.model.Base

import zio.{Has, ZIO, ZLayer}
import zio.console.Console

package object services {

  // BasicService
  type BasicService = Has[BasicService.Service]

  def getListEntity(): ZIO[BasicService, Throwable, List[BasicServiceResponse]] = ZIO.accessM(_.get.getListEntity())

  def doActionPost(request: BasicServiceRequest): ZIO[BasicService, Throwable, Int] =
    ZIO.accessM(_.get.doActionPost(request))

  def doActionPut(request: BasicServiceRequest): ZIO[BasicService, Throwable, BasicServiceResponse] =
    ZIO.accessM(_.get.doActionPut(request))

  def doActionDelete(id: Int): ZIO[BasicService, Throwable, Int] = ZIO.accessM(_.get.doDelete(id))

  val serviceBasicService: ZLayer[Any, Nothing, Console with BasicService] = Console.live ++ BasicService.live

  // BusinessService
  type BusinessService = Has[BusinessService.Service]

  def doSomething1(param1: String): ZIO[BusinessService, Any, String] = ZIO.accessM(_.get.doSomething1(param1))

  def findAll(): ZIO[BusinessService, Any, List[Base]] = ZIO.accessM(_.get.findAll())

  def serviceBusinessService(url: String): ZLayer[Any, Nothing, BusinessService] = BusinessService.live(url)

}
