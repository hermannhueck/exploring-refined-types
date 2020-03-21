package singleton.types

import scala.util.chaining._
import util.formatting._
import munit.Assertions._

/*
  See:
  https://docs.scala-lang.org/sips/42.type.html
 */

object SingletonTypes extends util.App {

  "SingletonTypes".magenta pipe println

  // Literals can now appear in type position, designating the corresponding singleton type.

  {
    val one: 1               = 1       // val declaration
    def foo(x: 1): Option[1] = Some(x) // param type, type arg
    def bar[T <: 1](t: T): T = t       // type parameter bound
    foo(1: 1) // type ascription
    foo(1)
    foo(one)
    // foo(2) // doesn't compile
    bar(1: 1) // type ascription
    bar(1)
    // bar(2) // doesn't compile

  }

  // The .type singleton type forming operator can be applied to values of all subtypes of Any.
  // To prevent the compiler from widening our return type we assign to a final val.

  {
    def foo[T](t: T): t.type = t
    val bar1: Int            = foo(23)
    bar1 pipe println
    val bar2: 23 = foo(23)
    bar2 pipe println
    // val bar3: 24             = foo(23) // doesn't compile
    // final val x              = foo(42) // not allowed in inner block
  }
  def foo[T](t: T): t.type = t
  final val x              = foo(42) // x has type 42

  // The presence of an upper bound of Singleton on a formal type parameter indicates
  // that singleton types should be inferred for type parameters at call sites.
  // To help see this we introduce type constructor Id to prevent the compiler from widening our return type.

  {
    type Id[A] = A
    def wide[T](t: T): Id[T] = t
    wide(23) // result is 23: Id[Int]
    def narrow[T <: Singleton](t: T): Id[T] = t
    narrow(23) // result is 23: Id[23]
  }

  // Pattern matching against literal types and isInstanceOf tests are implemented
  // via equality/identity tests of the corresponding values.

  {
    assert(
      (1: Any) match {
        case _: 1 => true
        case _    => false
      }, // result is true: Boolean
      true
    )
    assert(
      (1: Any).isInstanceOf[1], // result is true: Boolean
      true
    )
  }

  // A scala.ValueOf[T] type class and corresponding scala.Predef.valueOf[T] operator
  // has been added yielding the unique value of types with a single inhabitant.

  {
    def foo[T](implicit v: ValueOf[T]): T = v.value
    foo[13]     // result is 13: Int
    valueOf[13] // result is 13: Int
  }
}
