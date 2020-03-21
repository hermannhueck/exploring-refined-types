package github.readme

import scala.util.chaining._

// import singleton.ops._

object App01MyVec extends util.App {

  // class MyVec[L] {
  //   def doubleSize                                  = new MyVec[2 * L]
  //   def nSize[N]                                    = new MyVec[N * L]
  //   def getLength(implicit length: SafeInt[L]): Int = length
  // }
  // object MyVec {
  //   implicit def apply[L](implicit check: Require[L > 0]): MyVec[L] = new MyVec[L]()
  // }
  // val myVec: MyVec[10] = MyVec[4 + 1].doubleSize
  // val myBadVec = MyVec[-1] //fails compilation, as required

  "hello" pipe println
}
