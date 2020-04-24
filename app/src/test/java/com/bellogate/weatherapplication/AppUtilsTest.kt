package com.bellogate.weatherapplication

import com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname.FiveDayWeatherForecast
import com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname.Main
import com.bellogate.weatherapplication.ui.util.convertToCelsius
import com.bellogate.weatherapplication.ui.util.getFiveDayForecast
import org.junit.Test

/**
 * This class contains unit test for AppUtils.kt
 */
class AppUtilsTest {


    @Test
    fun convertToCelsiusTest(){
        val inputKelvin = 300.00000
        val expectedResultInCelsius = 26.86
        val result = convertToCelsius(inputKelvin)

        assert(expectedResultInCelsius == result)
    }


    @Test
    fun getFiveDayForecast(){

         val expectedResult = "---- Day 1 ----  \n" +
                 "2020-04-01 : 3:30am  :  26.86°C\n" +
                 "\n" +
                 "---- Day 2 ----  \n" +
                 "2020-04-02 : 3:30am  :  26.86°C\n" +
                 "\n" +
                 "---- Day 3 ----  \n" +
                 "2020-04-03 : 3:30am  :  26.86°C\n" +
                 "\n" +
                 "---- Day 4 ----  \n" +
                 "2020-04-04 : 3:30am  :  26.86°C\n" +
                 "\n" +
                 "---- Day 5 ----  \n" +
                 "2020-04-05 : 3:30am  :  26.86°C\n"

        val list1 = com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname.List()
        list1.dtTxt = "2020-04-01 : 3:30am"
        val main1 = Main()
        main1.temp = 300.0
        list1.main = main1

        val list2 = com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname.List()
        list2.dtTxt = "2020-04-02 : 3:30am"
        val main2 = Main()
        main2.temp = 300.0
        list2.main = main2

        val list3 = com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname.List()
        list3.dtTxt = "2020-04-03 : 3:30am"
        val main3 = Main()
        main3.temp = 300.0
        list3.main = main3

        val list4 = com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname.List()
        list4.dtTxt = "2020-04-04 : 3:30am"
        val main4 = Main()
        main4.temp = 300.0
        list4.main = main4

        val list5 = com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname.List()
        list5.dtTxt = "2020-04-05 : 3:30am"
        val main5 = Main()
        main5.temp = 300.0
        list5.main = main5

        val fiveDayWeatherForecast = FiveDayWeatherForecast()
        fiveDayWeatherForecast.list = listOf(list1, list2, list3, list4, list5)

        val result = getFiveDayForecast(fiveDayWeatherForecast)

        assert(expectedResult.contains(result))

    }
}