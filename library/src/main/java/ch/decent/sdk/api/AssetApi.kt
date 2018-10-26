package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import io.reactivex.Single

class AssetApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Get assets by id.
   *
   * @param assetIds asset id eg. DCT id is 1.3.0
   *
   * @return list of assets or [ObjectNotFoundException]
   */
  fun getAssets(assetIds: List<ChainObject>): Single<List<Asset>> = GetAssets(assetIds).toRequest()

  /**
   * Get asset by id.
   *
   * @param assetId asset id eg. DCT id is 1.3.0
   *
   * @return asset or [ObjectNotFoundException]
   */
  fun getAsset(assetId: ChainObject): Single<Asset> = getAssets(listOf(assetId)).map { it.single() }

  /**
   * Lookup assets by symbol.
   *
   * @param assetSymbols asset symbols eg. DCT
   *
   * @return list of assets or [ObjectNotFoundException]
   */
  fun lookupAssets(assetSymbols: List<String>): Single<List<Asset>> = LookupAssets(assetSymbols).toRequest()

  /**
   * Lookup asset by symbol.
   *
   * @param assetSymbol asset symbol eg. DCT
   *
   * @return asset or [ObjectNotFoundException]
   */
  fun lookupAsset(assetSymbol: String): Single<Asset> = lookupAssets(listOf(assetSymbol)).map { it.single() }

  /**
   * Returns fees for operation.
   *
   * @param op list of operations
   *
   * @return a list of fee asset amounts
   */
  fun getFees(op: List<BaseOperation>): Single<List<AssetAmount>> = GetRequiredFees(op).toRequest()

  /**
   * Returns fee for operation.
   *
   * @param op operation
   *
   * @return a fee asset amount
   */
  fun getFee(op: BaseOperation): Single<AssetAmount> = getFees(listOf(op)).map { it.single() }

  /**
   * Returns fee for operation type, not valid for operation per size fees:
   * [OperationType.PROPOSAL_CREATE_OPERATION],
   * [OperationType.PROPOSAL_UPDATE_OPERATION],
   * [OperationType.WITHDRAW_PERMISSION_CLAIM_OPERATION],
   * [OperationType.CUSTOM_OPERATION]
   *
   * @param type operation type
   *
   * @return a fee asset amount
   */
  fun getFee(type: OperationType): Single<AssetAmount> =
      require(listOf(
          OperationType.PROPOSAL_CREATE_OPERATION,
          OperationType.PROPOSAL_UPDATE_OPERATION,
          OperationType.WITHDRAW_PERMISSION_CLAIM_OPERATION,
          OperationType.CUSTOM_OPERATION)
          .contains(type).not()
      ).let { getFee(EmptyOperation(type)) }


  /**
   * Get assets alphabetically by symbol name.
   *
   * @param lowerBound lower bound of symbol names to retrieve
   * @param limit maximum number of assets to fetch (must not exceed 100)
   *
   * @return the assets found
   */
  @JvmOverloads
  fun listAssets(lowerBound: String, limit: Int = 100): Single<List<Asset>> = ListAssets(lowerBound, limit).toRequest()

  /**
   * Converts asset into DCT, using actual price feed.
   *
   * @param amount some amount
   *
   * @return price in DCT
   */
  fun priceToDct(amount: AssetAmount): Single<AssetAmount> = PriceToDct(amount).toRequest()

  /**
   * Return current core asset supply.
   *
   * @return current supply
   */
  fun getRealSupply(): Single<RealSupply> = GetRealSupply.toRequest()
}
