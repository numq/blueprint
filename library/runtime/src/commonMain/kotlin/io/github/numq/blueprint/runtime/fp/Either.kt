package io.github.numq.blueprint.runtime.fp

/**
 * Represents a value of one of two possible types (a disjoint union).
 * An instance of [Either] is either an instance of [Either.Left] or [Either.Right].
 *
 * Conventionally, [Left] is used for failure/error states and [Right] is used for success values.
 *
 * @param L the type of the [Left] (error) value.
 * @param R the type of the [Right] (success) value.
 */
sealed interface Either<out L, out R> {
    /**
     * Represents the left side of an [Either] instance, typically containing error or failure details.
     *
     * @property value the left value.
     */
    data class Left<out L>(val value: L) : Either<L, Nothing>

    /**
     * Represents the right side of an [Either] instance, typically containing a successful result.
     *
     * @property value the right value.
     */
    data class Right<out R>(val value: R) : Either<Nothing, R>

    /**
     * Maps the [Right] value using the provided [transform] function if present.
     *
     * @param T the transformed value type.
     * @param transform function to apply to the [Right] value.
     * @return a new [Either] containing the transformed value if this was [Right], or `this` if [Left].
     */
    fun <T> map(transform: (R) -> T): Either<L, T> = when (this) {
        is Left -> this

        is Right -> Right(value = transform(value))
    }

    /**
     * Applies [transform] to the [Right] value, returning the resulting [Either].
     *
     * @param T the transformed right value type.
     * @param transform function to produce an [Either] from the [Right] value.
     * @return the result of applying [transform] if [Right], or `this` if [Left].
     */
    fun <T> flatMap(transform: (R) -> Either<@UnsafeVariance L, T>): Either<L, T> = when (this) {
        is Left -> this

        is Right -> transform(value)
    }

    /**
     * Returns the [Right] value if present, or evaluates [fallback] if this is a [Left].
     *
     * @param fallback default value producer function evaluated on [Left].
     * @return [R] value or fallback.
     */
    fun getOrElse(fallback: () -> @UnsafeVariance R): R = when (this) {
        is Left -> fallback()

        is Right -> value
    }
}