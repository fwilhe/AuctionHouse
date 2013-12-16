package de.hska.wifl1011.ws1314.projektarbeit

class Auctioneer(auctionhouse: Auctionhouse, auctionsCount: Int) {

  var auctions: Int = auctionsCount
  
  while (auctions > 0) {
    auctionhouse ! AddAuction(new Auction(scala.util.Random.nextInt()))
    auctions -= 1
  }

  override def toString(): String = {
    "Auctioneer"
  }  
}
