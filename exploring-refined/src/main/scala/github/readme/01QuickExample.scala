package github.readme

import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._
import eu.timepit.refined.types.numeric.PosInt

import scala.util.chaining._
import munit.Assertions._

import compat213.either._ // Either#withRight & Either#withLeft for 2.12

object QuickExample extends _root_.util.App {

  // This refines Int with the Positive predicate and checks via an
  // implicit macro that the assigned value satisfies it:
  val i1: Int Refined Positive = 5 // PosInt(5)
  i1 tap { r =>
    assertEquals(r, PosInt(5))
  } pipe println

  // If the value does not satisfy the predicate, we get a meaningful
  // compile error:
  assertNoDiff(
    obtained = compileErrors("val i2: Int Refined Positive = -5"),
    expected = """|error: Predicate failed: (-5 > 0).
                  |val i2: Int Refined Positive = -5
                  |                               ^
                  |""".stripMargin
  )

  // There is also the explicit refineMV macro that can infer the base
  // type from its parameter:
  refineMV[Positive](5)
    .tap(assertEquals(_, PosInt(5)))
    .pipe(println)
  // res0: Int Refined Positive = 5

  // Macros can only validate literals because their values are known at
  // compile-time. To validate arbitrary (runtime) values we can use the
  // refineV function:

  val x = 42 // suppose the value of x is not known at compile-time

  refineV[Positive](x)
    .tap(assertEquals(_, Right(PosInt(42)).withLeft[String]))
    .pipe(println)
  // res1: Either[String, Int Refined Positive] = Right(42)

  refineV[Positive](-x)
    .tap(assertEquals(_, Left("Predicate failed: (-42 > 0).").withRight[PosInt]))
    .pipe(println)
  // res2: Either[String, Int Refined Positive] = Left(Predicate failed: (-42 > 0).)

  // refined also contains inference rules for converting between different refined types.
  // For example, Int Refined Greater[W.`10`.T] can be safely converted to Int Refined Positive
  // because all integers greater than ten are also positive. The type conversion of refined types
  // is a compile-time operation that is provided by the library:

  val a: Int Refined Greater[W.`5`.T] = 10
  // a: Int Refined Greater[Int(5)] = 10

  // Since every value greater than 5 is also greater than 4, `a` can be
  // ascribed the type Int Refined Greater[W.`4`.T]:
  val b: Int Refined Greater[W.`4`.T] = a
  // b: Int Refined Greater[Int(4)] = 10

  // An unsound ascription leads to a compile error:
  println()
  compileErrors("val c: Int Refined Greater[W.`6`.T] = a") pipe println
  // <console>:23: error: type mismatch (invalid inference):
  //  Greater[Int(5)] does not imply
  //  Greater[Int(6)]
  //        val c: Int Refined Greater[W.`6`.T] = a
  //                                              ^
}
