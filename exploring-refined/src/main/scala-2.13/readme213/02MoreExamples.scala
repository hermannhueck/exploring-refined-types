package github.readme213

import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._
import eu.timepit.refined.api.RefType
import eu.timepit.refined.boolean._
import eu.timepit.refined.char._
import eu.timepit.refined.collection._
import eu.timepit.refined.generic._
import eu.timepit.refined.string._
import shapeless.{::, HNil}

import scala.util.chaining._
import munit.Assertions._
import eu.timepit.refined.types.string.NonEmptyString

object MoreExamples extends _root_.util.App {

  refineMV[NonEmpty]("Hello")
    .tap(assertEquals(_, NonEmptyString("Hello")))
    .pipe(println)
  // res2: String Refined NonEmpty = Hello

  println()
  compileErrors("""refineMV[NonEmpty]("")""") pipe println
  // error: Predicate isEmpty() did not fail.
  //             refineMV[NonEmpty]("")
  //                               ^

  type ZeroToOne = Not[Less[0.0]] And Not[Greater[1.0]]

  refineMV[ZeroToOne](0.8) pipe println

  println()
  compileErrors("""refineMV[ZeroToOne](1.8)""") pipe println
  // error: Right predicate of (!(1.8 < 0.0) && !(1.8 > 1.0)) failed: Predicate (1.8 > 1.0) did not fail.
  //        refineMV[ZeroToOne](1.8)
  //                           ^

  refineMV[AnyOf[Digit :: Letter :: Whitespace :: HNil]]('F') pipe println
  // res3: Char Refined AnyOf[Digit :: Letter :: Whitespace :: HNil] = F

  println()
  compileErrors("""refineMV[MatchesRegex["[0-9]+"]]("123.")""") pipe println
  // error: Predicate failed: "123.".matches("[0-9]+").
  //               refineMV[MatchesRegex[W.`"[0-9]+"`.T]]("123.")
  //                                                     ^

  val d1: Char Refined Equal['3'] = '3'
  // d1: Char Refined Equal[Char('3')] = 3
  d1 tap println

  val d2: Char Refined Digit = d1
  // d2: Char Refined Digit = 3
  d2 tap println

  println()
  compileErrors("""val d3: Char Refined Letter = d1""") pipe println
  // error: type mismatch (invalid inference):
  //  Equal[Char('3')] does not imply
  //  Letter
  //        val d3: Char Refined Letter = d1
  //                                      ^

  val r1: String Refined Regex = "(a|b)"
  // r1: String Refined Regex = (a|b)
  r1 pipe println

  println()
  compileErrors("""val r2: String Refined Regex = "(a|b"""") pipe println
  // error: Regex predicate failed: Unclosed group near index 4
  // (a|b
  //     ^
  //        val r2: String Refined Regex = "(a|b"
  //                                       ^

  val u1: String Refined Url = "http://example.com"
  // u1: String Refined Url = "http://example.com"
  u1 pipe println

  val u2: String Refined Url = "https://example.com"
  // u2: String Refined Url = "https://example.com"
  u2 pipe println

  println()
  compileErrors("""val u3: String Refined Url = "htp://example.com"""") pipe println
  // error: Url predicate failed: unknown protocol: htp
  //  val u3: String Refined Url = "htp://example.com"
  //                               ^

  // Here we define a refined type "Int with the predicate (7 <= value < 77)".
  type Age = Int Refined Interval.ClosedOpen[7, 77]

  val userInput = 55

  // We can refine values with this refined type by either using `refineV`
  // with an explicit return type
  val ageEither1: Either[String, Age] = refineV(userInput)
  // ageEither1: Either[String,Age] = Right(55)
  ageEither1 pipe println

  // or by using `RefType.applyRef` with the refined type as type parameter.
  val ageEither2 = RefType.applyRef[Age](userInput)
  // ageEither2: Either[String,Age] = Right(55)
  ageEither2 pipe println
}
