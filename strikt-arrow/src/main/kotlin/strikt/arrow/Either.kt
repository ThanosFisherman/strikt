package strikt.arrow

import arrow.core.Either
import strikt.api.Assertion

/**
 * Asserts that the [Either] is [Either.Right]
 * @return Assertion builder over the same subject that is now known to be
 * a [Either.Right].
 */
@Suppress("UNCHECKED_CAST")
fun <L, R> Assertion.Builder<Either<L, R>>.isRight() =
  assert("should be Right") {
    when (it) {
      is Either.Right -> pass()
      is Either.Left -> fail()
    }
  } as Assertion.Builder<Either.Right<R>>

/**
 * Asserts that the [Either] is [Either.Right] and that it contains the exact value
 * @param value Value to compare to the [Either]'s wrapped value
 * @return Assertion builder over the same subject that is now known to be
 * a [Either.Right].
 */
@Suppress("UNCHECKED_CAST")
infix fun <L, R> Assertion.Builder<Either<L, R>>.isRight(value: R) =
  assert("should be Right($value)") {
    when (it) {
      is Either.Right -> if (it.b == value) {
        pass()
      } else {
        fail()
      }

      else -> fail()
    }
  } as Assertion.Builder<Either.Right<R>>

/**
 * Unwraps the containing value of the [Either.Right]
 * @return Assertion builder over the unwrapped subject
 */
val <R> Assertion.Builder<Either.Right<R>>.b: Assertion.Builder<R>
  @JvmName("eitherB")
  get() = get("right value") { b }

/**
 * Asserts that the [Either] is [Either.Left]
 * @return Assertion builder over the same subject that is now known to be
 * a [Either.Left].
 */
@Suppress("UNCHECKED_CAST")
fun <L, R> Assertion.Builder<Either<L, R>>.isLeft() =
  assert("should be Left") {
    when {
      it.isRight() -> fail()
      it.isLeft() -> pass()
    }
  } as Assertion.Builder<Either.Left<L>>

/**
 * Asserts that the [Either] is [Either.Left] and that it contains the exact value
 * @param value Value to compare to the [Either]'s wrapped value
 * @return Assertion builder over the same subject that is now known to be
 * a [Either.Left].
 */
@Suppress("UNCHECKED_CAST")
infix fun <L, R> Assertion.Builder<Either<L, R>>.isLeft(value: L) =
  assert("should be Left($value)") {
    when (it) {
      is Either.Left -> {
        if (it.a == value) {
          pass()
        } else {
          fail()
        }
      }
      else -> fail()
    }
  } as Assertion.Builder<Either.Left<L>>

/**
 * Unwraps the containing value of the [Either.Left]
 * @return Assertion builder over the unwrapped subject
 */
val <L> Assertion.Builder<Either.Left<L>>.a: Assertion.Builder<L>
  @JvmName("eitherA")
  get() = get("left value") { a }
