package ch.decent.sdk.api

import ch.decent.sdk.account
import ch.decent.sdk.accountName
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.net.serialization.VoteId
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class MiningApiTest(channel: Channel) : BaseApiTest(channel) {
  override val useMock: Boolean = false

  @Test fun `get miners, load their accounts and put it into map with miner names`() {
    mockWebSocket
        .enqueue("""{"method":"call","params":[2,"lookup_miner_accounts",["",1000]],"id":1}""", """{"id":1,"result":[["init0","1.4.1"],["init1","1.4.2"],["init10","1.4.11"],["init2","1.4.3"],["init3","1.4.4"],["init4","1.4.5"],["init5","1.4.6"],["init6","1.4.7"],["init7","1.4.8"],["init8","1.4.9"],["init9","1.4.10"],["u46f36fcd24d74ae58c9b0e49a1f0103c","1.4.12"]]}""")
        .enqueue("""{"method":"call","params":[1,"database",[]],"id":2}""", """{"id":2,"result":2}""")
        .enqueue("""{"method":"call","params":[1,"login",["",""]],"id":3}""", """{"id":3,"result":true}""")
        .enqueue("""{"method":"call","params":[2,"get_objects",[["1.4.1","1.4.2","1.4.11","1.4.3","1.4.4","1.4.5","1.4.6","1.4.7","1.4.8","1.4.9","1.4.10","1.4.12"]]],"id":4}""", """{"id":4,"result":[{"id":"1.4.1","miner_account":"1.2.4","last_aslot":5739518,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.6","vote_id":"0:0","total_votes":"1883948815097","url":"","total_missed":477296,"last_confirmed_block_num":485250},{"id":"1.4.2","miner_account":"1.2.5","last_aslot":5739523,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.4","vote_id":"0:1","total_votes":"891228003003","url":"","total_missed":477300,"last_confirmed_block_num":485255},{"id":"1.4.11","miner_account":"1.2.14","last_aslot":5739515,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.9","vote_id":"0:10","total_votes":"891227003003","url":"","total_missed":475818,"last_confirmed_block_num":485248},{"id":"1.4.3","miner_account":"1.2.6","last_aslot":5739522,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.2","vote_id":"0:2","total_votes":248000000,"url":"","total_missed":472864,"last_confirmed_block_num":485254},{"id":"1.4.4","miner_account":"1.2.7","last_aslot":5739513,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.7","vote_id":"0:3","total_votes":"1883458812094","url":"","total_missed":477283,"last_confirmed_block_num":485246},{"id":"1.4.5","miner_account":"1.2.8","last_aslot":5739524,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.0","vote_id":"0:4","total_votes":248000000,"url":"","total_missed":477302,"last_confirmed_block_num":485256},{"id":"1.4.6","miner_account":"1.2.9","last_aslot":5739520,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.3","vote_id":"0:5","total_votes":"890986000000","url":"","total_missed":477298,"last_confirmed_block_num":485252},{"id":"1.4.7","miner_account":"1.2.10","last_aslot":5739519,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.10","vote_id":"0:6","total_votes":"992961815097","url":"","total_missed":477300,"last_confirmed_block_num":485251},{"id":"1.4.8","miner_account":"1.2.11","last_aslot":5739521,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.5","vote_id":"0:7","total_votes":"992720812094","url":"","total_missed":477299,"last_confirmed_block_num":485253},{"id":"1.4.9","miner_account":"1.2.12","last_aslot":5739516,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.8","vote_id":"0:8","total_votes":"890986000000","url":"","total_missed":477299,"last_confirmed_block_num":485249},{"id":"1.4.10","miner_account":"1.2.13","last_aslot":5695113,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.1","vote_id":"0:9","total_votes":0,"url":"","total_missed":5935,"last_confirmed_block_num":444827},{"id":"1.4.12","miner_account":"1.2.27","last_aslot":0,"signing_key":"DCT8cYDtKZvcAyWfFRusy6ja1Hafe9Ys4UPJS92ajTmcrufHnGgjp","vote_id":"0:11","total_votes":"992472812094","url":"","total_missed":3982,"last_confirmed_block_num":0}]}""")

    mockHttp.enqueue("""{"id":0,"result":[["init0","1.4.1"],["init1","1.4.2"],["init10","1.4.11"],["init2","1.4.3"],["init3","1.4.4"],["init4","1.4.5"],["init5","1.4.6"],["init6","1.4.7"],["init7","1.4.8"],["init8","1.4.9"],["init9","1.4.10"],["u46f36fcd24d74ae58c9b0e49a1f0103c","1.4.12"]]}""")
    mockHttp.enqueue("""{"id":0,"result":[{"id":"1.4.1","miner_account":"1.2.4","last_aslot":5739518,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.6","vote_id":"0:0","total_votes":"1883948815097","url":"","total_missed":477296,"last_confirmed_block_num":485250},{"id":"1.4.2","miner_account":"1.2.5","last_aslot":5739523,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.4","vote_id":"0:1","total_votes":"891228003003","url":"","total_missed":477300,"last_confirmed_block_num":485255},{"id":"1.4.11","miner_account":"1.2.14","last_aslot":5739515,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.9","vote_id":"0:10","total_votes":"891227003003","url":"","total_missed":475818,"last_confirmed_block_num":485248},{"id":"1.4.3","miner_account":"1.2.6","last_aslot":5739522,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.2","vote_id":"0:2","total_votes":248000000,"url":"","total_missed":472864,"last_confirmed_block_num":485254},{"id":"1.4.4","miner_account":"1.2.7","last_aslot":5739513,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.7","vote_id":"0:3","total_votes":"1883458812094","url":"","total_missed":477283,"last_confirmed_block_num":485246},{"id":"1.4.5","miner_account":"1.2.8","last_aslot":5739524,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.0","vote_id":"0:4","total_votes":248000000,"url":"","total_missed":477302,"last_confirmed_block_num":485256},{"id":"1.4.6","miner_account":"1.2.9","last_aslot":5739520,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.3","vote_id":"0:5","total_votes":"890986000000","url":"","total_missed":477298,"last_confirmed_block_num":485252},{"id":"1.4.7","miner_account":"1.2.10","last_aslot":5739519,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.10","vote_id":"0:6","total_votes":"992961815097","url":"","total_missed":477300,"last_confirmed_block_num":485251},{"id":"1.4.8","miner_account":"1.2.11","last_aslot":5739521,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.5","vote_id":"0:7","total_votes":"992720812094","url":"","total_missed":477299,"last_confirmed_block_num":485253},{"id":"1.4.9","miner_account":"1.2.12","last_aslot":5739516,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.8","vote_id":"0:8","total_votes":"890986000000","url":"","total_missed":477299,"last_confirmed_block_num":485249},{"id":"1.4.10","miner_account":"1.2.13","last_aslot":5695113,"signing_key":"DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4","pay_vb":"1.9.1","vote_id":"0:9","total_votes":0,"url":"","total_missed":5935,"last_confirmed_block_num":444827},{"id":"1.4.12","miner_account":"1.2.27","last_aslot":0,"signing_key":"DCT8cYDtKZvcAyWfFRusy6ja1Hafe9Ys4UPJS92ajTmcrufHnGgjp","vote_id":"0:11","total_votes":"992472812094","url":"","total_missed":3982,"last_confirmed_block_num":0}]}""")

    val test = api.miningApi.getMiners()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get new asset per block`() {
    val test = api.miningApi.getNewAssetPerBlock()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get asset per block for block`() {
    val test = api.miningApi.getAssetPerBlock(100)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get miner by account`() {
    val test = api.miningApi.getMinerByAccount("1.2.4".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get miner count`() {
    val test = api.miningApi.getMinerCount()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get feeds by miner`() {
    val test = api.miningApi.getFeedsByMiner("1.2.4".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `lookup votes`() {
    val test = api.miningApi.lookupVoteIds(listOf("0:0", "0:1"))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get actual votes`() {
    val test = api.miningApi.getActualVotes()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `search miner voting`() {
    val test = api.miningApi.searchMinerVoting("init", accountName = accountName, onlyMyVotes = true)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

}