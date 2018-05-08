package kirk.samples

import kirk.api.Assertion
import kirk.api.expect
import kirk.assertions.isEqualTo
import kirk.assertions.isNull
import kirk.assertions.isUpperCase
import java.time.LocalDate
import java.time.temporal.ChronoUnit.YEARS
import kotlin.test.Test

data class Person(
  val name: String,
  val birthDate: LocalDate
)

class Assertions {
  @Test
  fun inlineAssert() {
    val subject = Person(
      name = "David",
      birthDate = LocalDate.of(1947, 1, 8)
    )

    // use assert in a test for a one-off assertion
    expect(subject)
      .assert("is older than 21") {
        if (subject.birthDate < LocalDate.now().minus(21, YEARS)) {
          pass()
        } else {
          fail()
        }
      }

    // alternatively define a reusable assertion function
    fun Assertion<Person>.isOlderThan(age: Int) =
      assert("is older than $age") {
        if (subject.birthDate < LocalDate.now().minus(age.toLong(), YEARS)) {
          pass()
        } else {
          fail()
        }
      }

    // which can then be used just like standard assertions
    expect(subject).isOlderThan(21)
  }

  @Test
  fun map() {
    val subject = Person(
      name = "David",
      birthDate = LocalDate.of(1947, 1, 8)
    )

    expect(subject) {
      map { name }             // map based on properties...
        .isEqualTo("David")
        .map { toUpperCase() } // ... or methods
        .isEqualTo("DAVID")
      map { birthDate }
        .map { year }
        .isEqualTo(1947)
    }

    expect(subject)
      .map(Person::name)       // map using a property reference
      .isEqualTo("David")
  }

  @Test
  fun not() {
    val subject: String? = "covfefe"
    expect(subject)
      .not()
      .isNull()
      .isUpperCase()
  }
}
