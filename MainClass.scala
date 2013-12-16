package de.hska.wifl1011.ws1314.projektarbeit

object MainClass {

  def main(args: Array[String]) {

    var biddersLeft: Int = 8
    var auctioneersLeft: Int = 3
    var auctionsPerAuctioneer: Int = 2

    var auctionsLeft: Int = auctioneersLeft * auctionsPerAuctioneer
        
    println(" * * * AuctionHouse started * * * ")
    println(" * * * " + auctionsLeft + " auctions will be traded * * * ")
  
    val auctionhouse: Auctionhouse = new Auctionhouse(auctionsLeft)
    auctionhouse.start

    while (auctioneersLeft > 0) {
      val auctioneer: Auctioneer = 
        new Auctioneer(auctionhouse, auctionsPerAuctioneer)
      auctioneersLeft = auctioneersLeft - 1 
    }
     
    Thread.sleep(100)
    
    var bidderId: Int = 0
    var bidders: List[Bidder] = List()

    while (biddersLeft > 0) {
      val bidder = new Bidder(bidderId, auctionhouse)
      bidder.start
      bidders = bidders ::: List(bidder)
      bidderId += 1
      biddersLeft -= 1
    }
  }
}
