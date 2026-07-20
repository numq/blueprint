package io.github.numq.blueprint.runtime.fp

sealed interface Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>

    data class Right<out R>(val value: R) : Either<Nothing, R>

    fun <T> map(transform: (R) -> T): Either<L, T> = when (this) {
        is Left -> this

        is Right -> Right(value = transform(value))
    }

    fun <T> flatMap(transform: (R) -> Either<@UnsafeVariance L, T>): Either<L, T> = when (this) {
        is Left -> this

        is Right -> transform(value)
    }

    fun getOrElse(fallback: () -> @UnsafeVariance R): R = when (this) {
        is Left -> fallback()

        is Right -> value
    }
}