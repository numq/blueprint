package io.github.numq.blueprint.runtime.fp

fun <L> L.left(): Either<L, Nothing> = Either.Left(this)

fun <R> R.right(): Either<Nothing, R> = Either.Right(this)

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