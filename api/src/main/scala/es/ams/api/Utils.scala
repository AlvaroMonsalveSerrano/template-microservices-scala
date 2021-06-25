package es.ams.api

import cats.data.Chain

protected[api] object Utils {

  // Es una utilidad.
  def getValueFromChain[A](chain: Chain[A]): String = chain match {
    case Chain(a) => a.toString
    case _        => ""
  }

}
