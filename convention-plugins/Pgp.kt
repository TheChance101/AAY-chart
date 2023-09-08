abstract class Pgp {
    abstract  fun useInMemoryPgpKeys(
         defaultKeyId: String,
         defaultSecretKey: String,
         defaultPassword: String
    )
}