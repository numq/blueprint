package io.github.numq.blueprint.runtime.fp

/**
 * Wraps this value into an [Either.Left].
 */
fun <L> L.left(): Either<L, Nothing> = Either.Left(this)

/**
 * Wraps this value into an [Either.Right].
 */
fun <R> R.right(): Either<Nothing, R> = Either.Right(this)

/**
 * Folds over an [Iterable] accumulating state using [operation] that returns an [Either].
 * Short-circuits and immediately returns [Either.Left] upon encountering the first failure.
 *
 * @param initial starting accumulator value.
 * @param operation function applied sequentially to accumulator and elements.
 * @return accumulated result wrapped in [Either.Right], or the first encountered [Either.Left].
 */
inline fun <T, L, R> Iterable<T>.foldEither(initial: R, operation: (acc: R, element: T) -> Either<L, R>): Either<L, R> {
    var current = initial

    for (item in this) {
        when (val result = operation(current, item)) {
            is Either.Left -> return result

            is Either.Right -> current = result.value
        }
    }

    return Either.Right(current)
}