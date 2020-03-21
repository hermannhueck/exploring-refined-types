package singleton.types

import scala.util.chaining._
import util.formatting._
import munit.Assertions._

import shapeless._
import syntax.singleton._

/*
  See:
  https://docs.scala-lang.org/sips/42.type.html
 */

object ShapelessWitness extends util.App {

  "ShapelessWitness".magenta pipe println

  val wOne        = Witness(1)
  val one: wOne.T = wOne.value // wOne.T is the type 1
  // wOne.value is 1: 1

  def foo[T](implicit w: Witness.Aux[T]): w.T = w.value
  foo[wOne.T] // result is 1: 1

  "foo" ->> 23 // shapeless record field constructor
  // result type is FieldType["foo", Int]
}
