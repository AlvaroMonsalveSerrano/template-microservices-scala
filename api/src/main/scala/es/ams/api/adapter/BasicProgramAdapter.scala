package es.ams.api.adapter

import es.ams.api.views.BasicDTO.{CreateBasic, DeleteBasic, UpdateBasic}
import es.ams.api.views.BasicViews.BasicResponse
import es.ams.services.views.BasicViews.BasicServiceRequest
import es.ams.services.{BasicService, doActionDelete, doActionPost, doActionPut, getListEntity}
import zio.ZIO
import zio.console.{Console, putStrLn}

object BasicProgramAdapter {

  def programGetList(): ZIO[Console with BasicService, Throwable, List[BasicResponse]] =
    for {
      _    <- putStrLn("[START] GetList...")
      list <- getListEntity()
      _    <- putStrLn("[END] GetList...")
    } yield { list.map(elem => BasicResponse(name = elem.name, value = elem.value)) }

  def programDoPost(dtoRequest: CreateBasic): ZIO[Console with BasicService, Throwable, Int] =
    for {
      _               <- putStrLn("[START] Insert entity ...")
      idEntityCreated <- doActionPost(BasicServiceRequest(name = dtoRequest.name, value = dtoRequest.value))
      _               <- putStrLn("[END] Insert entity ...")
    } yield { idEntityCreated }

  def programDoPut(dtoRequest: UpdateBasic): ZIO[Console with BasicService, Throwable, BasicResponse] =
    for {
      _ <- putStrLn("[START] Update entity ...")
      entityUpdated <- doActionPut(
        BasicServiceRequest(id = Some(dtoRequest.id), name = dtoRequest.name, value = dtoRequest.value)
      )
      _ <- putStrLn("[END] Update entity ...")
    } yield { BasicResponse(name = entityUpdated.name, value = entityUpdated.value) }

  def programDoDelete(dtoRequest: DeleteBasic): ZIO[Console with BasicService, Throwable, Int] =
    for {
      _               <- putStrLn("[START] Delete entity ...")
      idEntityDeleted <- doActionDelete(dtoRequest.id)
      _               <- putStrLn("[END] Delete entity ...")
    } yield { idEntityDeleted }

}
