package wtfIsRefined213

import scala.util.chaining._

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Interval
import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.api.RefinedTypeOps

/*
  See:
  https://medium.com/@Methrat0n/wtf-is-refined-5008eb233194
 */
object SimplerValidations extends _root_.util.App {

  type Id          = Int Refined Interval.Open[100, 500]
  type CodeArticle = String Refined MatchesRegex["^[0-9]{3,12}$"]

  object Id          extends RefinedTypeOps[Id, Int]
  object CodeArticle extends RefinedTypeOps[CodeArticle, String]

  final case class Article(id: Id, codeArticle: CodeArticle)

  errorOrArticle(102, "001") pipe println // will yield a Right
  errorOrArticle(92, "001") pipe println  // will yield a Left

  def errorOrArticle(id: Int, code: String): Either[String, Article] =
    for {
      validId   <- Id.from(id) //Will stop here as id is out of the Id scope
      validCode <- CodeArticle.from(code)
    } yield Article(validId, validCode)
}
