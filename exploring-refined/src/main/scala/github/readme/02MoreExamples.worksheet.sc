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

refineMV[NonEmpty]("Hello")
// res2: String Refined NonEmpty = Hello

// refineMV[NonEmpty]("")
// <console>:39: error: Predicate isEmpty() did not fail.
//             refineMV[NonEmpty]("")
//                               ^

type ZeroToOne = Not[Less[W.`0.0`.T]] And Not[Greater[W.`1.0`.T]]

refineMV[ZeroToOne](0.8)
// refineMV[ZeroToOne](1.8)
// <console>:40: error: Right predicate of (!(1.8 < 0.0) && !(1.8 > 1.0)) failed: Predicate (1.8 > 1.0) did not fail.
//        refineMV[ZeroToOne](1.8)
//                           ^

refineMV[AnyOf[Digit :: Letter :: Whitespace :: HNil]]('F')
// res3: Char Refined AnyOf[Digit :: Letter :: Whitespace :: HNil] = F

// refineMV[MatchesRegex[W.`"[0-9]+"`.T]]("123.")
// <console>:39: error: Predicate failed: "123.".matches("[0-9]+").
//               refineMV[MatchesRegex[W.`"[0-9]+"`.T]]("123.")
//                                                     ^

val d1: Char Refined Equal[W.`'3'`.T] = '3'
// d1: Char Refined Equal[Char('3')] = 3

val d2: Char Refined Digit = d1
// d2: Char Refined Digit = 3

// val d3: Char Refined Letter = d1
// <console>:39: error: type mismatch (invalid inference):
//  Equal[Char('3')] does not imply
//  Letter
//        val d3: Char Refined Letter = d1
//                                      ^

val r1: String Refined Regex = "(a|b)"
// r1: String Refined Regex = (a|b)

// val r2: String Refined Regex = "(a|b"
// <console>:38: error: Regex predicate failed: Unclosed group near index 4
// (a|b
//     ^
//        val r2: String Refined Regex = "(a|b"
//                                       ^

val u1: String Refined Url = "http://example.com"
// u1: String Refined Url = "http://example.com"

val u2: String Refined Url = "https://example.com"
// u2: String Refined Url = "https://example.com"

// val u3: String Refined Url = "htp://example.com"
// <console>:38: error: Url predicate failed: unknown protocol: htp
//  val u3: String Refined Url = "htp://example.com"
//                               ^

// Here we define a refined type "Int with the predicate (7 <= value < 77)".
type Age = Int Refined Interval.ClosedOpen[W.`7`.T, W.`77`.T]

val userInput = 55

// We can refine values with this refined type by either using `refineV`
// with an explicit return type
val ageEither1: Either[String, Age] = refineV(userInput)
// ageEither1: Either[String,Age] = Right(55)

// or by using `RefType.applyRef` with the refined type as type parameter.
val ageEither2 = RefType.applyRef[Age](userInput)
// ageEither2: Either[String,Age] = Right(55)
