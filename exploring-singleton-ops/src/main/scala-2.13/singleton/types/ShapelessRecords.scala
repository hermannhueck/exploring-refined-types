package singleton.types

import scala.util.chaining._
import util.formatting._
import munit.Assertions._

import shapeless._
import record._
import ops.hlist.ToList
import ops.record.{Keys, Values}
import syntax.singleton._

/*
  See:
  https://docs.scala-lang.org/sips/42.type.html
 */

object ShapelessRecords extends util.App {

  "ShapelessRecords".magenta pipe println

  // use 2.13 singleton types
  val book213 =
    ("author" ->> "Benjamin Pierce") ::
      ("title" ->> "Types and Programming Languages") ::
      ("id" ->> 262162091) ::
      ("price" ->> 44.11) ::
      HNil

  // use Witness in 2.12
  val wAuthor = Witness("author")
  val wTitle  = Witness("title")
  val wId     = Witness("id")
  val wPrice  = Witness("price")

  // syntax removed in shapeless 2.1.0
  // type Book =
  //   (wAuthor.T ->> String) ::
  //     (wTitle.T ->> String) ::
  //     (wId.T ->> Int) ::
  //     (wPrice.T ->> Double) ::
  //     HNil

  type R = Record.`'i -> Int, 's -> String, 'b -> Boolean`.T

  type Book = Record.`"author" -> String, "title" -> String, "id" -> Int, "price" -> Double`.T

  val book212: Book =
    ("author" ->> "Benjamin Pierce") ::
      ("title" ->> "Types and Programming Languages") ::
      ("id" ->> 262162091) ::
      ("price" ->> 44.11) ::
      HNil

}
