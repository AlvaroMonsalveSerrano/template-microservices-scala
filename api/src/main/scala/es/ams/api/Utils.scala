package es.ams.api

import cats.data.Chain
import es.ams.api.views.BasicDTO.{CreateBasic, UpdateBasic}

private[api] object Utils {

  // Es una utilidad.
  def getStringValueFromChain[A](chain: Chain[A]): String = chain match {
    case Chain(a) => a.toString
    case _        => ""
  }

  def getIntValueFromChain[A](chain: Chain[A]): Int = chain match {
    case Chain(a) => getStringValueFromChain(chain).toInt
    case _        => 0
  }

  def loadCreateBasic[A](elem1: Chain[A], elem2: Chain[A]): CreateBasic =
    CreateBasic(name = getStringValueFromChain(elem1), value = getStringValueFromChain(elem2))

  def loadUpdateBasic[A](elem1: Chain[A], elem2: Chain[A], elem3: Chain[A]): UpdateBasic =
    UpdateBasic(
      id = getIntValueFromChain(elem1),
      name = getStringValueFromChain(elem2),
      value = getStringValueFromChain(elem3)
    )
}
