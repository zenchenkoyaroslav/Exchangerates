package com.example.exchangerates

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "DailyExRates", strict = false)
class NBRBDailyExRatesResponse{
    @field:ElementList(inline = true, required = false)
    internal var Currency: List<NBRBCurrencyResponse>? = null
}

@Root(name = "Currency")
internal class NBRBCurrencyResponse{

    @set:Element(name = "CharCode")
    @get:Element(name = "CharCode")
    var CharCode: String? = null

    @set:Element(name = "Scale")
    @get:Element(name = "Scale")
    var Scale: String? = null

    @set:Element(name = "Name")
    @get:Element(name = "Name")
    var Name: String? = null

    @set:Element(name = "Rate")
    @get:Element(name = "Rate")
    var Rate: String? = null

}

