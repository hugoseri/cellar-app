package com.example.myapplication

class Cellar(name: String, id: String) {

    private var nbBottles: Int = 0;

    private var bottles: ArrayList<Bottle> = arrayListOf()


    fun addBottle(name: String, price: Double) {
        bottles.add(Bottle(name, price))
        nbBottles++
    }

    fun addBottle(bottle: Bottle){
        bottles.add(bottle)
        nbBottles++
    }

    fun addBottleStart(bottle: Bottle){
        bottles.add(0, bottle)
        nbBottles++
    }

    fun removeBottle(index: Int){
        bottles.removeAt(index)
        nbBottles--
    }

    fun removeAllBottles(){
        bottles.clear()
    }

    fun getBottles(): List<Bottle> {
        return bottles
    }

    fun getLastBottle(): Bottle {
        return bottles[bottles.lastIndex]
    }


    fun getFirstBottle(): Bottle {
        return bottles[0]
    }

    fun getBottle(index: Int): Bottle {
        return bottles[index]
    }

    fun getBottle(name: String): Bottle {
        var i: Int = 0
        var found: Boolean = false
        var myBottle: Bottle = Bottle("empty", 0.0)

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

    fun getTotalPriceInEuros(): Double {
        return computeTotalPrice()
    }

    fun getTotalPriceInDollars(): Double {
        return computeTotalPrice() * 4 / 5
    }

    fun computeTotalPrice(): Double {
        var price: Double = 0.0
        for (bottle in bottles) {
            price += bottle.price
        }
        return price
    }

    fun getNumberOfBottles(): Int {
        return nbBottles
    }
}