package singleton.types

import scala.util.chaining._
import util.formatting._

import shapeless._
import record._
import syntax.singleton._

/*
  See:
  https://docs.scala-lang.org/sips/42.type.html
 */

object ShapelessRecords extends util.App {

  "ShapelessRecords".magenta pipe println

  // syntax removed in shapeless 2.1.0
  // type Book =
  //   (wAuthor.T ->> String) ::
  //     (wTitle.T ->> String) ::
  //     (wId.T ->> Int) ::
  //     (wPrice.T ->> Double) ::
  //     HNil

  type Book = Record.`"author" -> String, "title" -> String, "id" -> Int, "price" -> Double`.T

  val book: Book =
    ("author" ->> "Benjamin Pierce") ::
      ("title" ->> "Types and Programming Languages") ::
      ("id" ->> 262162091) ::
      ("price" ->> 44.11) ::
      HNil
}
