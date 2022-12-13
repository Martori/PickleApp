package cat.martori.pickleapp.domain


inline fun <R, T> Result<T>.flatMap(transform: (value: T) -> Result<R>) = fold({
    transform(it)
}, {
    Result.failure(it)
})