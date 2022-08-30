package com.example.firstcomposeactivity.crypto.domain.usecase.getcoindetails

import com.example.firstcomposeactivity.crypto.common.Resource
import com.example.firstcomposeactivity.crypto.data.remote.dto.toCoin
import com.example.firstcomposeactivity.crypto.data.remote.dto.toCoinDetails
import com.example.firstcomposeactivity.crypto.domain.model.Coin
import com.example.firstcomposeactivity.crypto.domain.model.CoinDetails
import com.example.firstcomposeactivity.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetCoinDetailsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId:String): Flow<Resource<CoinDetails>> = flow {
        try {
            emit(Resource.Loading<CoinDetails>())
            val coin = repository.getCoinById(coinId).toCoinDetails()
            emit(Resource.Success<CoinDetails>(coin))
        } catch (e: HttpException) {
            emit(Resource.Error<CoinDetails>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<CoinDetails>("Couldn't reach server, check your internet connection"))
        }
    }
}
