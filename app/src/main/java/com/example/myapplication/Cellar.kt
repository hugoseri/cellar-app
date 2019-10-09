package com.example.myapplication

class Cellar(name: String, id: String) {

    private var nbBottles: Int = 0;

    private var bottles: List<Bottle> = emptyList()


    fun addBottle(name: String, price: Int) {
        bottles += Bottle(name, price)
        nbBottles++
    }

    fun getBottle(name: String): Bottle {
        var i: Int = 0
        var found: Boolean = false
        var myBottle: Bottle = Bottle("empty", 0)

        while (i < nbBottles && !found) {
            if (bottles[i].name == name) {
                found = true;
                myBottle = bottles[i]
            }
            i++
        }

        if (found) {
            return myBottle
        } else {
            throw Error("The bottle named $name isn't in the cellar.")
        }
    }

    fun getTotalPriceInEuros(): Int {
        return computeTotalPrice()
    }

    fun getTotalPriceInDollars(): Int {
        return computeTotalPrice() * 4 / 5
    }

    fun computeTotalPrice(): Int {
        var price: Int = 0
        for (bottle in bottles) {
            price += bottle.price
        }
        return price
    }

    fun getNumberOfBottles(): Int {
        return nbBottles
    }
}