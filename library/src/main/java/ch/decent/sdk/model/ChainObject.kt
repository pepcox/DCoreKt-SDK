package ch.decent.sdk.model

import ch.decent.sdk.model.types.UInt64
import java.math.BigInteger

@Suppress("MagicNumber")
class ChainObject {

  val objectType: ObjectType
  @UInt64 val instance: BigInteger

  private constructor(id: String) {
    val g = regex.matchEntire(id)!!.groupValues
    objectType = ObjectType.fromSpaceType(g[1].toInt(), g[2].toInt())
    instance = g[3].toBigInteger()
  }

  internal constructor(objectType: ObjectType) {
    this.objectType = objectType
    this.instance = BigInteger.ZERO
  }

  internal val fullInstance: Long
    get() = (objectType.space.toLong().shl(56) or objectType.type.toLong().shl(48) or instance.toLong())

  /**
   *
   * @return: A String containing the full object apiId in the form {space}.{type}.{instance}
   */
  val objectId: String
    get() = String.format("%d.%d.%d", objectType.space, objectType.type, instance)

  override fun toString(): String = objectId

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ChainObject

    if (objectType != other.objectType) return false
    if (instance != other.instance) return false

    return true
  }

  override fun hashCode(): Int {
    var result = objectType.hashCode()
    result = 31 * result + instance.hashCode()
    return result
  }

  companion object {
    private val regex = Regex("""(\d+)\.(\d+)\.(\d+)(?:\.(\d+))?""")

    @JvmStatic fun parse(id: String) = if (isValid(id)) ChainObject(id) else throw IllegalArgumentException()
    @JvmStatic fun isValid(id: String): Boolean = regex.matchEntire(id.trim()) != null
  }
}

fun String.toChainObject() = ChainObject.parse(this)
